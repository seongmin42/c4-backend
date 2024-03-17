package com.hanwha.backend.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseChunkDto {
    private String id;
    private String object;
    private Date created;
    private String model;
    private List<Chunk> choices;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Chunk{
        private int index;
        private Delta delta;
        private String finish_reason;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Delta{
            private String content;
        }
    }

    @Override
    public String toString(){
        return this.choices.get(0).getDelta().getContent();
    }
}
