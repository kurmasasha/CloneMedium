package ru.javamentor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;
    private static String OS = System.getProperty("os.name");

    /**
     * Определяем путь до загруженных файлов, что бы использовать его в наших шаблонизаторах
     * у кого linux ставим путь "file://"
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path path = Paths.get(uploadPath);
        String filePath = path.toAbsolutePath().toString();
        String prefix;
        System.out.println(OS);

        if (OS.startsWith("Windows")) {
            prefix = "file:/";
        } else {
            prefix = "file://";
        }

        registry.addResourceHandler("/topic-img/**")
                .addResourceLocations(prefix + filePath + "/");
    }
}
