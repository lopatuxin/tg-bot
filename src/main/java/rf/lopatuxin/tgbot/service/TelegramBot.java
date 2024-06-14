package rf.lopatuxin.tgbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rf.lopatuxin.tgbot.config.BotConfig;
import rf.lopatuxin.tgbot.service.message.MessageHandler;
import rf.lopatuxin.tgbot.service.message.MessageService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final MessageHandler messageHandler;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private final MessageService messageService;

    public TelegramBot(BotConfig botConfig, MessageHandler messageHandler, MessageService messageService) {
        super(botConfig.getBotToken());
        this.botConfig = botConfig;
        this.messageHandler = messageHandler;
        this.messageService = messageService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery() && "Хочу зарабатывать\uD83D\uDCB0".equals(update.getCallbackQuery().getData())) {
            scheduleCallbackMessage(update, 3, "Хочу также");
            scheduleCallbackMessage(update, 4, "Моя жизнь");
            scheduleCallbackMessage(update, 5, "Работа в интернете");
            scheduleCallbackMessage(update, 6, "Как легко зарабатывать");
            scheduleCallbackMessage(update, 7, "Напоминание");
        }
        SendMessage message = messageHandler.handleUpdate(update);
        sendMessage(message);
    }

    private void scheduleCallbackMessage(Update update, Integer time, String command) {
        scheduler.schedule(() -> {
            Message newMessage = update.getCallbackQuery().getMessage();
            newMessage.setText(command);
            update.setMessage(newMessage);

            SendMessage message = messageHandler.handleUpdate(update);
            sendMessage(message);

            sendVideoByCommand(command, "Моя жизнь", "My life.mp4", message.getChatId());
            sendVideoByCommand(command, "Расскажи о работе", "about_your_work.mp4", message.getChatId());
            sendVideoByCommand(command, "Как легко зарабатывать", "money.mp4", message.getChatId());

            scheduler.shutdown();

        }, time, TimeUnit.MINUTES);
    }

    public void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения" + e.getMessage());
        }
    }

    private void sendVideoByCommand(String command, String text, String nameVideo, String chatId) {
        if (text.equals(command)) {
            String videoPath = "src/main/resources/videos/" + nameVideo;
            SendVideo videoMessage = messageService.createVideoMessage(chatId, videoPath, "Смотри видео");
            sendVideoMessage(videoMessage);
        }
    }

    private void sendVideoMessage(SendVideo videoMessage) {
        try {
            execute(videoMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки видео" + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }
}
