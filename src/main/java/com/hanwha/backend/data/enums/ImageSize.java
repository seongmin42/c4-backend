package com.hanwha.backend.data.enums;

import lombok.Getter;

@Getter
public enum ImageSize {
    SMALL("256x256"), MEDIUM("512x512"), LARGE("1024x1024");
    final private String size;
    ImageSize(String size) {
        this.size = size;
    }
}
