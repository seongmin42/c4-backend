package com.hanwha.backend.data.dto.response;

import com.hanwha.backend.data.entity.ChatLog;
import com.hanwha.backend.data.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaintingResponseDto {

    private Long created;
    private List<Url> data;

    @Deprecated
    public List<ChatLog> toChatLogList(String userId, String roomId){
        List<ChatLog> chatLogs = new ArrayList<>();
        for(Url url: this.data){
            chatLogs.add(new ChatLog(url.getUrl(), Role.dalle, userId, roomId, true));
        }
        return chatLogs;
    }
}
