package rf.lopatuxin.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rf.lopatuxin.tgbot.model.Video;
import rf.lopatuxin.tgbot.repository.VideoRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public void saveVideo(String name, InputStream videoStream) throws IOException {
        byte[] content = videoStream.readAllBytes();
        Video video = Video.builder()
                .name(name)
                .content(content)
                .build();
        videoRepository.save(video);
    }

    public Optional<Video> getVideo(String name) {
        return videoRepository.findByName(name);
    }
}
