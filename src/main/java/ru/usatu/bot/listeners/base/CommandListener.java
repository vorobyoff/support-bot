package ru.usatu.bot.listeners.base;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.usatu.bot.dtos.telegram.Command;
import ru.usatu.bot.events.UpdateEvent;
import ru.usatu.bot.mappers.api.Mapper;

import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public abstract class CommandListener implements ApplicationListener<UpdateEvent> {

    private final Mapper<UpdateEvent, List<Command>> commandsMapper;

    @Override
    public void onApplicationEvent(UpdateEvent event) {
        event.message().ifPresent(it -> {
            if (hasBotCommands(it)) {
                var botCommands = commandsMapper.map(event);
                if (isNull(botCommands) || botCommands.isEmpty()) return;
                for (var botCommand : botCommands)
                    handle(botCommand);
            }
        });
    }

    protected abstract void handle(Command command);

    private boolean hasBotCommands(Message message) {
        if (!message.hasEntities()) return false;
        return message.getEntities().stream()
                .anyMatch(it -> "bot_command".equals(it.getType()));
    }

}