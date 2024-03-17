package com.hanwha.backend.data.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public enum ChatOption {
    Robot(0.1), Balanced(0.7), Creative(2.0), Painting;
    final private Double temperature;

    ChatOption(double temperature) {
        this.temperature = temperature;
    }
}
