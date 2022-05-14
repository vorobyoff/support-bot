package ru.usatu.bot.mappers.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.usatu.bot.dtos.telegram.TelegramUser;
import ru.usatu.bot.mappers.api.Mapper;

import static java.util.Objects.isNull;

@Component
public final class TelegramUserMapper implements Mapper<User, TelegramUser> {

    @Override
    public TelegramUser map(User user) {
        if (isNull(user)) return null;

        return TelegramUser.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .userId(user.getId())
                .build();
    }
}