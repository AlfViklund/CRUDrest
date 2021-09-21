package com.arslek.CRUD3.controller;

import com.arslek.CRUD3.model.User;
import com.arslek.CRUD3.service.RoleServiceImpl;
import com.arslek.CRUD3.service.UserService;
import com.arslek.CRUD3.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final UserServiceImpl userService;

    public RestController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Set<User> getUsersList() {
        System.out.println(userService.listUsers());
        return userService.listUsers();
    }
    //@Transactional
    @GetMapping("/current")
    public User getCurrentUser() {
        return userService.getUserByName(userService.getCurrentUserName());
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public void newUser(@RequestBody User user) {
        userService.addUserWithRole(user, user.getRole());
    }

    @PatchMapping("/users")
    public void editUser(@RequestBody User user) {
        userService.addUserWithRole(user, user.getRole());
    }

    @DeleteMapping("/users")
    public void deleteUser(@RequestBody User user) { userService.delete(user); }
}
