package rf.lopatuxin.tgbot.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import rf.lopatuxin.tgbot.service.message.MessageService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {

    private static final String COMMAND = "/start";
    private final MessageService messageService;

    @Override
    public String getCommand() {
        return COMMAND;
    }

    @Override
    public SendMessage handle(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageService.getMessage(COMMAND));

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
}
