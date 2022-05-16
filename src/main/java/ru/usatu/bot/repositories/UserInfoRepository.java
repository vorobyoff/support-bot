package ru.usatu.bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.usatu.bot.domain.UserInfo;
import ru.usatu.bot.domain.UserInfo.UserPK;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, UserPK> {

    Optional<UserInfo> findByUserId_TelegramId(Long telegramId);

}