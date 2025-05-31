package com.example.berkutsanzharbot.service.impl;

import com.example.berkutsanzharbot.entity.User;
import com.example.berkutsanzharbot.entity.dto.UserDto;
import com.example.berkutsanzharbot.repository.UserRepository;
import com.example.berkutsanzharbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> registration(UserDto userDto) {

        String password = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setPassword(password);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }


    @Override
    public ResponseEntity<?> loginToken() {

        String token = UUID.randomUUID().toString().substring(0, 6);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        String telegram_token = username + "_" + token;

        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username));

            if (user.getTelegramToken() == null){
                user.setTelegramToken(telegram_token);
                userRepository.save(user);
                return ResponseEntity.ok(telegram_token);
            }else {
                return ResponseEntity
                        .status(409)
                        .body("Для него Телеграм токен существует");
            }




    }
}
