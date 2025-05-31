package com.example.berkutsanzharbot.service;

import com.example.berkutsanzharbot.entity.User;
import com.example.berkutsanzharbot.entity.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface TelegramService {

    ResponseEntity<User> saveChatId(String chatId,String text);

}
