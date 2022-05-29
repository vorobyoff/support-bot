package ru.usatu.bot.domain.dtos.osticket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@JsonPropertyOrder({"status", "time", "data"})
public class Tickets {

    @JsonProperty("status")
    private String status;
    @JsonProperty("time")
    private Double time;
    @JsonProperty("data")
    private TicketsContainer data;

}
