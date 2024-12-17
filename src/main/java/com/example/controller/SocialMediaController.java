package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAccount(@RequestBody Account account){
        if(accountService.checkIfUsernameExists(account) != null){
            return ResponseEntity.status(409).body(null);
        } else {
            Account registered = accountService.createAccount(account);
            if(registered == null){
                return ResponseEntity.status(400).body(null);
            } else {
                return ResponseEntity.status(200).body(registered.toString());
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account loggedIn = accountService.loginAccount(account);
        if(loggedIn == null){
            return ResponseEntity.status(401).build();
        } else{
            return ResponseEntity.status(200).body(loggedIn);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage == null){
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(200).body(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(allMessages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessagesById(@PathVariable Integer messageId){
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.status(200).body(message);
    }
 
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessagesById(@PathVariable Integer messageId){
        if(messageService.getMessageById(messageId) == null){
            return ResponseEntity.status(200).build();
        } else {
            Integer rows = messageService.deleteMessageById(messageId);
            return ResponseEntity.status(200).body(rows);
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer messageId, @RequestBody Message message){
        if(messageService.updateMessageById(messageId, message) == 0){
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.status(200).body(messageService.updateMessageById(messageId, message));
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer accountId){
        return ResponseEntity.status(200).body(messageService.getMessagesByUserId(accountId));
    }
}
