package ru.usatu.bot.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.usatu.bot.domain.dtos.telegram.Command;
import ru.usatu.bot.domain.dtos.telegram.CommandType;
import ru.usatu.bot.domain.dtos.telegram.TelegramUser;
import ru.usatu.bot.events.UpdateEvent;
import ru.usatu.bot.mappers.api.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.EnumSet.noneOf;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toCollection;

@Component
@RequiredArgsConstructor
public final class CommandsMapper implements Mapper<UpdateEvent, List<Command>> {

    private final Mapper<User, TelegramUser> userInfoMapper;

    @Override
    public List<Command> map(UpdateEvent event) {
        if (isNull(event) || event.message().isEmpty()) return emptyList();

        var message = event.message().orElseThrow();
        var commandTypes = mapCommandTypes(message.getEntities());
        var userInfo = userInfoMapper.map(message.getFrom());

        if (isNull(userInfo)) return emptyList();
        return commandTypes.stream()
                .map(it -> Command.builder()
                        .chatId(message.getChatId())
                        .user(userInfo)
                        .type(it)
                        .build())
                .toList();
    }

    private Set<CommandType> mapCommandTypes(List<MessageEntity> entities) {
        if (isNull(entities) || entities.isEmpty()) return emptySet();

        return entities.stream()
                .map(MessageEntity::getText)
                .map(CommandType::from)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toCollection(() -> noneOf(CommandType.class)));
    }

}