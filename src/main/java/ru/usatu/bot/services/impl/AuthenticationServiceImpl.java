package ru.usatu.bot.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.usatu.bot.dtos.osticket.OsTicketUsers;
import ru.usatu.bot.dtos.osticket.OsTicketUsers.OsTicketUser;
import ru.usatu.bot.services.api.AuthenticationService;

import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<OsTicketUser> findOsTicketUserById(String osTicketUserId) {
        var result = restTemplate.getForEntity("http://thisbot.ru/ost_wbs/?apikey={apikey}&query=user&condition=specific&parameters={userId}",
                OsTicketUsers.class, Map.of("apikey", "AB5F9CB3B226E2F3667581C77CAB87DC", "userId", osTicketUserId));
        if (!result.hasBody()) return empty();

        var body = requireNonNull(result.getBody());
        if (body.status().equals("Error")) return empty();
        return body.data().users().stream()
                .filter(it -> it.userId().equals(osTicketUserId))
                .findFirst();
    }
}
