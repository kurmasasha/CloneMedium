package ru.javamentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.javamentor.model.User;
import ru.javamentor.service.role.RoleService;
import ru.javamentor.service.theme.ThemeService;
import ru.javamentor.service.user.UserService;
import ru.javamentor.util.img.LoaderImages;
import ru.javamentor.util.validation.ValidatorFormEditUser;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Контроллер для юзера
 *
 * @author Java Mentor
 * @version 1.0
 */

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final ValidatorFormEditUser validatorFormEditUser;
    private final ThemeService themeService;
    private final LoaderImages loaderImages;

    @Value("${upload.user.path}")
    private String uploadPath;

    @Autowired
    public UserController(UserService userService, RoleService roleService, ValidatorFormEditUser validatorFormEditUser, ThemeService themeService, LoaderImages loaderImages) {
        this.userService = userService;
        this.roleService = roleService;
        this.validatorFormEditUser = validatorFormEditUser;
        this.themeService = themeService;
        this.loaderImages = loaderImages;
    }

    /**
     * Метод обрабатывающий юзера и возвращающий его страницу.
     */
    @GetMapping("/user")
    public String showUser(Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        User userDB = userService.getUserById(user.getId());
        model.addAttribute("user", userDB);
        themeService.showThemes(model, userDB);
        List<String> notSubscribed = userService.getAllSubscribesNotOfUser(user.getUsername());
        model.addAttribute("notSubscribedAuthors", notSubscribed);
        List<String> subscribes = userService.getAllSubscribesOfUser(user.getUsername());
        model.addAttribute("subscribes", subscribes);
        return "userPage";
    }

    //TODO при неудачной валидации перенаправляет на адрес запроса
    /**
     * Метод изменяющий юзера.
     */
    @PostMapping("/user")
    public String userUpdate(@ModelAttribute("user") User user,
                          @RequestParam(name = "themes", required = false) Set<Long> themesIds,
                          @RequestParam(name = "subscribes", required = false) Set<String> subscribes,
                          @RequestParam(name = "file", required = false) MultipartFile file,
                          Model model,
                          BindingResult bindingResult) throws IOException {

        validatorFormEditUser.validate(user, bindingResult);
        User userDB = userService.getUserById(user.getId());
        if (bindingResult.hasErrors()) {
            themeService.showThemes(model, userDB);
            List<String> notSubscribed = userService.getAllSubscribesNotOfUser(user.getUsername());
            model.addAttribute("notSubscribedAuthors", notSubscribed);
            return "userPage";
        }
        userDB.setFirstName(user.getFirstName());
        userDB.setLastName(user.getLastName());
        themeService.changeThemes(themesIds, userDB);
        if (!user.getPassword().equals("")) {
            userDB.setPassword(user.getPassword());
        }

        if (user.getPhoneNumber().equals("")) {
            userDB.setPhoneNumber(null);
        } else {
            userDB.setPhoneNumber(user.getPhoneNumber());
        }

        String resultFileName = "no-img.png";
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            resultFileName = loaderImages.upload(file, uploadPath);
        }else {
            resultFileName = userDB.getImg();
        }
        userDB.setImg(resultFileName);

        if (userService.updateUser(userDB) && userService.changeSubscribe(subscribes, userDB.getUsername())) {
            return "redirect:/user";
        } else {
            model.addAttribute("message", "invalidData");
        }
        return "redirect:/user";
    }
}
