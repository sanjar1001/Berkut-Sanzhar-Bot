package com.example.berkutsanzharbot.service.impl;

import com.example.berkutsanzharbot.entity.User;
import com.example.berkutsanzharbot.entity.dto.UserDto;
import com.example.berkutsanzharbot.exception.NotFoundException;
import com.example.berkutsanzharbot.repository.UserRepository;
import com.example.berkutsanzharbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@RestControllerAdvice
public class ServiceImpl implements UserService {

    private final UserRepository userRepository;

    //Регистрация пользователя
    public ResponseEntity<?> registration(UserDto userDto) {

        User user = userRepository.findByUsername(userDto.username)
                .orElseThrow(() -> new NotFoundException(userDto.username + "Это имя уже занят!"));


        String password = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());

        User user1 = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setPassword(password);
        userRepository.save(user1);

        return ResponseEntity.ok(user1);
    }


    //Получение токена после авторизации
    public ResponseEntity<?> loginToken() {

        String token = UUID.randomUUID().toString().substring(0, 6);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        String telegram_token = username + "_" + token;

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        if (user.getTelegramToken() == null) {
            user.setTelegramToken(telegram_token);
            userRepository.save(user);
            return ResponseEntity.ok(telegram_token);
        } else {
            throw new NotFoundException("У вас уже есть Telegram токен");
        }
    }

}
