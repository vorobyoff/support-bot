package ru.usatu.bot.dtos;

import lombok.Builder;

import static java.util.Objects.hash;
import static java.util.Objects.isNull;

@Builder
public record UserInfo(Long userId, String firstName, String lastName, String userName) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (isNull(obj) || getClass() != obj.getClass()) return false;
        var user = (UserInfo) obj;

        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return hash(userId);
    }

}