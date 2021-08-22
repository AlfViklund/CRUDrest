package com.arslek.CRUD3.dao;

import com.arslek.CRUD3.model.User;

import java.util.List;

public interface UserDao {

    void add(User user);

    void delete(User user);

    List<User> listUsers();

    User getUserById(long id);

    User getUserByName(String name);

    void update(User user);
}
