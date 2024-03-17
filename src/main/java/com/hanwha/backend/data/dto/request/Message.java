package com.hanwha.backend.data.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String role;
    private String content;
}
