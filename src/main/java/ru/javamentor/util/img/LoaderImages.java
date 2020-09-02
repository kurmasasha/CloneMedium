package ru.javamentor.util.img;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Интерфейс для работы с изображением
 *
 * @author Java Mentor
 * @version 1.0
 */

@Component
public interface LoaderImages {

    /**
     * Метод возвращает путь до картинки, который мы будем использовать в html
     */
    String upload(MultipartFile file, String uploadPath) throws IOException;

    /**
     * Метод проверяет загружаемый файл на соответствие и
     * возвращает имя файла на сервере
     *
     * @param file - картинка
     * @return - имя файла на сервере
     * @throws IOException
     */
    String fileToImage(MultipartFile file) throws IOException;
}
