
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
@JsonPropertyOrder({"ticket_id", "ticket_pid", "number", "user_id", "user_email_id", "status_id", "dept_id", "sla_id",
        "topic_id", "staff_id", "team_id", "email_id", "lock_id", "flags", "sort", "subject", "title", "body", "ip_address",
        "source", "source_extra", "isoverdue", "isanswered", "duedate", "est_duedate", "reopened", "closed", "lastupdate",
        "created", "updated"})
public class Ticket {

    @JsonProperty("ticket_id")
    private String ticketId;
    @JsonProperty("ticket_pid")
    private Object ticketPid;
    @JsonProperty("number")
    private String number;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("user_email_id")
    private String userEmailId;
    @JsonProperty("status_id")
    private String statusId;
    @JsonProperty("dept_id")
    private String deptId;
    @JsonProperty("sla_id")
    private String slaId;
    @JsonProperty("topic_id")
    private String topicId;
    @JsonProperty("staff_id")
    private String staffId;
    @JsonProperty("team_id")
    private String teamId;
    @JsonProperty("email_id")
    private String emailId;
    @JsonProperty("lock_id")
    private String lockId;
    @JsonProperty("flags")
    private String flags;
    @JsonProperty("sort")
    private String sort;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("title")
    private String title;
    @JsonProperty("body")
    private String body;
    @JsonProperty("ip_address")
    private String ipAddress;
    @JsonProperty("source")
    private String source;
    @JsonProperty("source_extra")
    private Object sourceExtra;
    @JsonProperty("isoverdue")
    private String isoverdue;
    @JsonProperty("isanswered")
    private String isanswered;
    @JsonProperty("duedate")
    private Object duedate;
    @JsonProperty("est_duedate")
    private String estDuedate;
    @JsonProperty("reopened")
    private Object reopened;
    @JsonProperty("closed")
    private Object closed;
    @JsonProperty("lastupdate")
    private String lastupdate;
    @JsonProperty("created")
    private String created;
    @JsonProperty("updated")
    private String updated;

}