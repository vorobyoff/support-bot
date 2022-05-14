package ru.usatu.bot.listeners;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.usatu.bot.dtos.telegram.Command;
import ru.usatu.bot.events.UpdateEvent;
import ru.usatu.bot.listeners.base.CommandListener;
import ru.usatu.bot.mappers.api.Mapper;

import java.util.List;

import static java.text.MessageFormat.format;
import static ru.usatu.bot.dtos.telegram.CommandType.START;

@Component
public final class StartCommandListener extends CommandListener {

    private final TelegramLongPollingBot bot;

    public StartCommandListener(Mapper<UpdateEvent, List<Command>> commandsMapper, TelegramLongPollingBot bot) {
        super(commandsMapper);
        this.bot = bot;
    }

    @Override
    @SneakyThrows
    protected void handle(Command command) {
        var type = command.type();
        if (type != START) return;

        bot.execute(SendSticker.builder()
                .sticker(new InputFile("CAACAgIAAxkBAAEErPRidqr-GJExJkWzOovKMIR9pAF8MgACMAEAAqtXxAtP62N9ym6CbyQE"))
                .chatId(command.chatId().toString())
                .build());

        bot.execute(SendMessage.builder()
                .chatId(command.chatId().toString())
                .text(format("Привет {0}! Введи id пользователя, выданное администратором системы OsTicket, " +
                        "что бы я смог полноценно работать.", command.user().firstName()
                )).build());
    }

}