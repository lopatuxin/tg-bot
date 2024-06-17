package rf.lopatuxin.tgbot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rf.lopatuxin.tgbot.service.VideoService;

import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final VideoService videoService;

    @Override
    public void run(String... args) throws Exception {
        videoService.saveVideo("Моя жизнь", Paths.get("src/main/resources/videos/My life.mp4"));
        videoService.saveVideo("Как легко зарабатывать", Paths.get("src/main/resources/videos/money.mp4"));
        videoService.saveVideo("Работа в интернете", Paths.get("src/main/resources/videos/about_your_work.mp4"));
    }
}
