package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message){
        if((message.getMessageText().isBlank()) || (message.getMessageText() == null) || 
        (message.getMessageText().length() > 255) || 
        (accountRepository.findByUsername(message.getPostedBy().toString()) == null)){
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAllMessages();
    }

    public Message getMessageById(Integer id){
        return messageRepository.findMessageById(id);
    }

    public Integer deleteMessageById(Integer id){
        return messageRepository.deleteMessageById(id);
    }
}
