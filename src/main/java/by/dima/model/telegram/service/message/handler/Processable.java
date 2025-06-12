package by.dima.model.telegram.service.message.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

interface Processable {
    SendMessage process(Update update);
}
