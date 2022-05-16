
package ru.usatu.bot.domain.dtos.osticket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@JsonPropertyOrder({"total", "tickets"})
public class TicketsContainer {

    @JsonProperty("total")
    private Long total;
    @JsonProperty("tickets")
    private List<List<Ticket>> tickets;

}
