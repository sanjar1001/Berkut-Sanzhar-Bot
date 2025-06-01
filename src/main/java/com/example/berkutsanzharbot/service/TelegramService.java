package com.example.berkutsanzharbot.service;

import com.example.berkutsanzharbot.entity.User;
import com.example.berkutsanzharbot.entity.dto.MessageDto;
import com.example.berkutsanzharbot.entity.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface TelegramService {

    ResponseEntity<User> saveChatId(String chatId, String text);

    ResponseEntity<Void> saveMessage(String chatId, LocalDateTime now, String text);

    ResponseEntity<?> sendMessage(MessageDto messageDto);

    ResponseEntity<?> findAllMessage();
}
