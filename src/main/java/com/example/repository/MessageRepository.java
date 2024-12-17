package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

    @Query("select m from Message m")
    List<Message> findAllMessages();

    @Query("select m from Message m where m.messageId = ?1")
    Message findMessageById(Integer messageId);

    @Transactional
    @Query("delete from Message m where m.messageId = ?1")
    void deleteMessageByMessageId(Integer messageId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Message m set m.messageText =:messageText where m.messageId =:messageId")
    void updateMessageById(@Param("messageText") String messageText, @Param("messageId") Integer messageId);

    @Query("select m from Message m where m.postedBy = ?1")
    List<Message> findMessageByUserId(Integer postedBy);
}
