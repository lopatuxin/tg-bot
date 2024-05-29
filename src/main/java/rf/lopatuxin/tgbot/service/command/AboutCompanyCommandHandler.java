package rf.lopatuxin.tgbot.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import rf.lopatuxin.tgbot.service.message.MessageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AboutCompanyCommandHandler implements CommandHandler {

    private static final String COMMAND = "О компании";
    private final MessageService messageService;

    @Override
    public String getCommand() {
        return COMMAND;
    }

    @Override
    public SendMessage handle(Long chatId) {

        return messageService.createMessage(chatId, Collections.emptyList(), COMMAND);
    }
}
