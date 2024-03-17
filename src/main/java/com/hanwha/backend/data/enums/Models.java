package com.hanwha.backend.data.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public enum Models {
    gpt3("gpt-3.5-turbo"), gpt4("gpt-4"), dalle;
    final private String model;

    Models(String model) {
        this.model = model;
    }
}
