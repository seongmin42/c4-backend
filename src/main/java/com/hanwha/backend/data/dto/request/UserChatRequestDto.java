package com.hanwha.backend.data.dto.request;

import com.hanwha.backend.data.IData;
import com.hanwha.backend.data.enums.ChatOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChatRequestDto implements IData {
    private String userId;
    private String roomId;
    private String content;
    private ChatOption chatOption;
}

