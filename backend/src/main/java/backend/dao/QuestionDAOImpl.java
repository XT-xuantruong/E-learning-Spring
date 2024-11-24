package backend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.entity.Question;

@Repository
public class QuestionDAOImpl implements QuestionDAO{
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@Transactional	
	public List<Question> readListQuestion() {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<Question> theQuery = currentSession.createQuery("FROM Question", Question.class);
        return theQuery.getResultList();
	}

	@Override
	@Transactional
	public Question findById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Question.class, id);
	}

	@Override
	@Transactional
	public void createQuestion(Question theQuestion) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(theQuestion);
		
	}

	@Override
	@Transactional
	public void updateQuestion(Question theQuestion) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(theQuestion);
		
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Question theQuestion = currentSession.get(Question.class, id);
        currentSession.remove(theQuestion);
		
	}
	
}
