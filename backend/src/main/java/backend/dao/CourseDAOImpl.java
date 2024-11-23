package backend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.entity.Course;

@Repository
public class CourseDAOImpl implements CourseDAO{
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@Transactional	
	public List<Course> readListCourses() {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<Course> theQuery = currentSession.createQuery("FROM Course", Course.class);
        return theQuery.getResultList();
	}

	@Override
	@Transactional
	public Course findById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Course.class, id);
	}

	@Override
	@Transactional
	public void createCourse(Course theCourse) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(theCourse);
		
	}

	@Override
	@Transactional
	public void updateCourse(Course theCourse) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(theCourse);
		
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        Course theCourse = currentSession.get(Course.class, id);
        currentSession.remove(theCourse);
		
	}
	
}
