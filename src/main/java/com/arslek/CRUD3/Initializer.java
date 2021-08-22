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
        userService.add(new User("admin", "Lastname", "admin", new Role("ROLE_ADMIN")));
        for(int i = 0; i <= 10; i++) {
            userService.addUserWithRole(new User("user" + i, "Lastname" + i, "user" + i), "ROLE_USER");
        }
    }
}
