package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.Theme;
import ru.javamentor.service.ThemeService;

/**
 * Rest контроллер для тем
 *
 * @version 1.0
 * @author Java Mentor
 */
@RestController
@RequestMapping("/api")
public class ThemeRestController {

    public ThemeService themeService;

    @Autowired
    public ThemeRestController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/admin/addTheme")
    public ResponseEntity<Theme> addTheme(@RequestParam String name) {
        Theme theme = new Theme(name);
        if (themeService.addTheme(theme)) {
            return new ResponseEntity<>(theme, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/admin/deleteTheme")
    public ResponseEntity<String> deleteTheme(@RequestParam(name = "id") Long id) {
        if (themeService.deleteTheme(id)) {
            return new ResponseEntity<>("Тема успешно удалена", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Ошибка удаления темы", HttpStatus.BAD_REQUEST);
        }
    }
}
