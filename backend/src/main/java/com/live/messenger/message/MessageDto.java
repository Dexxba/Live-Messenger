package com.live.messenger.message;

import com.live.messenger.user.UserDtoSender;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;

public class MessageDto {

    @NotBlank
    private int senderId;

    private int recipientId;

    @NotBlank
    private String content;

    private Timestamp timestamp;

    private UserDtoSender userDtoSender;

    public UserDtoSender getUserDtoSender() {
        return userDtoSender;
    }

    public void setUserDtoSender(UserDtoSender userDtoSender) {
        this.userDtoSender = userDtoSender;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
