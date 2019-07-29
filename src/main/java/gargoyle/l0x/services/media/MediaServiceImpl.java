package gargoyle.l0x.services.media;

import gargoyle.l0x.config.MediaConfig;
import gargoyle.l0x.util.Images;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private static final String RES_FALLBACK = "static/img/No_Image-250x250.png";
    private final MediaConfig config;

    private Path getFilePath(String filename) {
        return Paths.get(config.getImageLocation(), filename);
    }

    @Override
    public Resource getFallback() {
        return new ClassPathResource(RES_FALLBACK);
    }

    @Override
    public Resource getFile(String filename) {
        return new FileSystemResource(getFilePath(filename));
    }

    @Override
    public void save(String filename, byte[] bytes) throws IOException {
        Images.imageWrite(Images.resize(Images.readImage(bytes), config.getImageWidth(), config.getImageHeight()), getFilePath(filename));
    }

    @Override
    public void createDirectories() throws IOException {
        Files.createDirectories(Paths.get(config.getImageLocation()));
    }
}
