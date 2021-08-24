package com.arslek.CRUD3.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.arslek.CRUD3.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@EnableTransactionManagement(proxyTargetClass = true)
public class RoleDaoImpl implements RoleDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Role saveRole(Role role) {
        Role similarRole = findRole(role.getRole());
        if(similarRole == null) {
            entityManager.persist(role);
            similarRole = findRole(role.getRole());
        }
        return similarRole;
    }

    @Override
    public Role findRole(String role) {
        try{
            return entityManager.createQuery("SELECT r FROM Role r WHERE r.role = :role", Role.class).
                    setParameter("role", role).getSingleResult();
        }catch(NoResultException ex) {
        }
        return null;
    }

    @Override
    public void updateRole(Role role) { entityManager.merge(role); }

    @Override
    public void deleteRole(Role role) {
        entityManager.createQuery("DELETE Role WHERE id =:id").
                setParameter("id", role.getId()).
                executeUpdate();
    }

    @Override
    public List<Role> getRolesList() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }
}
