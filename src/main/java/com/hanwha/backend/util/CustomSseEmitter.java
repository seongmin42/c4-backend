package com.hanwha.backend.util;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class CustomSseEmitter extends SseEmitter{

    private static final long TIME_OUT = 60 * 1000L;

    public CustomSseEmitter() {
        super(TIME_OUT);
    }

    @Override
    public void onTimeout(Runnable runnable){
        super.onTimeout(()->{});
    }

}
