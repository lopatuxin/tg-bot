package rf.lopatuxin.tgbot.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import rf.lopatuxin.tgbot.service.message.MessageService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyLifeCommandHandler implements CommandHandler {

    private static final String COMMAND = "Моя жизнь";
    private static final List<String> BUTTON_NAMES = Collections.emptyList();
    private final MessageService messageService;

    @Override
    public String getCommand() {
        return COMMAND;
    }

    @Override
    public SendMessage handle(Long chatId) {
        return messageService.createMessage(chatId, BUTTON_NAMES, COMMAND);
    }
}