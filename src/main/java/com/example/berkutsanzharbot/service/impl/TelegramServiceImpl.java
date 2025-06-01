package com.example.berkutsanzharbot.service.impl;

import com.example.berkutsanzharbot.entity.Message;
import com.example.berkutsanzharbot.entity.User;
import com.example.berkutsanzharbot.entity.dto.MessageAllDto;
import com.example.berkutsanzharbot.entity.dto.MessageDto;
import com.example.berkutsanzharbot.exception.NotFoundException;
import com.example.berkutsanzharbot.map.Mapper;
import com.example.berkutsanzharbot.repository.MessageRepository;
import com.example.berkutsanzharbot.repository.UserRepository;
import com.example.berkutsanzharbot.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@RestControllerAdvice
public class TelegramServiceImpl implements TelegramService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final TelegramClient telegramClient = new OkHttpTelegramClient("7651372603:AAHdclFmWSHgmIUMYLY5tjZ8kbtWgFEV7ec");
    private final Mapper mapper;

    // Сохраняем chatId пользователю по его Telegram токену
    public ResponseEntity<User> saveChatId(String chatId, String text) {
        Optional<User> userByToken = userRepository.findByTelegramToken(text);

        if (userByToken.isPresent()) {
            User user = userByToken.get();
            user.setChatId(chatId);
            User savedUser = userRepository.save(user);

            return ResponseEntity.ok(savedUser);
        }
        throw new RuntimeException("Раньше чат был привязан");
    }

    //Сохраняем входящее сообщение от Telegram
    public ResponseEntity<Void> saveMessage(String chatId, LocalDateTime now, String text) {

        Optional<User> userByChatId = userRepository.findByChatId(chatId);
        if (userByChatId.isEmpty()) {
            throw new NotFoundException("ChatId: Пустой невозможно сохранить");
        }
        User user = userByChatId.get();

        Message message = new Message();
        message.setText(text);
        message.setCreatedAt(now);
        message.setUser(user);

        messageRepository.save(message);

        return ResponseEntity.ok().build();

    }

    //Отправляем сообщение по его выданному Токену
    public ResponseEntity<?> sendMessage(MessageDto messageDto) {

        Optional<User> user = userRepository.findByTelegramToken(messageDto.getToken());
        if (user.isEmpty()) {
            throw new NotFoundException("Что бы отправить сообщение, напишите токен");
        }

        String chatId = user.get().getChatId();
        if (chatId == null) {
            throw new NotFoundException("ChatId: Пустой невозможно отправить");
        }
        SendMessage sendMessage = new SendMessage(chatId, messageDto.getText());
        try {
            telegramClient.execute(sendMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при отправке в Telegram");
        }

        Message message = new Message();
        message.setText(messageDto.getText());
        message.setCreatedAt(LocalDateTime.now());
        message.setUser(user.get());
        messageRepository.save(message);

        return ResponseEntity.ok("Сообщение отправлено");
    }

    // Получаем текущего авторизованного пользователя
    // Возвращаем список его сообщений в виде DTO
    public ResponseEntity<?> findAllMessage() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new NotFoundException("Перед тем авторизуйтесь");
        }
        Optional<User> user = userRepository.findByUsername(auth.getName());
        if (user.isEmpty()) {
            throw new NotFoundException("Пользователь  не найдет");
        }
        List<Message> messages = messageRepository.findAllByUserId(user.get().getId());

        List<MessageAllDto> messageDtos = mapper.toMessageAllDtoList(messages);
        return ResponseEntity.ok(messageDtos);


    }

}
