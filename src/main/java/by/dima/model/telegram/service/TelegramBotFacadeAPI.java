package by.dima.model.telegram.service;


import by.dima.model.telegram.config.BotCredentials;
import by.dima.model.telegram.service.message.handler.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@SuppressWarnings("ALL")
@Component
public class TelegramBotFacadeAPI extends TelegramLongPollingBot {

    private final BotCredentials credentials;
    private final MessageHandler messageHandler;

    @Autowired
    public TelegramBotFacadeAPI(BotCredentials credentials, MessageHandler messageHandler) {
        this.credentials = credentials;
        this.messageHandler = messageHandler;
    }

    @Override
    public String getBotUsername() {
        return credentials.getUsername();
    }

    @Override
    public String getBotToken() {
        return credentials.getToken();
    }

    @Override
    public void onUpdateReceived(Update messageObject) {
        if (messageHandler.validate(messageObject)) {
            SendMessage answerFromProcessedCommand = messageHandler.process(messageObject);
            try {
                execute(answerFromProcessedCommand);
            } catch (TelegramApiException e) {
                throw new RuntimeException("Не получилось отправить ответное сообщение пользователю!", e);
            }
        }
    }
}

