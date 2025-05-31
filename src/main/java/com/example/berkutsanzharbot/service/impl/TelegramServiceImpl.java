package com.example.berkutsanzharbot.service.impl;

import com.example.berkutsanzharbot.entity.User;
import com.example.berkutsanzharbot.repository.UserRepository;
import com.example.berkutsanzharbot.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private final UserRepository userRepository;

    public ResponseEntity<User> saveChatId(String chatId, String text) {
        Optional<User> userByToken = userRepository.findByTelegramToken(text);
            if (userByToken.isPresent()) {
                User user = userByToken.get();
                user.setChatId(chatId);
                User savedUser = userRepository.save(user);
                return ResponseEntity.ok(savedUser);
            }
        return ResponseEntity.notFound().build();
    }




}
