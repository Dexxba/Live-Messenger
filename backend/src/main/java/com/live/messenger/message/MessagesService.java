package com.live.messenger.message;
import com.live.messenger.user.UserDtoSender;
import com.live.messenger.user.Users;
import com.live.messenger.user.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private UsersRepository usersRepository;

    public MessageDto getMessageDto(int id) {
        Optional<Messages> messageOpt = messagesRepository.findById(id);
        if (messageOpt.isPresent()) {
            Messages message = messageOpt.get();
            MessageDto messageDto = new MessageDto();
            messageDto.setSenderId(message.getSender().getId());
            messageDto.setRecipientId(message.getRecipient().getId());
            messageDto.setContent(message.getContent());
            messageDto.setTimestamp(message.getTimestamp());
            return messageDto;
        }
        return null;
    }

    public Messages createMessage(MessageDto messageDto) {
        Users sender = usersRepository.findById(messageDto.getSenderId()).orElse(null);
        Users recipient = usersRepository.findById(messageDto.getRecipientId()).orElse(null);
        if (sender == null) {
            return null; // Sender not found
        }

        Messages newMessage = new Messages();
        newMessage.setSender(sender);
        newMessage.setRecipient(recipient);
        newMessage.setContent(messageDto.getContent());
        newMessage.setTimestamp(new Timestamp(System.currentTimeMillis())); // Set current timestamp
        return messagesRepository.save(newMessage);
    }

    public Messages updateMessage(int id, MessageDto messageDto) {
        Optional<Messages> messageOpt = messagesRepository.findById(id);
        if (messageOpt.isEmpty()) {
            return null; // Message not found
        }

        Messages existingMessage = messageOpt.get();
        Users sender = usersRepository.findById(messageDto.getSenderId()).orElse(null);
        Users recipient = usersRepository.findById(messageDto.getRecipientId()).orElse(null);
        if (sender == null) {
            return null; // Sender not found
        }

        existingMessage.setSender(sender);
        existingMessage.setRecipient(recipient);
        existingMessage.setContent(messageDto.getContent());
        existingMessage.setTimestamp(new Timestamp(System.currentTimeMillis())); // Update timestamp
        return messagesRepository.save(existingMessage);
    }

    public void deleteMessage(int id) {
        messagesRepository.deleteById(id);
    }

    public List<MessageDto> getMessages() {
        // Retrieve a list of all messages from the repository, map them to MessageDto and return it.
        return messagesRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MessageDto convertToDto(Messages message) {
        // Convert Messages to MessageDto
        MessageDto messageDto = new MessageDto();
        messageDto.setSenderId(message.getSender().getId());
        messageDto.setRecipientId(message.getRecipient() != null ? message.getRecipient().getId() : 0); // Assuming recipientId can be 0 if null
        messageDto.setContent(message.getContent());
        messageDto.setTimestamp(message.getTimestamp());

        // Convert Users to UserDtoSender
        UserDtoSender userDtoSender = new UserDtoSender();
        userDtoSender.setFirstName(message.getSender().getFirstName());
        userDtoSender.setLastName(message.getSender().getLastName());
        userDtoSender.setProfilePicture(message.getSender().getProfilePicture());

        messageDto.setUserDtoSender(userDtoSender);

        return messageDto;
    }
}

