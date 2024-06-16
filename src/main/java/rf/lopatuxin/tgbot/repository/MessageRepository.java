package rf.lopatuxin.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rf.lopatuxin.tgbot.model.Message;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByCommand(String command);
}
