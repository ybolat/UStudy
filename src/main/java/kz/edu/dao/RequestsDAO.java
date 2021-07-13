package kz.edu.dao;

import kz.edu.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class RequestsDAO {
    private final SessionFactory sessionFactory;

    public RequestsDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createRequest(Requests requests){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.persist(requests);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void createRequest_Centers(Requests_centers requests_centers){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.persist(requests_centers);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void editRequest_Centers(Requests_centers requests_centers){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.merge(requests_centers);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void editRequest(Requests requests){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.merge(requests);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public List<Requests> getAllRequests(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Requests> requestsList;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Requests> criteria = builder.createQuery(Requests.class);
            Root<Requests> root = criteria.from(Requests.class);
            criteria.select(root);
            Query<Requests> query = session.createQuery(criteria);
            requestsList = query.getResultList();
        } finally {
            session.close();
        }
        return requestsList;
    }

    public List<Requests> getRequestsOfStatus(Statuses statuses){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Requests> requestsList;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Requests> criteria = builder.createQuery(Requests.class);
            Root<Requests> root = criteria.from(Requests.class);

            Predicate predicate = builder.equal(root.get("status"), statuses);
            requestsList = session.createQuery(criteria.where(predicate)).getResultList();
        } finally {
            session.close();
        }
        return requestsList;
    }

    public List<Requests_centers> getRequestCentersOfRequest(Requests requests){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Requests_centers> requests_centersList;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Requests_centers> criteria = builder.createQuery(Requests_centers.class);
            Root<Requests_centers> root = criteria.from(Requests_centers.class);

            Predicate predicate = builder.equal(root.get("requests"), requests);
            requests_centersList = session.createQuery(criteria.where(predicate)).getResultList();
        } finally {
            session.close();
        }
        return requests_centersList;
    }

    public Statuses find_status_by_name(String name){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Statuses statuses;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Statuses> query = builder.createQuery(Statuses.class);
            Root<Statuses> root = query.from(Statuses.class);

            Predicate predicate = builder.equal(root.get("status_name"), name);
            statuses = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            statuses = null;
        }
        finally{
            session.close();
        }
        return statuses;
    }

    public Requests_centers find_rc_by_id(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Requests_centers requests_centers;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Requests_centers> query = builder.createQuery(Requests_centers.class);
            Root<Requests_centers> root = query.from(Requests_centers.class);

            Predicate predicate = builder.equal(root.get("rc"), id);
            requests_centers = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            requests_centers = null;
        }
        finally{
            session.close();
        }
        return requests_centers;
    }

    public List<Requests_centers> find_request_centers_by_center(Centers center){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Requests_centers> requests_centersList;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Requests_centers> query = builder.createQuery(Requests_centers.class);
            Root<Requests_centers> root = query.from(Requests_centers.class);

            Predicate predicate = builder.equal(root.get("centers"), center);
            requests_centersList = session.createQuery(query.where(predicate)).getResultList();
        }
        catch (NoResultException noResultException) {
            requests_centersList = null;
        }
        finally{
            session.close();
        }
        return requests_centersList;
    }

    public void deleteRequestsCenters(Centers center){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Requests_centers> requests_centersList = find_request_centers_by_center(center);
            for (int i = 0; i < requests_centersList.size(); i++){
                session.delete(requests_centersList.get(i));
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void deleteRequestsCenters(Requests_centers requests_centers){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.delete(requests_centers);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public Requests find_request_by_name(String name){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Requests requests;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Requests> query = builder.createQuery(Requests.class);
            Root<Requests> root = query.from(Requests.class);

            Predicate predicate = builder.equal(root.get("exam_name"), name);
            requests = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            requests = null;
        }
        finally{
            session.close();
        }
        return requests;
    }

    public Requests find_request_by_id(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Requests requests;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Requests> query = builder.createQuery(Requests.class);
            Root<Requests> root = query.from(Requests.class);

            Predicate predicate = builder.equal(root.get("request_id"), id);
            requests = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            requests = null;
        }
        finally{
            session.close();
        }
        return requests;
    }
}
