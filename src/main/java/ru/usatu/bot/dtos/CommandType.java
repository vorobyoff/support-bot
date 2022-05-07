package ru.usatu.bot.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public enum CommandType {

    START("/start"),
    AUTH("/auth");

    @Getter
    private final String text;

    public static Optional<CommandType> from(String command) {
        if (!hasText(command)) return empty();
        command = command.trim();
        var tmp = command;

        return Stream.of(values())
                .filter(it -> it.text.equals(tmp))
                .findFirst();
    }

}