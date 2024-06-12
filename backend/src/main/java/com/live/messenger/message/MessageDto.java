package com.live.messenger.message;

import com.live.messenger.user.UserDtoSender;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * Data Transfer Object (DTO) for messages.
 * This class is used to encapsulate the message data transferred between the client and server.
 */
public class MessageDto {

    @NotBlank
    private int senderId; // ID of the message sender

    private int recipientId; // ID of the message recipient

    @NotBlank
    private String content; // Content of the message

    private Timestamp timestamp; // Timestamp of when the message was sent

    private UserDtoSender userDtoSender; // DTO for the sender user details

    /**
     * Gets the sender user details.
     *
     * @return the sender user details
     */
    public UserDtoSender getUserDtoSender() {
        return userDtoSender;
    }

    /**
     * Sets the sender user details.
     *
     * @param userDtoSender the sender user details to set
     */
    public void setUserDtoSender(UserDtoSender userDtoSender) {
        this.userDtoSender = userDtoSender;
    }

    /**
     * Gets the sender ID.
     *
     * @return the sender ID
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * Sets the sender ID.
     *
     * @param senderId the sender ID to set
     */
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    /**
     * Gets the recipient ID.
     *
     * @return the recipient ID
     */
    public int getRecipientId() {
        return recipientId;
    }

    /**
     * Sets the recipient ID.
     *
     * @param recipientId the recipient ID to set
     */
    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    /**
     * Gets the content of the message.
     *
     * @return the content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the message.
     *
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the timestamp of when the message was sent.
     *
     * @return the timestamp of when the message was sent
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of when the message was sent.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns a string representation of the MessageDto.
     *
     * @return a string representation of the object
     */
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
