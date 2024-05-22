package rf.lopatuxin.tgbot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import rf.lopatuxin.tgbot.service.TelegramBot;

@Component
@Slf4j
@RequiredArgsConstructor
public class BotInitializer {

    private final TelegramBot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            log.info("Телеграмм бот успешно зарегистрирован");
        } catch (TelegramApiException e) {
            log.error("При регистрации бота произошла ошибка " + e.getMessage());
        }
    }
}
