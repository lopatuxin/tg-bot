package rf.lopatuxin.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandHandlerRegistry {

    private final Map<String, CommandHandler> handlers = new HashMap<>();

    @Autowired
    public CommandHandlerRegistry(List<CommandHandler> handlers) {
        for (CommandHandler handler : handlers) {
            this.handlers.put(handler.getCommand(), handler);
        }
    }

    public CommandHandler getHandler(String command) {
        return handlers.get(command);
    }
}
