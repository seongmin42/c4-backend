package com.hanwha.backend.data.dto.request;

import com.hanwha.backend.data.IData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatRequestDto implements IData {
    private String model;
    private List<Message> messages;
    private double temperature;
    private boolean stream = true;

    public ChatRequestDto(String model, List<Message> messages, Double temperature) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "ChatRequestDto{" +
                "model='" + model + '\'' +
                ", messages=" + messages.toString() +
                ", temperature=" + temperature +
                '}';
    }

}
