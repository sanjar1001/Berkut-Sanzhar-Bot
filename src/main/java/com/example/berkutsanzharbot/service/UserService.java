package com.example.berkutsanzharbot.service;

import com.example.berkutsanzharbot.entity.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> registration(UserDto userDto);

    ResponseEntity<?> loginToken();

}
