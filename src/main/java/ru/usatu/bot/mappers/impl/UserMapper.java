package ru.usatu.bot.mappers.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.usatu.bot.dtos.UserInfo;
import ru.usatu.bot.mappers.api.Mapper;

import static java.util.Objects.isNull;

@Component
public final class UserMapper implements Mapper<User, UserInfo> {

    @Override
    public UserInfo map(User user) {
        if (isNull(user)) return null;

        return UserInfo.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .userId(user.getId())
                .build();
    }
}