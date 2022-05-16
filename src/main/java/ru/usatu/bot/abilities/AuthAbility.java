package ru.usatu.bot.abilities;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.usatu.bot.domain.UserInfo;
import ru.usatu.bot.domain.UserInfo.UserPK;
import ru.usatu.bot.domain.dtos.osticket.OsTicketUsers;
import ru.usatu.bot.domain.dtos.osticket.OsTicketUsers.OsTicketUser;
import ru.usatu.bot.domain.dtos.telegram.TelegramUser;
import ru.usatu.bot.mappers.api.Mapper;
import ru.usatu.bot.repositories.UserInfoRepository;
import ru.usatu.bot.services.api.AuthenticationService;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static ru.usatu.bot.domain.dtos.telegram.CommandType.AUTH;

@Component
@RequiredArgsConstructor
public final class AuthAbility implements AbilityExtension {

    private final Mapper<User, TelegramUser> userMapper;
    private final UserInfoRepository userInfoRepository;
    private final AuthenticationService authService;

    @SuppressWarnings("unused")
    public Ability start() {
        return Ability.builder()
                .action(this::authenticate)
                .name(AUTH.getText())
                .input(1)
                .privacy(PUBLIC)
                .locality(USER)
                .build();
    }

    private void authenticate(MessageContext ctx) {
        var osTicketUserId = ctx.firstArg();
        if (!hasText(osTicketUserId)) return;

        authService.findOsTicketUserById(osTicketUserId).ifPresentOrElse(
                osTicketUser -> {
                    var telegramUser = userMapper.map(ctx.user());
                    saveUserInformation(telegramUser, osTicketUser);
                    sendKeyboard(ctx);
                },
                () -> sendMessage(ctx, "Неверный идентификатор пользователя")
        );
    }

    @SneakyThrows
    private void sendKeyboard(MessageContext ctx) {
        var row = new KeyboardRow();
        row.add(KeyboardButton.builder()
                .text("/all_tickets")
                .build());

        var keyboardMarkup = ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row))
                .resizeKeyboard(true)
                .build();

        ctx.bot().sender().execute(SendMessage.builder()
                .chatId(ctx.chatId().toString())
                .text("Вы успешно авторизованы")
                .replyMarkup(keyboardMarkup)
                .build());
    }

    private UserInfo saveUserInformation(TelegramUser telegramUser, OsTicketUser osTicketUser) {
        var userInfo = buildUserInfo(telegramUser, osTicketUser);
        return userInfoRepository.save(userInfo);
    }

    private UserInfo buildUserInfo(TelegramUser telegramUser, OsTicketUsers.OsTicketUser osTicketUser) {
        return UserInfo.builder()
                .userId(UserPK.builder()
                        .osTicketId(osTicketUser.userId())
                        .telegramId(telegramUser.userId())
                        .build())
                .createdAt(osTicketUser.createdAt())
                .firstName(telegramUser.firstName())
                .lastName(telegramUser.lastName())
                .userName(telegramUser.userName())
                .systemName(osTicketUser.name())
                .build();
    }

    @SneakyThrows
    private void sendMessage(MessageContext ctx, String message) {
        ctx.bot().execute(SendMessage.builder()
                .chatId(ctx.chatId().toString())
                .text(message)
                .build());
    }

}