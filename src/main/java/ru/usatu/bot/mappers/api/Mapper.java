package ru.usatu.bot.mappers.api;

import java.util.function.Function;

public interface Mapper<I, O> extends Function<I, O> {

    @Override
    default O apply(I in) {
        return map(in);
    }

    O map(I in);

}