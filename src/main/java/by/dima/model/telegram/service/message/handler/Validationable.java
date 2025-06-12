package by.dima.model.telegram.service.message.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

interface Validationable {
    boolean validate(Update update);
}
