package com.hanwha.backend.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CustomWebClient {

    @Value("${gpt.api-url}")
    private String gptApiUrl;

    @Value("${gpt.secret}")
    private String openaiApiKey;

    @Bean
    @Qualifier("openaiWebClient")
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(gptApiUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Bearer " + openaiApiKey)
                .build();
    }

}
