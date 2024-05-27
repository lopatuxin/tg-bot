package rf.lopatuxin.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rf.lopatuxin.tgbot.model.Message;
import rf.lopatuxin.tgbot.repository.MessageRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Optional<Message> findByCommand(String command) {
        return messageRepository.findByCommand(command);
    }
}
