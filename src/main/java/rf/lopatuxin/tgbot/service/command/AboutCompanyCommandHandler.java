package rf.lopatuxin.tgbot.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import rf.lopatuxin.tgbot.service.message.MessageService;

import java.util.Collections;

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
