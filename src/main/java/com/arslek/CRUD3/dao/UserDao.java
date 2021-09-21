package com.arslek.CRUD3.dao;

import com.arslek.CRUD3.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    void add(User user);

    void delete(User user);

    Set<User> listUsers();

    User getUserById(long id);

    User getUserByName(String name);

    void update(User user);
}
