package backend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.entity.Lecture;

@Repository
public class LectureDAOImpl implements LectureDAO{
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@Transactional	
	public List<Lecture> readListLecture() {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<Lecture> theQuery = currentSession.createQuery("FROM Lecture", Lecture.class);
        return theQuery.getResultList();
	}

	@Override
	@Transactional
	public Lecture findById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Lecture.class, id);
	}

	@Override
	@Transactional
	public void createLecture(Lecture theLecture) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(theLecture);
		
	}

	@Override
	@Transactional
	public void updateLecture(Lecture theLecture) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(theLecture);
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Lecture theLecture = currentSession.get(Lecture.class, id);
        currentSession.remove(theLecture);
		
	}
	
}
