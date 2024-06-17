package rf.lopatuxin.tgbot.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import rf.lopatuxin.tgbot.model.Message;
import rf.lopatuxin.tgbot.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public SendMessage createMessage(Long chatId, List<String> buttonNames, String command, String name) {
        SendMessage message = new SendMessage(chatId.toString(), getMessage(command, name));

        if (hasValidButtons(buttonNames)) {
            message.setReplyMarkup(createInlineKeyboard(buttonNames));
        }

        return message;
    }

    private boolean hasValidButtons(List<String> buttonNames) {
        return buttonNames != null && !buttonNames.isEmpty();
    }

    private InlineKeyboardMarkup createInlineKeyboard(List<String> buttonNames) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (String buttonText : buttonNames) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(buttonText);
            button.setCallbackData(buttonText);
            rowInline.add(button);
            rowsInline.add(rowInline);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowsInline);

        return inlineKeyboardMarkup;
    }

    private String getMessage(String command, String name) {
        String messageText = messageRepository.findByCommand(command)
                .map(Message::getResponse)
                .orElse("");

        return name + messageText;
    }
}
