package rf.lopatuxin.tgbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import rf.lopatuxin.tgbot.config.BotConfig;
import rf.lopatuxin.tgbot.service.message.MessageHandler;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final MessageHandler messageHandler;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private final VideoService videoService;

    public TelegramBot(BotConfig botConfig, MessageHandler messageHandler, VideoService videoService) {
        super(botConfig.getBotToken());
        this.botConfig = botConfig;
        this.messageHandler = messageHandler;
        this.videoService = videoService;
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

            sendVideoByCommand(command, "Моя жизнь", message.getChatId());
            sendVideoByCommand(command, "Работа в интернете", message.getChatId());
            sendVideoByCommand(command, "Как легко зарабатывать", message.getChatId());

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

    private void sendVideoByCommand(String command, String videoName, String chatId) {
        if (videoName.equals(command)) {
            videoService.getVideo(videoName).ifPresent(video -> {
                InputFile videoFile = new InputFile(new ByteArrayInputStream(video.getContent()), videoName);
                SendVideo videoMessage = new SendVideo(chatId, videoFile);
                sendVideoMessage(videoMessage);
            });
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
