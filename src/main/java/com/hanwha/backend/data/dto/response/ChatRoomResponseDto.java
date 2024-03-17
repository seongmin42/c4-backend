package com.hanwha.backend.data.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRoomResponseDto {
    private String _id;
    private List<String> contents;
    private Date createdAt;
}
