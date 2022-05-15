package ru.usatu.bot.domain.dtos.telegram;

import lombok.Builder;

@Builder
public record Command(Long chatId, CommandType type, TelegramUser user) {
}