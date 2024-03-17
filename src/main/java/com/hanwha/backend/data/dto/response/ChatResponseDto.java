package com.hanwha.backend.data.dto.response;

import com.hanwha.backend.data.dto.request.Message;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDto {
    private Long created;
    private List<Choice> choices;

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Choice {
        private int index;
        private Message message;
    }

}
