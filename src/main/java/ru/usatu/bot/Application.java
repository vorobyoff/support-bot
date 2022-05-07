package ru.usatu.bot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.usatu.bot.poperties.TelegramBotProperties;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableConfigurationProperties(TelegramBotProperties.class)
public class Application {

    public static void main(String... args) {
        run(Application.class, args);
    }

}