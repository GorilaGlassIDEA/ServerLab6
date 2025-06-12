package by.dima.model.telegram.service.message.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyMessageHandler implements MessageHandler {
    @Override
    public boolean validate(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    @Override
    public SendMessage process(Update update) {
        long chatId = update.getMessage().getChatId();
        String firstName = update.getMessage().getChat().getFirstName();
        String answer = "Hi," + firstName;
        return send(chatId, answer);
    }

    @Override
    public SendMessage send(long chatId, String textMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textMessage);
        return message;
    }
}
