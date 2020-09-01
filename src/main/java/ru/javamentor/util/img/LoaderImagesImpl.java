package ru.javamentor.util.img;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.javamentor.util.validation.topic.TopicValidator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Реализация интерфейса LoaderImages
 *
 * @author Java Mentor
 * @version 1.0
 */

@Component
public class LoaderImagesImpl implements LoaderImages {
    private final TopicValidator topicValidator;

    private static final int IMG_WIDTH = 225;

    @Value("${upload.topic.path}")
    private String uploadPath;

    @Autowired
    public LoaderImagesImpl(TopicValidator topicValidator) {
        this.topicValidator = topicValidator;
    }

    /**
     * Если загрузка прошла удачно и не выпало исключение то,
     * метод возвращает путь до картинки, который мы будем использовать в html
     */
    @Override
    public String upload(MultipartFile file, String uploadPath) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizeImagePng = resizeImage(originalImage, type);

        String uuidFile = UUID.randomUUID().toString();
        String imgName = uuidFile + "." + file.getOriginalFilename().split("\\.")[0] + "." + "png";

        Path path = Paths.get(uploadPath);
        String filePath = path.toAbsolutePath().toString();
        File uploadDir = new File(filePath);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        ImageIO.write(resizeImagePng, "png",
                new File(filePath + "/" + imgName));

        return imgName;
    }

    @Override
    public String fileToImage(MultipartFile file) throws IOException {
        String imageName = "no-img.png";
        boolean availableFile = (file != null && !file.getOriginalFilename().isEmpty());

        if (availableFile) {
            topicValidator.checkFile(file);

            if (topicValidator.getError() == null) {
                imageName = upload(file, uploadPath);
            }
        }

        return imageName;
    }

    /**
     * Изменение размера картинки, т.к. задается только ширина,
     * то рассчитываем высоту
     */
    public BufferedImage resizeImage(BufferedImage originalImage, int type) {
        float height = IMG_WIDTH / ((float) originalImage.getWidth() / originalImage.getHeight());

        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, (int) height, type);
        Graphics2D g = resizedImage.createGraphics();

        g.drawImage(originalImage, 0, 0, IMG_WIDTH, (int) height, null);
        g.dispose();

        return resizedImage;
    }
}
