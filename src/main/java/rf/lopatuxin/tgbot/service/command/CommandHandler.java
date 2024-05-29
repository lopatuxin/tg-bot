package rf.lopatuxin.tgbot.service.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface CommandHandler {

    String getCommand();
    SendMessage handle(Long chatId);
}
