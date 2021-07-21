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
public class ExamsDAO {
    private final SessionFactory sessionFactory;

    public ExamsDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void CreateExam(Exams exams){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.persist(exams);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void EditExam(Exams exams){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.merge(exams);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public List<Exam_types_centers> find_examTypes_centers_by_center(Centers center){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exam_types_centers> exam_types_centersList;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exam_types_centers> query = builder.createQuery(Exam_types_centers.class);
            Root<Exam_types_centers> root = query.from(Exam_types_centers.class);

            Predicate predicate = builder.equal(root.get("centers"), center);
            exam_types_centersList = session.createQuery(query.where(predicate)).getResultList();
        }
        catch (NoResultException noResultException) {
            exam_types_centersList = null;
        }
        finally{
            session.close();
        }
        return exam_types_centersList;
    }

    public void deleteExamTypesCenters(Centers center){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Exam_types_centers> exam_types_centersList = find_examTypes_centers_by_center(center);
            for (int i = 0; i < exam_types_centersList.size(); i++){
                session.delete(exam_types_centersList.get(i));
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void removeExamTypesCenters(Exam_types_centers exam_types_centers){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.delete(exam_types_centers);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public List<Exams_centers> find_exam_centers_by_center(Centers center){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exams_centers> exams_centersList;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exams_centers> query = builder.createQuery(Exams_centers.class);
            Root<Exams_centers> root = query.from(Exams_centers.class);

            Predicate predicate = builder.equal(root.get("centers"), center);
            exams_centersList = session.createQuery(query.where(predicate)).getResultList();
        }
        catch (NoResultException noResultException) {
            exams_centersList = null;
        }
        finally{
            session.close();
        }
        return exams_centersList;
    }

    public List<Exams_centers> find_exam_centers_by_exam(Exams exams){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exams_centers> exams_centersList;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exams_centers> query = builder.createQuery(Exams_centers.class);
            Root<Exams_centers> root = query.from(Exams_centers.class);

            Predicate predicate = builder.equal(root.get("exams"), exams);
            exams_centersList = session.createQuery(query.where(predicate)).getResultList();
        }
        catch (NoResultException noResultException) {
            exams_centersList = null;
        }
        finally{
            session.close();
        }
        return exams_centersList;
    }

    public void deleteExamCenters(Centers center){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Exams_centers> exams_centersList = find_exam_centers_by_center(center);
            for (int i = 0; i < exams_centersList.size(); i++){
                session.delete(exams_centersList.get(i));
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void deleteExamCenters(Exams exams){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            List<Exams_centers> exams_centersList = find_exam_centers_by_exam(exams);
            for (int i = 0; i < exams_centersList.size(); i++){
                session.delete(exams_centersList.get(i));
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void deleteExam(Exams exams){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.delete(exams);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }


    public void CreateExamCenters(Exams_centers exams_centers){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.persist(exams_centers);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void EditExamCenters(Exams_centers exams_centers){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.merge(exams_centers);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void AddExamTypesCenters(Exam_types_centers exam_types_centers){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.persist(exam_types_centers);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public void EditExamTypesCenters(Exam_types_centers exam_types_centers){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try{
            session.merge(exam_types_centers);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }
    }

    public Exam_Types find_examType_by_name(String name){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Exam_Types exam_types;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exam_Types> query = builder.createQuery(Exam_Types.class);
            Root<Exam_Types> root = query.from(Exam_Types.class);

            Predicate predicate = builder.equal(root.get("exam_type_name"), name);
            exam_types = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            exam_types = null;
        }
        finally{
            session.close();
        }
        return exam_types;
    }

    public Exams findExamByRequest(Requests requests){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Exams exams;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exams> query = builder.createQuery(Exams.class);
            Root<Exams> root = query.from(Exams.class);

            Predicate predicate = builder.equal(root.get("requests"), requests);
            exams = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            exams = null;
        }
        finally{
            session.close();
        }
        return exams;
    }

    public Exams findExamById(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Exams exams;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exams> query = builder.createQuery(Exams.class);
            Root<Exams> root = query.from(Exams.class);

            Predicate predicate = builder.equal(root.get("exam_id"), id);
            exams = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            exams = null;
        }
        finally{
            session.close();
        }
        return exams;
    }

    public Exams_centers findExamCentersById(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Exams_centers exams_centers;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exams_centers> query = builder.createQuery(Exams_centers.class);
            Root<Exams_centers> root = query.from(Exams_centers.class);

            Predicate predicate = builder.equal(root.get("ec"), id);
            exams_centers = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            exams_centers = null;
        }
        finally{
            session.close();
        }
        return exams_centers;
    }

    public Exam_types_centers findExamTypeCentersById(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Exam_types_centers exam_types_centers;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exam_types_centers> query = builder.createQuery(Exam_types_centers.class);
            Root<Exam_types_centers> root = query.from(Exam_types_centers.class);

            Predicate predicate = builder.equal(root.get("etc"), id);
            exam_types_centers = session.createQuery(query.where(predicate)).getSingleResult();
        }
        catch (NoResultException noResultException) {
            exam_types_centers = null;
        }
        finally{
            session.close();
        }
        return exam_types_centers;
    }

    public List<Exam_types_centers> getCentersOfExamType(Exam_Types exam_types){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exam_types_centers> exam_types_centersList;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exam_types_centers> query = builder.createQuery(Exam_types_centers.class);
            Root<Exam_types_centers> root = query.from(Exam_types_centers.class);

            Predicate predicate = builder.equal(root.get("exam_types"), exam_types);
            exam_types_centersList = session.createQuery(query.where(predicate)).getResultList();
        }
        catch (NoResultException noResultException) {
            exam_types_centersList = null;
        }
        finally{
            session.close();
        }
        return exam_types_centersList;
    }

    public List<Exam_types_centers> getExamTypesOfCenter(Centers centers){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exam_types_centers> exam_types_centersList;
        try{
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exam_types_centers> query = builder.createQuery(Exam_types_centers.class);
            Root<Exam_types_centers> root = query.from(Exam_types_centers.class);

            Predicate predicate = builder.equal(root.get("centers"), centers);
            exam_types_centersList = session.createQuery(query.where(predicate)).getResultList();
        }
        catch (NoResultException noResultException) {
            exam_types_centersList = null;
        }
        finally{
            session.close();
        }
        return exam_types_centersList;
    }

    public List<Exams> getAllExams(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exams> examsList;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exams> criteria = builder.createQuery(Exams.class);
            Root<Exams> root = criteria.from(Exams.class);
            criteria.select(root);
            Query<Exams> query = session.createQuery(criteria);
            examsList = query.getResultList();
        } finally {
            session.close();
        }
        return examsList;
    }

    public List<Exam_Types> getAllExamTypes(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exam_Types> exam_typesList;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exam_Types> criteria = builder.createQuery(Exam_Types.class);
            Root<Exam_Types> root = criteria.from(Exam_Types.class);
            criteria.select(root);
            Query<Exam_Types> query = session.createQuery(criteria);
            exam_typesList = query.getResultList();
        } finally {
            session.close();
        }
        return exam_typesList;
    }

    public List<Exams_centers> getAllExams_Centers(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Exams_centers> exams_centersList;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Exams_centers> criteria = builder.createQuery(Exams_centers.class);
            Root<Exams_centers> root = criteria.from(Exams_centers.class);
            criteria.select(root);
            Query<Exams_centers> query = session.createQuery(criteria);
            exams_centersList = query.getResultList();
        } finally {
            session.close();
        }
        return exams_centersList;
    }
}
