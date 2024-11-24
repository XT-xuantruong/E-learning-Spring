package backend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.entity.Quiz;

@Repository
public class QuizDAOImpl implements QuizDAO{
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@Transactional	
	public List<Quiz> readListQuiz() {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<Quiz> theQuery = currentSession.createQuery("FROM Category", Quiz.class);
        return theQuery.getResultList();
	}

	@Override
	@Transactional
	public Quiz findById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Quiz.class, id);
	}

	@Override
	@Transactional
	public void createQuiz(Quiz theQuiz) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(theQuiz);
		
	}

	@Override
	@Transactional
	public void updateQuiz(Quiz theQuiz) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(theQuiz);
		
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Quiz theQuiz = currentSession.get(Quiz.class, id);
        currentSession.remove(theQuiz);
		
	}
	
}
