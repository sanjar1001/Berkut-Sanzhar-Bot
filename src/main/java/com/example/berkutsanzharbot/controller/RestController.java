package com.example.berkutsanzharbot.controller;


import com.example.berkutsanzharbot.entity.dto.MessageDto;
import com.example.berkutsanzharbot.entity.dto.UserDto;
import com.example.berkutsanzharbot.service.TelegramService;
import com.example.berkutsanzharbot.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {

    private final UserService userService;
    private final TelegramService telegramService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserDto userDto ) {
        return userService.registration(userDto);

    }

    @PostMapping("/login/token")
    public ResponseEntity<?> login() {
        return userService.loginToken();

    }

    @PostMapping("/send/message")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto) {
        return telegramService.sendMessage(messageDto);

    }

    @GetMapping("/find/all/message")
    public ResponseEntity<?> findAllMessage() {
        return telegramService.findAllMessage();

    }

}
