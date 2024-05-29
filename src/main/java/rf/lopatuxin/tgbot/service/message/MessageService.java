package rf.lopatuxin.tgbot.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import rf.lopatuxin.tgbot.model.Message;
import rf.lopatuxin.tgbot.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public SendMessage createMessage(Long chatId, List<String> buttonsNames, String command) {
        SendMessage message = new SendMessage(chatId.toString(), getMessage(command));

        if (buttonsNames != null && !buttonsNames.isEmpty()) {
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();

            for (String buttonText : buttonsNames) {
                row.add(new KeyboardButton(buttonText));
            }

            keyboard.add(row);
            keyboardMarkup.setKeyboard(keyboard);
            keyboardMarkup.setResizeKeyboard(true);

            message.setReplyMarkup(keyboardMarkup);
        }

        return message;
    }

    private String getMessage(String command) {
        return findByCommand(command).map(Message::getResponse).orElse("Ошибка");
    }

    private Optional<Message> findByCommand(String command) {
        return messageRepository.findByCommand(command);
    }
}
