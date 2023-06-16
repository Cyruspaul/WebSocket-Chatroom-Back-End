package com.websocket.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDAO extends CrudRepository<ChatMessage, String> {
}
