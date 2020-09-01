package ru.javamentor.util.img;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Интерфейс для работы с изображением
 *
 * @version 1.0
 * @author Java Mentor
 */

@Component
public interface LoaderImages {

    /**
     * Метод возвращает путь до картинки, который мы будем использовать в html
     */
    String upload(MultipartFile file, String uploadPath) throws IOException;

    String fileToImage(MultipartFile file) throws IOException;
}
