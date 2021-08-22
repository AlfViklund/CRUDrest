package com.arslek.CRUD3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arslek.CRUD3.dao.RoleDao;
import com.arslek.CRUD3.model.Role;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDao roleDao;

    @Override
    public Role saveRole(Role role) {
        return roleDao.saveRole(role);
    }

    @Override
    public Role findRole(String role) {
        return roleDao.findRole(role);
    }

    @Override
    public void updateRole(Role role) {
        roleDao.updateRole(role);
    }

    @Override
    public void deleteRole(Role role) {
        roleDao.deleteRole(role);
    }
}
