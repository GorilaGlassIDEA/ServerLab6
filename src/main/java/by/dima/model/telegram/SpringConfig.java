package by.dima.model.telegram;


import by.dima.model.telegram.service.message.handler.MessageHandler;
import by.dima.model.telegram.service.message.handler.MyMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    MessageHandler getMessageHandler(){
        return new MyMessageHandler();
    }

}
