package com.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.entity.Message;

public interface MessageRepo extends JpaRepository<Message, Integer>{

}
