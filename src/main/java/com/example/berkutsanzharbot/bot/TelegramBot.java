package com.example.berkutsanzharbot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.meta.generics.TelegramClient;


@Component
@RequiredArgsConstructor
public class TelegramBot implements SpringLongPollingBot {

    private final UpdateText update;

    @Override
    public String getBotToken() {
        return "7651372603:AAHdclFmWSHgmIUMYLY5tjZ8kbtWgFEV7ec";
    }


    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
            return update;
    }

}
