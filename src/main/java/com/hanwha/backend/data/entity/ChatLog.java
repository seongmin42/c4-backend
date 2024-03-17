package com.hanwha.backend.data.entity;

import com.hanwha.backend.data.IData;
import com.hanwha.backend.data.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalTime;

@Document(collection = "chatlog")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatLog implements IData {
    @Id
    private String _id;
    private String content;
    private boolean verified = true;
    private LocalTime createdAt = LocalTime.now();
    @Enumerated
    private Role role;
    private String user;
    private String roomId;
    private boolean isDeleted = false;

    public ChatLog(String content, Role role, String user, String roomId, boolean verified) {
        this.content = content;
        this.role = role;
        this.user = user;
        this.roomId = roomId;
        this.verified = verified;
    }
}
