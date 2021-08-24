package com.arslek.CRUD3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.arslek.CRUD3.dao.UserDaoImpl;
import com.arslek.CRUD3.model.Role;
import com.arslek.CRUD3.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDaoImpl userDao;
    private final RoleServiceImpl roleService;
    @Autowired
    private PasswordEncoder encoder;

    public UserServiceImpl(UserDaoImpl userDao, RoleServiceImpl roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    public void add(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.add(user);
    }

    @Override
    public void addUserWithRole(User user, String roleName) {
        Role role = roleService.findRole(roleName);

        if(getUserByName(user.getName()) == null) {
            add(user);
            user = getUserByName(user.getName());
        }
        if(role == null) { role = roleService.saveRole(new Role(roleName)); }

        user.addRoles(role);
        update(user);
    }

    @Override
    public void delete(User user) { userDao.delete(user); }

    @Override
    public List<User> listUsers() { return userDao.listUsers(); }

    @Override
    public User getUserById(long id) { return userDao.getUserById(id); }

    @Override
    public void update(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.update(user);
    }

    @Override
    public User getUserByName(String name) { return userDao.getUserByName(name); }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return getUserByName(s);
    }
}
