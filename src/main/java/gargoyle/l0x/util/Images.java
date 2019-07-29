package gargoyle.l0x.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public enum Images {
    ;

    private static final String IMAGE_TYPE_MASK = "image/";

    public static BufferedImage resize(BufferedImage image, int imageWidth, int imageHeight) {
        return resize(image, fit(
                new Rectangle(0, 0, image.getWidth(), image.getHeight()),
                new Rectangle(0, 0, imageWidth, imageHeight)));
    }

    public static BufferedImage resize(BufferedImage src, Rectangle rectangle) {
        BufferedImage image = new BufferedImage(rectangle.width, rectangle.height, src.getType());
        image.createGraphics().drawImage(src, 0, 0, rectangle.width, rectangle.height, null);
        return image;
    }

    public static Rectangle fit(Rectangle inner, Rectangle outer) {
        Rectangle result = new Rectangle();
        if (outer.width < outer.height) {
            result.width = outer.width;
            result.height = (int) (inner.height * result.width / inner.getWidth());
        }
        else {
            result.height = outer.height;
            result.width = (int) (inner.width * result.height / inner.getHeight());
        }
        return result;
    }

    public static void imageWrite(BufferedImage image, Path path) throws IOException {
        ImageIO.write(image, "", path.toFile());
    }

    public static byte[] imageWrite(BufferedImage image, String mediaType) throws IOException {
        byte[] bytes;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            ImageIO.write(image, type(mediaType), output);
            bytes = output.toByteArray();
        }
        return bytes;
    }

    private static String type(String type) throws IOException {
        if (type.startsWith(IMAGE_TYPE_MASK)) {
            return type.substring(IMAGE_TYPE_MASK.length());
        }
        throw new IOException(type);
    }

    public static BufferedImage readImage(byte[] bytes) throws IOException {
        BufferedImage image;
        try (ByteArrayInputStream input = new ByteArrayInputStream(bytes)) {
            image = ImageIO.read(input);
        }
        return image;
    }
}
