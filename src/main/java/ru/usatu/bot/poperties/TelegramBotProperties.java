package ru.usatu.bot.poperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("telegram.bot")
public record TelegramBotProperties(String token, String username, long creatorId) {
}