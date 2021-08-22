package com.arslek.CRUD3.dao;

import com.arslek.CRUD3.model.Role;

public interface RoleDao {
    Role saveRole(Role role);

    Role findRole(String role);

    void updateRole(Role role);

    void deleteRole(Role role);
}
