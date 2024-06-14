package rf.lopatuxin.tgbot.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import rf.lopatuxin.tgbot.service.command.CommandHandlerRegistry;

@Service
@RequiredArgsConstructor
public class SimpleMessageHandler implements MessageHandler {

    private final CommandHandlerRegistry commandHandlerRegistry;

    @Override
    public SendMessage handleUpdate(Update update) {
        if (checkUpdate(update)) {
            var message = update.getMessage();
            var commandHandler = commandHandlerRegistry.getHandler(message.getText());
            if (commandHandler != null) {
                String userName = getUserNameFromCommand(message.getText(), update);
                return commandHandler.handle(message.getChatId(), userName);
            }
        } else if (update.hasCallbackQuery()) {
            var callbackQuery = update.getCallbackQuery();
            var commandHandler = commandHandlerRegistry.getHandler(callbackQuery.getData());
            if (commandHandler != null) {
                String userName = getUserNameFromCommand(callbackQuery.getMessage().getText(), update);
                return commandHandler.handle(callbackQuery.getMessage().getChatId(), userName);
            }
        }
        return createDefaultMessage();
    }

    private SendMessage createDefaultMessage() {
        SendMessage message = new SendMessage();
        message.setText("Обновление не содержит текстового сообщения.");
        return message;
    }

    private boolean checkUpdate(Update update) {

        return update.hasMessage() && update.getMessage().hasText();
    }

    private String getUserNameFromCommand(String command, Update update) {
        return "Расскажи о работе".equalsIgnoreCase(command)
                ? getUserName(update)
                : "";
    }

    private String getUserName(Update update) {
        User user = null;
        if (update.hasMessage()) {
            user = update.getMessage().getFrom();
        } else if (update.hasCallbackQuery()) {
            user = update.getCallbackQuery().getFrom();
        }
        return user != null ? user.getFirstName() : "Unknown user";
    }
}
