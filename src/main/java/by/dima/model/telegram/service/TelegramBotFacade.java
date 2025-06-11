package by.dima.model.telegram.service;


import by.dima.model.telegram.config.BotCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@SuppressWarnings("ALL")
@Component
public class TelegramBotFacade extends TelegramLongPollingBot {

    private final BotCredentials config;
    HandlerMessage handlerMessage = new MyHandlerMessage();

    @Autowired
    public TelegramBotFacade(BotCredentials config) {
        this.config = config;
    }


    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (handlerMessage.validate(update)) {
            SendMessage answerFromProcessedCommand = handlerMessage.process(update);
            try {
                execute(answerFromProcessedCommand);
            } catch (TelegramApiException e) {
                throw new RuntimeException("Не получилось отправить ответное сообщение пользователю!", e);
            }
        }
    }


}

class MyHandlerMessage implements HandlerMessage {
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

interface Validationable {
    boolean validate(Update update);
}

interface Processable {
    SendMessage process(Update update);
}

interface Sendable {
    SendMessage send(long chatId, String textMessage);
}

interface HandlerMessage extends Validationable, Processable, Sendable {
}