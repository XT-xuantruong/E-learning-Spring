package backend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.entity.QuizResult;

@Repository
public class QuizResultDAOImpl implements QuizResultDAO{
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@Transactional	
	public List<QuizResult> readListQuizResult() {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<QuizResult> theQuery = currentSession.createQuery("FROM QuizResult", QuizResult.class);
        return theQuery.getResultList();
	}

	@Override
	@Transactional
	public QuizResult findById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(QuizResult.class, id);
	}

	@Override
	@Transactional
	public void createQuizResult(QuizResult theQuizResult) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(theQuizResult);
		
	}

	@Override
	@Transactional
	public void updateQuizResult(QuizResult theQuizResult) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(theQuizResult);
		
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		QuizResult theQuizResult = currentSession.get(QuizResult.class, id);
        currentSession.remove(theQuizResult);
		
	}

	@Override
	@Transactional
	public List<QuizResult> getListQuizResultbyUser(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<QuizResult> theQuery = currentSession.createQuery("FROM QuizResult where user.id= :id", QuizResult.class);
        theQuery.setParameter("id", id);
        return theQuery.getResultList();
	}
	
}
