package com.live.messenger.webSocket;

import com.live.messenger.message.MessageDto;
import com.live.messenger.message.Messages;
import com.live.messenger.message.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private MessagesService messagesService;

    @MessageMapping("/chat/sendMessage")
    @SendTo("/topic/public")
    public MessageDto sendMessage(MessageDto messageDto) {
        Messages messages = messagesService.createMessage(messageDto); // Sačuvaj poruku u bazi podataka
        return messagesService.convertToDto(messages);
    }

    @MessageMapping("/chat/addUser")
    @SendTo("/topic/public")
    public MessageDto addUser(MessageDto messageDto, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", messageDto.getUserDtoSender().getFirstName());
        return messageDto;
    }
}

