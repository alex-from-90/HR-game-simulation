package ru.simulator.hr.controller.game;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simulator.hr.entity.User;
import ru.simulator.hr.interfaces.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);

        if (createdUser != null) {
            return ResponseEntity.ok("Пользователь успешно создан. ID пользователя: " + createdUser.getUserTelId());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при создании пользователя");
        }
    }

}