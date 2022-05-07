package ru.usatu.bot.events;

import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;

import java.util.Optional;
import java.util.OptionalInt;

import static java.util.Optional.ofNullable;

public final class UpdateEvent extends ApplicationEvent {

    public UpdateEvent(Update source) {
        super(source);
    }

    @Override
    public Update getSource() {
        return (Update) super.getSource();
    }

    public Optional<ChosenInlineQuery> chosenInlineQuery() {
        return ofNullable(getSource().getChosenInlineQuery());
    }

    public Optional<PreCheckoutQuery> preCheckoutQuery() {
        return ofNullable(getSource().getPreCheckoutQuery());
    }

    public Optional<ChatJoinRequest> chatJoinRequest() {
        return ofNullable(getSource().getChatJoinRequest());
    }

    public Optional<ChatMemberUpdated> myChatMember() {
        return ofNullable(getSource().getMyChatMember());
    }

    public Optional<Message> editedChannelPost() {
        return ofNullable(getSource().getEditedChannelPost());
    }

    public Optional<CallbackQuery> callbackQuery() {
        return ofNullable(getSource().getCallbackQuery());
    }

    public Optional<ShippingQuery> shippingQuery() {
        return ofNullable(getSource().getShippingQuery());
    }

    public Optional<ChatMemberUpdated> chatMember() {
        return ofNullable(getSource().getChatMember());
    }

    public Optional<InlineQuery> inlineQuery() {
        return ofNullable(getSource().getInlineQuery());
    }

    public Optional<PollAnswer> pollAnswer() {
        return ofNullable(getSource().getPollAnswer());
    }

    public Optional<Message> channelPost() {
        return ofNullable(getSource().getChannelPost());
    }

    public Optional<Message> editedMessage() {
        return ofNullable(getSource().getMessage());
    }

    public OptionalInt updateId() {
        return OptionalInt.of(getSource().getUpdateId());
    }

    public Optional<Message> message() {
        return ofNullable(getSource().getMessage());
    }

    public Optional<Poll> poll() {
        return ofNullable(getSource().getPoll());
    }

}