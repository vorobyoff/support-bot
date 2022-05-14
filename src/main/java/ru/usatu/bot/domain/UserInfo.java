package ru.usatu.bot.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@Builder
@Table(name = "user")
@NoArgsConstructor(access = PROTECTED)
@Accessors(chain = true, fluent = true)
@AllArgsConstructor(access = PROTECTED)
public class UserInfo {

    @EmbeddedId
    private UserPK userId;

    private String systemName;
    private String firstName;
    private String createdAt;
    private String lastName;
    private String userName;

    @Builder
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor(access = PROTECTED)
    @AllArgsConstructor(access = PROTECTED)
    public static class UserPK implements Serializable {

        @Column(nullable = false)
        private String osTicketId;
        @Column(nullable = false)
        private Long telegramId;

    }

}