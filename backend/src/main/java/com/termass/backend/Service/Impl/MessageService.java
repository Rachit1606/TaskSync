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

    public Message getMessageById(String id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        return optionalMessage.orElse(null);
    }

    public Message updateMessage(Message message) {
        return messageRepository.save(message);
    }

    public void deleteMessage(String id) {
        messageRepository.deleteById(id);
    }
}

