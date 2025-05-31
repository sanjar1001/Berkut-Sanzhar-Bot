package com.example.berkutsanzharbot;

import com.example.berkutsanzharbot.bot.TelegramBot;
import com.example.berkutsanzharbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@RequiredArgsConstructor
@SpringBootApplication
public class BerkutSanzharBotApplication {

    public static void main(String[] args) {

        SpringApplication.run(BerkutSanzharBotApplication.class, args);



    }


}
