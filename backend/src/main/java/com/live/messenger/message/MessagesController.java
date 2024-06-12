package com.live.messenger.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling message-related operations.
 */
@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService; // Service for handling message operations

    /**
     * Endpoint for creating a new message.
     *
     * @param messageDto the message data transfer object containing the message details
     * @return a ResponseEntity with the status of the message creation
     */
    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody MessageDto messageDto) {
        Messages newMessage = messagesService.createMessage(messageDto);
        if (newMessage != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Message created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create message");
        }
    }

    /**
     * Endpoint for updating an existing message.
     *
     * @param id         the ID of the message to update
     * @param messageDto the message data transfer object containing the updated message details
     * @return a ResponseEntity with the status of the message update
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMessage(@PathVariable int id, @RequestBody MessageDto messageDto) {
        Messages updatedMessage = messagesService.updateMessage(id, messageDto);
        if (updatedMessage != null) {
            return ResponseEntity.ok("Message with ID " + id + " has been updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message with ID " + id + " not found");
        }
    }

    /**
     * Endpoint for deleting an existing message.
     *
     * @param id the ID of the message to delete
     * @return a ResponseEntity with the status of the message deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int id) {
        messagesService.deleteMessage(id);
        return ResponseEntity.ok("Message with ID " + id + " has been deleted");
    }

    /**
     * Endpoint for retrieving a message by its ID.
     *
     * @param id the ID of the message to retrieve
     * @return a ResponseEntity containing the message details or an error message if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getMessageById(@PathVariable int id) {
        MessageDto messageDto = messagesService.getMessageDto(id);
        if (messageDto != null) {
            return ResponseEntity.ok(messageDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message with ID " + id + " not found");
        }
    }

    /**
     * Endpoint for retrieving all messages.
     *
     * @return a list of message data transfer objects
     */
    @GetMapping("/list")
    public List<MessageDto> getMessages() {
        return messagesService.getMessages();
    }
}
