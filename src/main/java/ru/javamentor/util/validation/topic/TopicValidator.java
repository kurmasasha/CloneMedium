package ru.javamentor.util.validation.topic;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;

/**
 * Проверяет модальное окно добавления статьи
 */
@Component
@Data
public class TopicValidator {
    private String error;

    /**
     * проверка на валидность полей title, content
     */
    public void checkTitleAndContent(String title, String content) {
        error = null;
        // проверка на пустоту title and content
        if (title.equals("") || content.equals("")) {
            error = "поля 'Заголовок' и 'Содержание' должны быть заполнены";
        }
    }

    /**
     * проверка поля File
     * */
    public void checkFile(MultipartFile file) {
        error = null;
        // проверка что файл
        if (file.getSize() > 524288) {
            error = "размер картинки не должен превышать 512 KB";
        }
        if (!file.getContentType().split("/")[0].equals("image")) {
            error = "Загруженный файл не является картинкой";
        }

    }
}
