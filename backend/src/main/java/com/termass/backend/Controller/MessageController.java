package com.termass.backend.Controller;

import com.termass.backend.Entities.Message;
import com.termass.backend.Service.Impl.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{groupId}/messages")
    public ResponseEntity<List<Message>> getMessagesByGroupId(@PathVariable Long groupId) {
        List<Message> messages = messageService.getMessagesByGroupId(groupId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/createMessage")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    // Get message by ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update message
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message updatedMessage) {
        Message message = messageService.getMessageById(id);
        if (message != null) {
            updatedMessage.setId(id); // Ensure the ID is set to the correct value
            message = messageService.updateMessage(updatedMessage);
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete message
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        if (message != null) {
            messageService.deleteMessage(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
