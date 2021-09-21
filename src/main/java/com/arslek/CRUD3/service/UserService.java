package com.arslek.CRUD3.service;

import com.arslek.CRUD3.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void add(User user);

    void addUserWithRole(User user, String roleName);

    void delete(User user);

    Set<User> listUsers();

    User getUserById(long id);

    User getUserByName(String name);

    void update(User user);
}
