package backend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.entity.CourseEnrollment;

@Repository
public class CourseEnrollmentImpl implements CourseEnrollmentDAO{
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@Transactional	
	public List<CourseEnrollment> readListCourseEnrollment() {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<CourseEnrollment> theQuery = currentSession.createQuery("FROM CourseEnrollment", CourseEnrollment.class);
        return theQuery.getResultList();
	}

	@Override
	@Transactional
	public CourseEnrollment findById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(CourseEnrollment.class, id);
	}

	@Override
	@Transactional
	public void createCourseEnrollment(CourseEnrollment theCourseEnrollment) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(theCourseEnrollment);
		
	}

	@Override
	@Transactional
	public void updateCourseEnrollment(CourseEnrollment theCourseEnrollment) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(theCourseEnrollment);
		
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        CourseEnrollment theCourseEnrollment = currentSession.get(CourseEnrollment.class, id);
        currentSession.remove(theCourseEnrollment);
		
	}
	
}