package ru.usatu.bot.abilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static java.text.MessageFormat.format;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;
import static ru.usatu.bot.domain.dtos.telegram.CommandType.START;

@Slf4j
@Component
public final class StartAbility implements AbilityExtension {

    private static final InputFile STICKER = new InputFile("CAACAgIAAxkBAAEErPRidqr-GJExJkWzOovKMIR9pAF8MgACMAEAAqtXxAtP62N9ym6CbyQE");
    private static final String GREETING = """
            Привет **{0}**!
            Введи команду */auth [OsTicketUserId]*
            (*[OsTicketUserId]* - идентификатор пользователя системы OsTicket).
            """;

    @SuppressWarnings("unused")
    public Ability start() {
        return Ability.builder()
                .action(this::sendSticker)
                .post(this::sendGreeting)
                .name(START.getText())
                .privacy(PUBLIC)
                .locality(USER)
                .build();
    }

    private void sendSticker(MessageContext ctx) {
        try {
            ctx.bot().sender().sendSticker(SendSticker.builder()
                    .chatId(ctx.chatId().toString())
                    .sticker(STICKER)
                    .build());
        } catch (TelegramApiException e) {
            log.error("An error occurred during sending a sticker to the user.", e);
        }
    }

    private void sendGreeting(MessageContext ctx) {
        try {
            ctx.bot().sender().execute(SendMessage.builder()
                    .text(format(GREETING, ctx.user().getFirstName()))
                    .chatId(ctx.chatId().toString())
                    .parseMode(MARKDOWN)
                    .build());
        } catch (TelegramApiException e) {
            log.error("An error occurred during sending a greeting to the user.", e);
        }
    }

}