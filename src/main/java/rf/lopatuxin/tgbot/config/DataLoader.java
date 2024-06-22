package rf.lopatuxin.tgbot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rf.lopatuxin.tgbot.service.VideoService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final VideoService videoService;

    @Override
    public void run(String... args) throws Exception {
        saveVideoIfNotExist("Моя жизнь", Paths.get("src/main/resources/videos/My life.mp4"));
        saveVideoIfNotExist("Как легко зарабатывать", Paths.get("src/main/resources/videos/money.mp4"));
        saveVideoIfNotExist("Работа в интернете", Paths.get("src/main/resources/videos/about_your_work.mp4"));
    }

    private void saveVideoIfNotExist(String name, Path path) throws IOException {
        if (videoService.getVideo(name).isEmpty()) {
            videoService.saveVideo(name, path);
        }
    }
}
