package com.arslek.CRUD3.dao;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.arslek.CRUD3.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Repository
@Transactional
@EnableTransactionManagement(proxyTargetClass = true)
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) { entityManager.persist(user); }

    @Override
    @Transactional
    public void delete(User user) {
        entityManager.createQuery("DELETE FROM User where id = :id").
                setParameter("id", user.getId()).
                executeUpdate();
        }

    @Override
    public Set<User> listUsers() {
        return new LinkedHashSet<>(entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles", User.class)
                .getResultList());
    }

    @Override
    public User getUserById(long id) {
        try {
            return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = :id", User.class).
                    setParameter("id", id).getSingleResult();
        }catch (NoResultException ex) {
        }
        return null;
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    //@Transactional
    public User getUserByName(String name) {
        try {
            return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.name = :name", User.class).
                    setParameter("name", name).getSingleResult();
        }catch (NoResultException ex) {
        }
        return null;
    }
}
