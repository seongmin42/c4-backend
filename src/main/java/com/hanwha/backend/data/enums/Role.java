package com.hanwha.backend.data.enums;

import lombok.Getter;

@Getter
public enum Role {
    system(0), user(1), assistant(2), function(3), dalle(4);

    final private int code;
    Role(int code) {
        this.code = code;
    }
}
