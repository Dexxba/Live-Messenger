package com.live.messenger.message;

import com.live.messenger.user.Users;
import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * Entity class representing a message.
 * This class maps to the "messages" table in the database.
 */
@Entity
@Table(name = "messages")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Unique identifier for the message

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Users sender; // The sender of the message

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = true)
    private Users recipient; // The recipient of the message (can be null)

    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    private String content; // The content of the message

    @Column(nullable = false)
    private Timestamp timestamp; // The timestamp of when the message was sent

    // Getters and Setters

    /**
     * Gets the unique identifier for the message.
     *
     * @return the message ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the message.
     *
     * @param id the message ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the sender of the message.
     *
     * @return the sender
     */
    public Users getSender() {
        return sender;
    }

    /**
     * Sets the sender of the message.
     *
     * @param sender the sender to set
     */
    public void setSender(Users sender) {
        this.sender = sender;
    }

    /**
     * Gets the recipient of the message.
     *
     * @return the recipient
     */
    public Users getRecipient() {
        return recipient;
    }

    /**
     * Sets the recipient of the message.
     *
     * @param recipient the recipient to set
     */
    public void setRecipient(Users recipient) {
        this.recipient = recipient;
    }

    /**
     * Gets the content of the message.
     *
     * @return the content
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
     * @return the timestamp
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
     * Returns a string representation of the Messages object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
