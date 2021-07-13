package kz.edu.dao;

import kz.edu.model.Areas;
import kz.edu.model.Centers;
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
public class AreaDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public AreaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Areas> getAllAreas(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Areas> areasList;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Areas> criteria = builder.createQuery(Areas.class);
            Root<Areas> root = criteria.from(Areas.class);
            criteria.select(root);
            Query<Areas> query = session.createQuery(criteria);
            areasList = query.getResultList();
        } finally {
            session.close();
        }
        return areasList;
    }

    public Areas find_area_by_name(String area_name){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Areas areas;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Areas> query = builder.createQuery(Areas.class);
            Root<Areas> root = query.from(Areas.class);

            Predicate predicate = builder.equal(root.get("area_name"), area_name);
            areas = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            areas = null;
        }
        finally{
            session.close();
        }
        return areas;
    }
}
