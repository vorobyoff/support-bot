package ru.usatu.bot.services.api;

import ru.usatu.bot.domain.dtos.osticket.OsTicketUsers.OsTicketUser;

import java.util.Optional;

public interface AuthenticationService {

    Optional<OsTicketUser> findOsTicketUserById(String osTicketUserId);

}