package com.termass.backend.Service.Impl;

import com.termass.backend.Entities.Message;
import com.termass.backend.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByGroupId(String groupId) {
        return messageRepository.findByGroupId(groupId);
    }

    public List<Message> getMessagesByFilters(String groupId, String status, String assignee) {
        if (status != null && assignee != null) {
            return messageRepository.findByGroupIdAndStatusAndAssigneesContaining(groupId, status, assignee);
        } else if (status != null) {
            return messageRepository.findByGroupIdAndStatus(groupId, status);
        } else if (assignee != null) {
            return messageRepository.findByGroupIdAndAssigneesContaining(groupId, assignee);
        } else {
            return messageRepository.findByGroupId(groupId);
        }
    }

    public Message getMessageById(String id) {
        return messageRepository.findById(id).orElse(null);
    }

    public Message updateMessage(Message updatedMessage) {
        return messageRepository.save(updatedMessage);
    }

    public void deleteMessage(String id) {
        messageRepository.deleteById(id);
    }
}
