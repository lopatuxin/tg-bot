package rf.lopatuxin.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleMessageHandler implements MessageHandler {

    private static final String START_COMMAND = "/start";
    private static final String ABOUT_COMPANY_COMMAND = "О компании";

    @Override
    public SendMessage handleUpdate(Update update) {
        if (checkUpdate(update)) {
            var message = update.getMessage();
            return handleCommand(message.getText(), message.getChatId());
        }
        return createDefaultMessage();
    }

    private SendMessage createDefaultMessage() {
        SendMessage message = new SendMessage();
        message.setText("Обновление не содержит текстового сообщения.");
        return message;
    }

    private SendMessage handleCommand(String command, Long chatId) {
        switch (command) {
            case START_COMMAND -> {
                return createWelcomeMessage(chatId);
            }
            case ABOUT_COMPANY_COMMAND -> {
                return createAboutCompanyMessage(chatId);
            }
            default -> {
                return createUnknownCommandMessage(chatId);
            }
        }
    }

    private SendMessage createUnknownCommandMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Неизвестная команда. Пожалуйста, используйте /start.");
        return message;
    }

    private boolean checkUpdate(Update update) {

        return update.hasMessage() && update.getMessage().hasText();
    }

    private SendMessage createWelcomeMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Добро пожаловать! Нажмите кнопку 'О компании', чтобы узнать больше.");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton(ABOUT_COMPANY_COMMAND));
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        message.setReplyMarkup(keyboardMarkup);

        return message;
    }

    private SendMessage createAboutCompanyMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Мы компания, которая занимается... (добавьте ваше описание здесь).");

        return message;
    }
}
