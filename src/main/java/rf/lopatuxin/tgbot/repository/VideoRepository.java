package rf.lopatuxin.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rf.lopatuxin.tgbot.model.Video;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findByName(String name);
}
