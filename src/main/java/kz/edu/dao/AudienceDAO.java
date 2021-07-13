package kz.edu.dao;

import kz.edu.model.Audience;
import kz.edu.model.Centers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class AudienceDAO {
    private final SessionFactory sessionFactory;

    public AudienceDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Audience> getAudiencesOfCenter(Centers centers){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Audience> audienceList;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Audience> criteria = builder.createQuery(Audience.class);
            Root<Audience> root = criteria.from(Audience.class);

            Predicate predicate = builder.equal(root.get("centers"), centers);
            audienceList = session.createQuery(criteria.where(predicate)).getResultList();
        } finally {
            session.close();
        }
        return audienceList;
    }

    public void CreateAudience(Audience audience){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.persist(audience);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void EditAudience(Audience audience){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.merge(audience);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public Audience find_audience_by_id(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Audience audience;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Audience> query = builder.createQuery(Audience.class);
            Root<Audience> root = query.from(Audience.class);

            Predicate predicate = builder.equal(root.get("audience_id"), id);
            audience = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            audience = null;
        }
        finally{
            session.close();
        }
        return audience;
    }

    public void deleteAudience(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Audience audience = find_audience_by_id(id);
            session.delete(audience);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

}
