package ru.javamentor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javamentor.model.Role;
import ru.javamentor.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс представляющий объект DTO пользователя в системе
 *
 * @version 1.0
 * @author Java Mentor
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Role role;

    public List<UserDto> getUserDtoList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setRole(user.getRole());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}
