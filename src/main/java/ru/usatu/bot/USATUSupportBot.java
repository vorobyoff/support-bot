package ru.usatu.bot;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.usatu.bot.events.UpdateEvent;
import ru.usatu.bot.poperties.TelegramBotProperties;

@Component
@AllArgsConstructor
public final class USATUSupportBot extends TelegramLongPollingBot {

    private final TelegramBotProperties botProperties;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public String getBotUsername() {
        return botProperties.username();
    }

    @Override
    public String getBotToken() {
        return botProperties.token();
    }

    @Override
    public void onUpdateReceived(Update update) {
        UpdateEvent updateEvent = new UpdateEvent(update);
        eventPublisher.publishEvent(updateEvent);
    }

}