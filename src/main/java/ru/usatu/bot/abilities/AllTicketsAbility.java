package ru.usatu.bot.abilities;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.usatu.bot.domain.UserInfo;
import ru.usatu.bot.domain.dtos.osticket.Ticket;
import ru.usatu.bot.domain.dtos.osticket.Tickets;
import ru.usatu.bot.repositories.UserInfoRepository;

import java.util.Collection;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_16;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public final class AllTicketsAbility implements AbilityExtension {

    private final UserInfoRepository userInfoRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unused")
    public Ability allTickets() {
        return Ability.builder()
                .name("all_tickets")
                .action(this::findUserInfo)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .build();
    }

    @SneakyThrows
    private void findUserInfo(MessageContext ctx) {
        var telegramUserId = ctx.user().getId();
        var user = userInfoRepository.findByUserId_TelegramId(telegramUserId);
        if (user.isEmpty()) return;
        var tickets = findAllTickets(user.get());
        if (tickets.isEmpty()) return;
        ctx.bot().sender().execute(SendMessage.builder()
                .chatId(ctx.chatId().toString())
                .text(tickets.toString())
                .build());
    }

    private List<Ticket> findAllTickets(UserInfo userInfo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        headers.setAcceptCharset(singletonList(UTF_16));
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var result = restTemplate.exchange("https://thisbot.ru/ost_wbs/?apikey=AB5F9CB3B226E2F3667581C77CAB87DC&query=ticket&condition=all&sort=status&parameters=0", POST, entity, Tickets.class);
        if (!result.hasBody()) return emptyList();
        var body = result.getBody();
        assert body != null;

        return body.getData().getTickets().stream()
                .flatMap(Collection::stream)
                .filter(it -> it.getUserId().equals(userInfo.userId().osTicketId()))
                .toList();
    }
}