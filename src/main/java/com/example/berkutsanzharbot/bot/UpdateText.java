package com.example.berkutsanzharbot.bot;

import com.example.berkutsanzharbot.entity.User;
import com.example.berkutsanzharbot.repository.MessageRepository;
import com.example.berkutsanzharbot.repository.UserRepository;
import com.example.berkutsanzharbot.service.TelegramService;
import com.example.berkutsanzharbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateText implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient = new OkHttpTelegramClient(
            "7651372603:AAHdclFmWSHgmIUMYLY5tjZ8kbtWgFEV7ec");
    private final UserService userService;
    private void sendMessageSafe(SendMessage sendMessage) {
        try {
            telegramClient.execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
            // Можно сюда добавить логирование или другую обработку ошибок
        }
    }
    private final UserRepository userRepository;
    private final TelegramService telegramService;

    @Override
    public void consume(org.telegram.telegrambots.meta.api.objects.Update update) {

        String chatId = update.getMessage().getChatId().toString();
        String username = update.getMessage().getFrom().getUserName();
        String text = update.getMessage().getText();
        LocalDateTime now = LocalDateTime.now();

        Optional<User> optionalUser = userRepository.findByChatId(chatId);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();
            sendMessageSafe(new SendMessage(chatId, username + ", я получил от тебя сообщение:\n" + text));
            telegramService.saveMessage(chatId, now, text);

        } else {
            if (text.contains("_")) {

                ResponseEntity<User> response = telegramService.saveChatId(chatId, text);
                if (response.getStatusCode().is2xxSuccessful()) {

                    sendMessageSafe(new SendMessage(chatId, "✅ Токен привязан. Теперь можешь писать!"));
                } else {
                    sendMessageSafe(new SendMessage(chatId, "❌ Такой токен не найден. Отправьте заново."));
                }
            } else {
                sendMessageSafe(new SendMessage(chatId, "💬 Чат не связан. Отправь свой токен."));
            }
        }




    }


    }


