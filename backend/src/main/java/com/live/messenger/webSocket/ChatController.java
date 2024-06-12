package com.live.messenger.webSocket;

import com.live.messenger.message.MessageDto;
import com.live.messenger.message.Messages;
import com.live.messenger.message.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Controller for handling WebSocket chat messages.
 */
@Controller
public class ChatController {

    @Autowired
    private MessagesService messagesService; // Service for handling message operations

    /**
     * Handles incoming chat messages, saves them to the database, and broadcasts them to all subscribers.
     *
     * @param messageDto the message data transfer object containing the message details
     * @return the message DTO after saving to the database
     */
    @MessageMapping("/chat/sendMessage")
    @SendTo("/topic/public")
    public MessageDto sendMessage(MessageDto messageDto) {
        Messages messages = messagesService.createMessage(messageDto); // Save message to the database
        return messagesService.convertToDto(messages); // Convert the saved message to DTO and return it
    }

    /**
     * Handles adding a new user to the chat, associates the user's name with the WebSocket session, and broadcasts a join message.
     *
     * @param messageDto     the message data transfer object containing the user details
     * @param headerAccessor the WebSocket message header accessor
     * @return the message DTO containing the user details
     */
    @MessageMapping("/chat/addUser")
    @SendTo("/topic/public")
    public MessageDto addUser(MessageDto messageDto, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", messageDto.getUserDtoSender().getFirstName()); // Add username to WebSocket session attributes
        return messageDto; // Return the message DTO
    }
}
