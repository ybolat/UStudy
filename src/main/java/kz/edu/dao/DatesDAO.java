package kz.edu.dao;


import kz.edu.model.Dates;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class DatesDAO {
    private final SessionFactory sessionFactory;

    public DatesDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void CreateDate(Dates dates){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.persist(dates);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void EditDates(Dates dates){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.merge(dates);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public Dates find_date_by_id(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Dates dates;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Dates> query = builder.createQuery(Dates.class);
            Root<Dates> root = query.from(Dates.class);

            Predicate predicate = builder.equal(root.get("dates_id"), id);
            dates = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            dates = null;
        }
        finally{
            session.close();
        }
        return dates;
    }

//    public Dates find_date(String date){
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        Dates dates;
//        try{
//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<Dates> query = builder.createQuery(Dates.class);
//            Root<Dates> root = query.from(Dates.class);
//
//            Predicate predicate = builder.equal(root.get("start_date"), date);
//            dates = session.createQuery(query.where(predicate)).getSingleResult();
//        }
//        catch (NoResultException noResultException) {
//            dates = null;
//        }
//        finally{
//            session.close();
//        }
//        return dates;
//    }

    public void deleteDates(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Dates dates = find_date_by_id(id);
            session.delete(dates);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
