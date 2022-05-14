package ru.usatu.bot.dtos.telegram;

import lombok.Builder;

@Builder
public record TelegramUser(Long userId, String firstName, String lastName, String userName) {
}