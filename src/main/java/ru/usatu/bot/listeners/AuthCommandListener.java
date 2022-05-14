package ru.usatu.bot.listeners;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.usatu.bot.domain.UserInfo;
import ru.usatu.bot.domain.UserInfo.UserPK;
import ru.usatu.bot.dtos.osticket.OsTicketUsers.OsTicketUser;
import ru.usatu.bot.dtos.telegram.TelegramUser;
import ru.usatu.bot.events.UpdateEvent;
import ru.usatu.bot.mappers.api.Mapper;
import ru.usatu.bot.repositories.UserInfoRepository;
import ru.usatu.bot.services.api.AuthenticationService;

import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public final class AuthCommandListener implements ApplicationListener<UpdateEvent> {

    private final Mapper<User, TelegramUser> userMapper;
    private final UserInfoRepository userInfoRepository;
    private final AuthenticationService authService;
    private final TelegramLongPollingBot bot;

    @Override
    @SneakyThrows
    public void onApplicationEvent(UpdateEvent event) {
        event.message().ifPresent(msg -> {
            if (!msg.hasText() || !hasText(msg.getText())) return;
            var osTicketUserId = msg.getText();
            authService.findOsTicketUserById(osTicketUserId).ifPresentOrElse(
                    usr -> {
                        var telegramUser = userMapper.map(msg.getFrom());
                        var userInfo = buildUserInfo(telegramUser, usr);
                        userInfoRepository.save(userInfo);
                        sendMessage(msg.getChatId(), "Вы успешно авторизовались");
                    },
                    () -> sendMessage(msg.getChatId(), "Неверный идентификатор пользователя")
            );
        });
    }

    private UserInfo buildUserInfo(TelegramUser telegramUser, OsTicketUser osTicketUser) {
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
    private void sendMessage(Long chatId, String message) {
        bot.execute(SendMessage.builder()
                .chatId(chatId.toString())
                .text(message)
                .build());
    }

}