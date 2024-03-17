package com.hanwha.backend.util.converter;

import com.hanwha.backend.data.IData;
import com.hanwha.backend.data.dto.request.UserChatRequestDto;
import com.hanwha.backend.data.entity.ChatLog;
import com.hanwha.backend.data.enums.Role;
import com.hanwha.backend.util.verifier.BuiltinVerifier;

public class UserChatReqDtoToChatLog implements Converter{
    private final BuiltinVerifier verifier;

    public UserChatReqDtoToChatLog(BuiltinVerifier builtinVerifier){
        this.verifier = builtinVerifier;
    }
    @Override
    public IData convert(IData data) {
        UserChatRequestDto dto = (UserChatRequestDto) data;
        return new ChatLog(dto.getContent(), Role.user, dto.getUserId(), dto.getRoomId(), verifier.verify(dto.getContent()));
    }
}
