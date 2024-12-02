package backend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.entity.Answer;

@Repository
public class AnswerDAOImpl implements AnswerDAO{
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@Transactional	
	public List<Answer> readListAnswer() {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<Answer> theQuery = currentSession.createQuery("FROM Answer", Answer.class);
        return theQuery.getResultList();
	}

	@Override
	@Transactional
	public Answer findById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Answer.class, id);
	}

	@Override
	@Transactional
	public void createAnswer(Answer theAnswer) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(theAnswer);
		
	}

	@Override
	@Transactional
	public void updateAnswer(Answer theAnswer) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(theAnswer);
		
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Answer theAnswer = currentSession.get(Answer.class, id);
        currentSession.remove(theAnswer);
		
	}
	
}
