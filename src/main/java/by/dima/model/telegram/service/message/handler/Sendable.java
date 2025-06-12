package by.dima.model.telegram.service.message.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

interface Sendable {
    SendMessage send(long chatId, String textMessage);
}
