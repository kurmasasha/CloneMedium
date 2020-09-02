package ru.javamentor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс конфигураций mvc
 *
 * @version 1.0
 * @author Java Mentor
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.topic.path}")
    private String uploadTopicPath;

    @Value("${upload.user.path}")
    private String uploadUserPath;

    private static String OS = System.getProperty("os.name");

    /**
     * Определяем путь до загруженных файлов, что бы использовать его в наших шаблонизаторах
     * у кого linux ставим путь "file://"
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path topicPath = Paths.get(uploadTopicPath);
        String topicFilePath = topicPath.toAbsolutePath().toString();

        Path userPath = Paths.get(uploadUserPath);
        String userFilePath = userPath.toAbsolutePath().toString();


        String prefix;
        if (OS.startsWith("Windows")) {
            prefix = "file:/";
        } else {
            prefix = "file://";
        }

        registry.addResourceHandler("/topic-img/**")
                .addResourceLocations(prefix + topicFilePath + "/");

        registry.addResourceHandler("/user-img/**")
                .addResourceLocations(prefix + userFilePath + "/");
    }
}
