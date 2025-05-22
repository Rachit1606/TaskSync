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

    // Get messages by groupId, with optional filters for status and assignee
    @GetMapping("/{groupId}/messages")
    public ResponseEntity<List<Message>> getFilteredMessages(
            @PathVariable String groupId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String assignee) {
        List<Message> messages = messageService.getMessagesByFilters(groupId, status, assignee);
        return ResponseEntity.ok(messages);
    }

    // Create a new task
    @PostMapping("/createMessage")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        return ResponseEntity.ok(messageService.createMessage(message));
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable String id) {
        Message message = messageService.getMessageById(id);
        return (message != null) ? ResponseEntity.ok(message) : ResponseEntity.notFound().build();
    }

    // Update task
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable String id, @RequestBody Message updatedMessage) {
        Message existingMessage = messageService.getMessageById(id);
        if (existingMessage != null) {
            updatedMessage.setId(id);
            return ResponseEntity.ok(messageService.updateMessage(updatedMessage));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String id) {
        Message message = messageService.getMessageById(id);
        if (message != null) {
            messageService.deleteMessage(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
