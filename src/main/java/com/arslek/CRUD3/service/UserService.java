package com.arslek.CRUD3.service;

import com.arslek.CRUD3.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    void addUserWithRole(User user, String roleName);

    void delete(User user);

    List<User> listUsers();

    User getUserById(long id);

    User getUserByName(String name);

    void update(User user);
}
