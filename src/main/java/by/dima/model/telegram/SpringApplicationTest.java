package by.dima.model.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationTest.class);
    }
}
