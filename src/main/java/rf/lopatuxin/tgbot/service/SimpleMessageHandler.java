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

    @Override
    public SendMessage handleUpdate(Update update) {
        if (checkUpdate(update)) {
            var message = update.getMessage();
            return handleCommand(message.getText(), message.getChatId());
        }
        return null;
    }

    private SendMessage handleCommand(String command, Long chatId) {
        switch (command) {
            case "/start" -> {
                return sendWelcomeMessage(chatId);
            }
            case "О компании" -> {
                return sendAboutCompanyMessage(chatId);
            }
            default -> {
                return sendUnknownCommandMessage(chatId);
            }
        }
    }

    private SendMessage sendUnknownCommandMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Неизвестная команда. Пожалуйста, используйте /start или \"О компании\".");
        return message;
    }

    private boolean checkUpdate(Update update) {

        return update.hasMessage() && update.getMessage().hasText();
    }

    private SendMessage sendWelcomeMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Добро пожаловать! Нажмите кнопку 'О компании', чтобы узнать больше.");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("О компании"));
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        message.setReplyMarkup(keyboardMarkup);

        return message;
    }

    private SendMessage sendAboutCompanyMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Мы компания, которая занимается... (добавьте ваше описание здесь).");

        return message;
    }
}
