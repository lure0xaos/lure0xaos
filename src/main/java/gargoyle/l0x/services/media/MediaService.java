package gargoyle.l0x.services.media;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface MediaService {
    Resource getFallback();

    Resource getFile(String filename);

    void save(String filename, byte[] bytes) throws IOException;

    void createDirectories() throws IOException;
}
