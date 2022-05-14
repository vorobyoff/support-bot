package ru.usatu.bot.dtos.osticket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@JsonInclude(NON_NULL)
public record OsTicketUsers(@JsonProperty(value = "status", required = true) String status,
                            @JsonProperty("data") OsTicketUsersContainer data) {

    public record OsTicketUsersContainer(@JsonProperty("users") List<OsTicketUser> users) {
    }

    @JsonInclude(NON_NULL)
    public record OsTicketUser(@JsonProperty(value = "user_id", required = true) String userId,
                               @JsonProperty(value = "name", required = true) String name,
                               @JsonProperty("created") String createdAt) {
    }

}