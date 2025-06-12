package by.dima.model.telegram.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("application.properties")
@Data
public class BotCredentials {
    @Value("${telegram.username}")
    private String username;
    @Value("${telegram.token}")
    private String token;
}
