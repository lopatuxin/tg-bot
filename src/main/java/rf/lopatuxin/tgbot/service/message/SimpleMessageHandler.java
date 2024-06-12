package rf.lopatuxin.tgbot.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
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
                return commandHandler.handle(message.getChatId());
            }
        } else if (update.hasCallbackQuery()) {
            var callbackQuery = update.getCallbackQuery();
            var commandHandler = commandHandlerRegistry.getHandler(callbackQuery.getData());
            if (commandHandler != null) {
                return commandHandler.handle(callbackQuery.getMessage().getChatId());
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
}
