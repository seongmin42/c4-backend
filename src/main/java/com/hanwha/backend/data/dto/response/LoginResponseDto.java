package com.hanwha.backend.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String userId;
    private String accessToken;
}