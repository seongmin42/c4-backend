package com.hanwha.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanwha.backend.data.dto.request.UserChatRequestDto;
import com.hanwha.backend.data.dto.response.ChatResponseChunkDto;
import com.hanwha.backend.data.dto.response.ChatRoomResponseDto;
import com.hanwha.backend.data.dto.response.PaintingResponseDto;
import com.hanwha.backend.data.entity.ChatLog;
import com.hanwha.backend.data.enums.Role;
import com.hanwha.backend.exception.api.ChatRoomInvalid;
import com.hanwha.backend.repository.ChatLogRepository;
import com.hanwha.backend.util.CustomSseEmitter;
import com.hanwha.backend.util.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ChatService {


    @Value("${dalle.api-url}")
    private String dalleApiUrl;
    private final RestTemplate openaiRestTemplate;
    private final ChatLogRepository chatLogRepository;
    private final ObjectMapper jsonParser;
    private final WebClient webClient;
    private final Converter userChatReqDtoToChatLog;
    private final Converter userChatReqToChatReqDto;
    private final Converter userChatReqDtoToPaintingReqDto;

    @Autowired
    public ChatService(RestTemplate openaiRestTemplate, ChatLogRepository chatLogRepository,
                       ObjectMapper jsonParser,
                       @Qualifier("openaiWebClient") WebClient webClient,
                       Converter userChatReqDtoToChatLog,
                       Converter userChatReqToChatReqDto,
                       Converter userChatReqDtoToPaintingReqDto) {
        this.openaiRestTemplate = openaiRestTemplate;
        this.chatLogRepository = chatLogRepository;
        this.jsonParser = jsonParser;
        this.webClient = webClient;
        this.userChatReqDtoToChatLog = userChatReqDtoToChatLog;
        this.userChatReqToChatReqDto = userChatReqToChatReqDto;
        this.userChatReqDtoToPaintingReqDto = userChatReqDtoToPaintingReqDto;
    }

    public SseEmitter requestToGpt(UserChatRequestDto dto) {
        chatLogRepository.insert(userChatReqDtoToChatLog.convert(dto));
        SseEmitter sseEmitter = new CustomSseEmitter();
        List<String> completeResponse = new ArrayList<>();
        webClient
                .post()
                .body(BodyInserters.fromValue(userChatReqToChatReqDto.convert(dto)))
                .exchangeToFlux(response -> response.bodyToFlux(String.class))
                .doOnNext(line->doOnNext(sseEmitter, completeResponse, line))
                .doOnError(sseEmitter::completeWithError)
                .doOnComplete(()->doComplete(sseEmitter, new ChatLog(String.join("", completeResponse), Role.assistant, dto.getUserId(), dto.getRoomId(), true)))
                .subscribe();
        return sseEmitter;
    }

    public PaintingResponseDto requestToDalle(UserChatRequestDto dto) {
        chatLogRepository.insert(userChatReqDtoToChatLog.convert(dto));
        PaintingResponseDto response = openaiRestTemplate.postForObject(dalleApiUrl, userChatReqDtoToPaintingReqDto.convert(dto), PaintingResponseDto.class);
        chatLogRepository.insert(new ChatLog(response.getData().get(0).getUrl(), Role.dalle, dto.getUserId(), dto.getRoomId(), true));
        return response;
    }

    public String newChat(String userId){
        String roomId = UUID.randomUUID().toString();
        chatLogRepository.insert(new ChatLog("New Chat", Role.system, userId, roomId, true));
        return roomId;
    }

    public List<ChatRoomResponseDto> getOnesRooms(String userId){
        return chatLogRepository.findDistinctRoomByUserId(userId);
    }

    public List<ChatLog> getChatHistory(String roomId){
        return chatLogRepository.findChatLogByRoomId(roomId);
    }

    public void deleteChat(String roomId) throws ChatRoomInvalid {
        chatLogRepository.deleteChatLogByRoomId(roomId);
    }

    public void doOnNext(SseEmitter sseEmitter, List<String> completeResponse, String line){
        try{
            if(!line.equals("[DONE]")){
                ChatResponseChunkDto chunk = jsonParser.readValue(line, ChatResponseChunkDto.class);
                if(chunk.getChoices().get(0).getFinish_reason() == null){
                    String content = chunk.getChoices().get(0).getDelta().getContent();
                    sseEmitter.send(SseEmitter.event().data(content));
                    if(content.contains("\n\n")){
                        sseEmitter.send(SseEmitter.event().name("END"));
                    }
                    completeResponse.add(chunk.getChoices().get(0).getDelta().getContent());
                }
            }else{
                sseEmitter.send(SseEmitter.event().name("END"));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void doComplete(SseEmitter sseEmitter, ChatLog chatLog){
        chatLogRepository.insert(chatLog);
        sseEmitter.complete();
    }
}
