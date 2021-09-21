package com.arslek.CRUD3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.arslek.CRUD3.dao.UserDaoImpl;
import com.arslek.CRUD3.model.Role;
import com.arslek.CRUD3.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional
    @Override
    public void addUserWithRole(User user, String roleName) {
        Role role = roleService.findRole(roleName);
        if(role == null) { role = roleService.saveRole(new Role(roleName)); }
        String pass = user.getPassword();
        try {
            if(user.getId() == null) {
                user.addRoles(role);
                add(user);
                return;
            }
        }catch (NullPointerException ex) {}

        user.setRoles(new HashSet<>());
        user.addRoles(role);
        user.setPassword(pass);
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
        String dbPass = getUserById(user.getId()).getPassword();

        if (!dbPass.equals(user.getPassword()))
        {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        userDao.update(user);
    }

    @Override
    public User getUserByName(String name) { return userDao.getUserByName(name); }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return getUserByName(s);
    }

    public String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public boolean isAdmin() {
        boolean result = false;
        for(GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                result = true;
            }
        }
        return result;

    }
}
