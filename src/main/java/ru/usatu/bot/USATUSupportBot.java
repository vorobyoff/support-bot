package ru.usatu.bot;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.util.AbilityExtension;
import ru.usatu.bot.poperties.TelegramBotProperties;

import java.util.List;

@Component
public final class USATUSupportBot extends AbilityBot {

    private final TelegramBotProperties botProperties;

    USATUSupportBot(TelegramBotProperties botProperties, List<AbilityExtension> extensions) {
        super(botProperties.token(), botProperties.username());
        this.botProperties = botProperties;
        addExtensions(extensions);
    }

    @Override
    public long creatorId() {
        return botProperties.creatorId();
    }

}