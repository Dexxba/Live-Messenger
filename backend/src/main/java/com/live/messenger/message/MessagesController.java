package com.live.messenger.message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody MessageDto messageDto) {
        Messages newMessage = messagesService.createMessage(messageDto);
        if (newMessage != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Message created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create message");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMessage(@PathVariable int id, @RequestBody MessageDto messageDto) {
        Messages updatedMessage = messagesService.updateMessage(id, messageDto);
        if (updatedMessage != null) {
            return ResponseEntity.ok("Message with ID " + id + " has been updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message with ID " + id + " not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int id) {
        messagesService.deleteMessage(id);
        return ResponseEntity.ok("Message with ID " + id + " has been deleted");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessageById(@PathVariable int id) {
        MessageDto messageDto = messagesService.getMessageDto(id);
        if (messageDto != null) {
            return ResponseEntity.ok(messageDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message with ID " + id + " not found");
        }
    }
    @GetMapping("/list")
    public List<MessageDto> getMessages(){
        return messagesService.getMessages();
    }
}
