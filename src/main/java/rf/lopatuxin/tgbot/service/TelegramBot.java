package rf.lopatuxin.tgbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rf.lopatuxin.tgbot.config.BotConfig;
import rf.lopatuxin.tgbot.service.message.MessageHandler;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final MessageHandler messageHandler;

    public TelegramBot(BotConfig botConfig, MessageHandler messageHandler) {
        super(botConfig.getBotToken());
        this.botConfig = botConfig;
        this.messageHandler = messageHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = messageHandler.handleUpdate(update);
        sendMessage(message);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    public void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения" + e.getMessage());
        }
    }
}
