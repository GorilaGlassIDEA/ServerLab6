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
    MyActionOnUpdateMessageFromUser action = new MyActionOnUpdateMessageFromUser();

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
        if (action.validateMessage(update)) {
            SendMessage answerFromProcessedCommand = action.moveActionWithCommandFromMessage(update);
            try {
                execute(answerFromProcessedCommand);
            } catch (Exception e) {
                //TODO: обрабатывать это исключение на более низком уровне
            }
        }
    }


}

class MyActionOnUpdateMessageFromUser {
    public boolean validateMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    public SendMessage moveActionWithCommandFromMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        String firstName = update.getMessage().getChat().getFirstName();
        String answer = "Hi," + firstName;
        return sendMessage(chatId, answer);
    }

    private SendMessage sendMessage(long chatId, String textMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textMessage);
        return message;
    }
}