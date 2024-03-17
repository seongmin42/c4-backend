package com.hanwha.backend.controller;

import com.hanwha.backend.data.dto.request.UserChatRequestDto;
import com.hanwha.backend.exception.api.ChatRoomInvalid;
import com.hanwha.backend.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.lang.reflect.InvocationTargetException;

@Api("Chat Controller")
@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatController {

    private final ChatService chatService;

    @ApiOperation(value="유저측 대화 내용 수신", notes="유저의 프롬프트를 전송받습니다.")
    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "gpt의 응답을 ChatResponseDto로 반환합니다."),
            @ApiResponse(code = 401, message = "Token invalid or expired. Please login again.")
    })
    public SseEmitter receiveUserRequest(@RequestBody UserChatRequestDto userRequestDto) throws ChatRoomInvalid, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return chatService.requestToGpt(userRequestDto);
    }

    @ApiOperation(value="유저측 Dall-E 요청 수신", notes="유저의 Dall-E 요청 프롬프트를 전송받습니다.")
    @PostMapping("/painting")
    @ApiResponses({
            @ApiResponse(code = 200, message = "dall-e의 응답을 PaintingResponseDto로 반환합니다."),
            @ApiResponse(code = 401, message = "Token invalid or expired. Please login again.")
    })
    public ResponseEntity<?> receivePaintingRequest(@RequestBody UserChatRequestDto chatRequestDto) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return new ResponseEntity<>(chatService.requestToDalle(chatRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value="대화방 생성", notes="새로운 대화 세션을 생성합니다.")
    @PostMapping("/room/{userId}")
    @ApiResponses({
            @ApiResponse(code = 201, message = "New room created."),
            @ApiResponse(code = 401, message = "Token invalid or expired. Please login again.")
    })
    public ResponseEntity<?> createRoom(@PathVariable String userId){
        return new ResponseEntity<>(chatService.newChat(userId), HttpStatus.CREATED);
    }

    @ApiOperation(value="특정 유저의 대화방 가져오기", notes="특정 유저의 대화 세션을 가져옵니다.")
    @GetMapping("/room/{userId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "유저의 대화 세션 목록을 반환합니다."),
            @ApiResponse(code = 401, message = "Token invalid or expired. Please login again.")
    })
    public ResponseEntity<?> getRoom(@PathVariable String userId){
        return new ResponseEntity<>(chatService.getOnesRooms(userId), HttpStatus.OK);
    }

    @ApiOperation(value="대화방 삭제", notes="대화 세션을 삭제합니다.")
    @DeleteMapping("/room/{roomId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Room successfully deleted."),
            @ApiResponse(code = 401, message = "Token invalid or expired. Please login again.")
    })
    public ResponseEntity<?> removeRoom(@PathVariable String roomId){
        try {
            chatService.deleteChat(roomId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ChatRoomInvalid e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value="대화방 내 대화 가져오기", notes="대화 세션별로 대화 내역을 가져옵니다.")
    @GetMapping("/{roomId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "대화 내역들을 반환합니다."),
            @ApiResponse(code = 401, message = "Token invalid or expired. Please login again.")
    })
    public ResponseEntity<?> getConversation(@PathVariable String roomId){
        return new ResponseEntity<>(chatService.getChatHistory(roomId), HttpStatus.OK);
    }


}
