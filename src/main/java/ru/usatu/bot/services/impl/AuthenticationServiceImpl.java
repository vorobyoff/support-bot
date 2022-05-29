package ru.usatu.bot.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.usatu.bot.domain.dtos.osticket.OsTicketUsers;
import ru.usatu.bot.domain.dtos.osticket.OsTicketUsers.OsTicketUser;
import ru.usatu.bot.services.api.AuthenticationService;

import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_16;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<OsTicketUser> findOsTicketUserById(String osTicketUserId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        headers.setAcceptCharset(singletonList(UTF_16));
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var result = restTemplate.exchange("https://thisbot.ru/ost_wbs/?apikey=AB5F9CB3B226E2F3667581C77CAB87DC&query=user&condition=specific&parameters=" + osTicketUserId, POST, entity, OsTicketUsers.class);
        if (!result.hasBody()) return empty();
        var body = requireNonNull(result.getBody());
        if (body.status().equals("Error")) return empty();
        return body.data().users().stream()
                .filter(it -> it.userId().equals(osTicketUserId))
                .findFirst();
    }
}
