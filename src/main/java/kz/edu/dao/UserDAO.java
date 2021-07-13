package kz.edu.dao;

import kz.edu.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDAO
{
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAO(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public User findByUserName(String username)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user;
        try
        {
            CriteriaBuilder builder1 = session.getCriteriaBuilder();
            CriteriaQuery<User> q1 = builder1.createQuery(User.class);
            Root<User> root1 = q1.from(User.class);

            Predicate predicateUsername = builder1.equal(root1.get("login"), username);
            user = session.createQuery(q1.where(predicateUsername)).getSingleResult();
        }
        catch (NoResultException noResultException)
        {
            user = null;
        }

        return user;
    }

    public void addUser(User user)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try
        {
            CriteriaBuilder builder1 = session.getCriteriaBuilder();
            CriteriaQuery<Role> q1 = builder1.createQuery(Role.class);
            Root<Role> root1 = q1.from(Role.class);

            Predicate predicateRole = builder1.equal(root1.get("name"), "ROLE_USER");
            //Predicate predicateRole = builder1.equal(root1.get("name"), "ROLE_ADMIN");
            Role role = session.createQuery(q1.where(predicateRole)).getSingleResult();
            user.setRole(role);

            session.persist(user);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }
}