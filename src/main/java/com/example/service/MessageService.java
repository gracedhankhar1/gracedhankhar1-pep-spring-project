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
        (accountRepository.findAccountByAccountId(message.getPostedBy()) == null)){
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
        if(messageRepository.findMessageById(id) != null){
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    public Integer updateMessageById(Integer id, Message message){
        if((messageRepository.findMessageById(id) != null) && (!message.getMessageText().isBlank()) &&
        (message.getMessageText() != null) && (message.getMessageText().length() <= 255)){
            messageRepository.updateMessageById(message.getMessageText(), id);
            return 1;
        }
        return 0;
    }

    public List<Message> getMessagesByUserId(Integer userId){
        return messageRepository.findMessageByUserId(userId);
    }
}
