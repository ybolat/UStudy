package kz.edu.dao;

import kz.edu.model.Areas;
import kz.edu.model.Centers;
import kz.edu.model.Exam_Types;
import kz.edu.model.Exam_types_centers;
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
public class CentersDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public CentersDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Centers> getAllCenters(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Centers> centersList;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Centers> criteria = builder.createQuery(Centers.class);
            Root<Centers> root = criteria.from(Centers.class);
            criteria.select(root);
            Query<Centers> query = session.createQuery(criteria);
            centersList = query.getResultList();
        } finally {
            session.close();
        }
        return centersList;
    }

    public void createCenter(Centers center){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.persist(center);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void EditCenter(Centers center){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.merge(center);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public Centers find_center_by_id(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Centers center;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Centers> query = builder.createQuery(Centers.class);
            Root<Centers> root = query.from(Centers.class);

            Predicate predicate = builder.equal(root.get("center_id"), id);
            center = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            center = null;
        }
        finally{
            session.close();
        }
        return center;
    }

    public void deleteCenter(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Centers centers = find_center_by_id(id);
            session.delete(centers);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public List<Centers> getCentersOfArea(Areas area){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Centers> centersList;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Centers> criteria = builder.createQuery(Centers.class);
            Root<Centers> root = criteria.from(Centers.class);

            Predicate predicate = builder.equal(root.get("area"), area);
            centersList = session.createQuery(criteria.where(predicate)).getResultList();
        } finally {
            session.close();
        }
        return centersList;
    }

    public Centers findCenter(String text){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Centers centers;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Centers> criteria = builder.createQuery(Centers.class);
            Root<Centers> root = criteria.from(Centers.class);

            Predicate predicate = builder.equal(root.get("region"), text);
            Predicate predicate1 = builder.equal(root.get("address"), text);
            Predicate predicate2 = builder.equal(root.get("phone_number"), text);
            Predicate predicate3 = builder.equal(root.get("email"), text);
            Predicate predicateAll = builder.or(predicate,predicate1,predicate2,predicate3);

            centers = session.createQuery(criteria.where(predicateAll)).getSingleResult();
        } finally {
            session.close();
        }
        return centers;
    }

    public List<Exam_types_centers> getCenterOfExamTypes(Exam_Types exam_types){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exam_types_centers> exam_types_centersList;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exam_types_centers> criteria = builder.createQuery(Exam_types_centers.class);

            Root<Exam_types_centers> root = criteria.from(Exam_types_centers.class);
            Predicate predicate = builder.equal(root.get("exam_types"), exam_types);

            exam_types_centersList = session.createQuery(criteria.where(predicate)).getResultList();
        } finally {
            session.close();
        }
        return exam_types_centersList;
    }
}
