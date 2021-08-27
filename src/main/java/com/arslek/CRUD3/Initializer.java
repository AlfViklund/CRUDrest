package com.arslek.CRUD3;

import org.springframework.stereotype.Component;
import com.arslek.CRUD3.model.Role;
import com.arslek.CRUD3.model.User;
import com.arslek.CRUD3.service.UserService;

@Component
public class Initializer {
    private final UserService userService;

    public Initializer(UserService userService) {
        this.userService = userService;
        InitializeUsers();
    }


    public void InitializeUsers() {
        User user = new User("admin", "Lastname", 30, "admin@mail.ru", "admin", new Role("ROLE_ADMIN"));
        userService.addUserWithRole(user, "ROLE_USER");
        for(int i = 0; i <= 3; i++) {
            userService.addUserWithRole(new User("user" + i, "Lastname" + i, 30, i + "user@mail.ru", "user" + i), "ROLE_USER");
        }
    }
}
