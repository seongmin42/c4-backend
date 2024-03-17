package com.hanwha.backend.util.converter;

import com.hanwha.backend.data.IData;
import com.hanwha.backend.data.dto.request.ChatRequestDto;
import com.hanwha.backend.data.dto.request.Message;
import com.hanwha.backend.data.dto.request.UserChatRequestDto;
import com.hanwha.backend.data.enums.Models;
import com.hanwha.backend.data.enums.Role;
import com.hanwha.backend.repository.ChatLogRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserChatReqDtoToChatReqDto implements Converter{

    private final ChatLogRepository chatLogRepository;

    public UserChatReqDtoToChatReqDto(ChatLogRepository chatLogRepository) {
        this.chatLogRepository = chatLogRepository;
    }

    @Override
    public IData convert(IData data) {
        UserChatRequestDto dto = (UserChatRequestDto) data;
        List<Message> messages = chatLogRepository.findRecentChatLogByRoomId(dto.getRoomId())
                .stream()
                .map(logs -> new Message(logs.getRole().toString(), logs.getContent()))
                .collect(Collectors.toList());
        messages.add(new Message(Role.user.toString(), dto.getContent()));
        return new ChatRequestDto(Models.gpt3.getModel(), messages, dto.getChatOption().getTemperature());
    }
}
