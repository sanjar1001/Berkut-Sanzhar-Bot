package com.example.berkutsanzharbot.controller;


import com.example.berkutsanzharbot.entity.dto.UserDto;
import com.example.berkutsanzharbot.repository.UserRepository;
import com.example.berkutsanzharbot.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserDto userDto ) {

        return userService.registration(userDto);
    }

    @PostMapping("/login/token")
    public ResponseEntity<?> login() {
        return userService.loginToken();
    }





}
