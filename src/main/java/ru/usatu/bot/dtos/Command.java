package ru.usatu.bot.dtos;

import lombok.Builder;

@Builder
public record Command(Long chatId, CommandType type, UserInfo user) {
}