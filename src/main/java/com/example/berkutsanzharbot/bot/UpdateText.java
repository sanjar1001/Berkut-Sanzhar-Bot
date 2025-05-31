package com.example.berkutsanzharbot.bot;

import com.example.berkutsanzharbot.entity.User;
import com.example.berkutsanzharbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateText implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient = new OkHttpTelegramClient(
            "7651372603:AAHdclFmWSHgmIUMYLY5tjZ8kbtWgFEV7ec");
    private final UserRepository userRepository;


    @Override
    public void consume(org.telegram.telegrambots.meta.api.objects.Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String username = update.getMessage().getFrom().getUserName();
        System.out.println(chatId);
        String text = update.getMessage().getText();

// 1. Ищем по chatId
        Optional<User> optionalUser = userRepository.findByChatId(chatId);

        if (optionalUser.isPresent()) {
            // Если пользователь уже связан — просто эхо
            User user = optionalUser.get();
            SendMessage sendMessage = new SendMessage(chatId, username + ", я получил от тебя сообщение:\n" + text);
            try {
                telegramClient.execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // Пользователь НЕ найден по chatId — проверим, может он прислал токен
            if (text.contains("_")) {
                Optional<User> userByToken = userRepository.findByTelegramToken(text);
                if (userByToken.isPresent()) {
                    User user = userByToken.get();
                    user.setChatId(chatId);
                    userRepository.save(user);

                    SendMessage sendMessage = new SendMessage(chatId, "✅ Токен привязан. Теперь можешь писать!");
                    try {
                        telegramClient.execute(sendMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    // Токен не найден в базе
                    SendMessage sendMessage = new SendMessage(chatId, "❌ Такой токен не найден. Проверь правильность.");
                    try {
                        telegramClient.execute(sendMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {
                // Просто неизвестное сообщение от незарегистрированного пользователя
                SendMessage sendMessage = new SendMessage(chatId, "💬 Чат не связан. Отправь свой токен с подчёркиванием, например: `Sanjar_abc123`");
                try {
                    telegramClient.execute(sendMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    }