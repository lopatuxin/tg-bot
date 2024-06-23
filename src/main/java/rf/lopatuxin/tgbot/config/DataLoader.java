package rf.lopatuxin.tgbot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rf.lopatuxin.tgbot.service.VideoService;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final VideoService videoService;

    @Override
    public void run(String... args) throws Exception {
        saveVideoIfNotExist("Моя жизнь", "/videos/My life.mp4");
        saveVideoIfNotExist("Как легко зарабатывать", "/videos/money.mp4");
        saveVideoIfNotExist("Работа в интернете", "/videos/about_your_work.mp4");
    }

    private void saveVideoIfNotExist(String name, String resourcePath) throws IOException {
        if (videoService.getVideo(name).isEmpty()) {
            try (InputStream videoStream = getClass().getResourceAsStream(resourcePath)) {
                if (videoStream != null) {
                    videoService.saveVideo(name, videoStream);
                } else {
                    log.error("Видео не найдено: " + resourcePath);
                }
            }
        }
    }
}
