package ru.javamentor.util.img;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public interface LoaderImages {
    String upload(MultipartFile file, String uploadPath) throws IOException;
}
