package rf.lopatuxin.tgbot.service.message;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public interface MessageHandler {

    SendMessage handleUpdate(Update update);
}
