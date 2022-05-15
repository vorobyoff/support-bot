package ru.usatu.bot.domain.dtos.telegram;

import lombok.Builder;

@Builder
public record TelegramUser(Long userId, String firstName, String lastName, String userName) {
}