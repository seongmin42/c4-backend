package com.hanwha.backend.config;

import com.hanwha.backend.repository.ChatLogRepository;
import com.hanwha.backend.util.converter.Converter;
import com.hanwha.backend.util.converter.UserChatReqDtoToChatLog;
import com.hanwha.backend.util.converter.UserChatReqDtoToChatReqDto;
import com.hanwha.backend.util.converter.UserChatReqDtoToPaintingReqDto;
import com.hanwha.backend.util.verifier.BuiltinVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {
    private final BuiltinVerifier verifier;
    private final ChatLogRepository chatLogRepository;
    @Autowired
    public ConverterConfig(BuiltinVerifier verifier, ChatLogRepository chatLogRepository){
        this.verifier = verifier;
        this.chatLogRepository = chatLogRepository;
    }
    @Bean
    public Converter userChatReqDtoToChatLog(){
        return new UserChatReqDtoToChatLog(verifier);
    }

    @Bean
    public Converter userChatReqToChatReqDto(){
        return new UserChatReqDtoToChatReqDto(chatLogRepository);
    }

    @Bean
    public Converter userChatReqDtoToPaintingReqDto(){
        return new UserChatReqDtoToPaintingReqDto();
    }

}
