package ru.usatu.bot.dtos.telegram;

import lombok.Builder;

@Builder
public record Command(Long chatId, CommandType type, TelegramUser user) {
}