package com.arslek.CRUD3.service;

import com.arslek.CRUD3.model.Role;

public interface RoleService {
    Role saveRole(Role role);

    Role findRole(String role);

    void updateRole(Role role);

    void deleteRole(Role role);
}
