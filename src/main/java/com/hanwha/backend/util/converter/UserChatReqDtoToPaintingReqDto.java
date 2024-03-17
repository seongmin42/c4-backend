package com.hanwha.backend.util.converter;

import com.hanwha.backend.data.IData;
import com.hanwha.backend.data.dto.request.PaintingRequestDto;
import com.hanwha.backend.data.dto.request.UserChatRequestDto;

public class UserChatReqDtoToPaintingReqDto implements Converter{
    @Override
    public IData convert(IData userChatRequestDto) {
        return new PaintingRequestDto(((UserChatRequestDto) userChatRequestDto).getContent());
    }
}