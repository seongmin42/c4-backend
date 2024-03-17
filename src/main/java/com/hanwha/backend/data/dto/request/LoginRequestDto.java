package com.hanwha.backend.data.dto.request;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String userId;
    private String password;
}
