package backend.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.dto.AnswerDTO;
import backend.dto.QuestionDTO;
import backend.dto.QuizDetailsDTO;
import backend.entity.Quiz;

@Repository
public class QuizDAOImpl implements QuizDAO{
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@Transactional	
	public List<Quiz> readListQuiz() {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<Quiz> theQuery = currentSession.createQuery("FROM Quiz", Quiz.class);
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

	@Override
	@Transactional
	public QuizDetailsDTO findQuizWithQuestionsAndAnswers(String id) {
		Session currentSession = sessionFactory.getCurrentSession();

	    List<Object[]> results = currentSession.createQuery(
	        "SELECT q.id, q.title, qs.id, qs.questionText, a.id, a.answerText, a.isCorrect " +
	        "FROM Quiz q " +
	        "LEFT JOIN q.questions qs " +
	        "LEFT JOIN qs.answers a " +
	        "WHERE q.id = :quizId",
	        Object[].class
	    ).setParameter("quizId", id).list();

	    // Map dữ liệu vào DTO
	    QuizDetailsDTO quizDTO = new QuizDetailsDTO();
	    Map<String, QuestionDTO> questionMap = new HashMap<>();

	    for (Object[] row : results) {
	        quizDTO.setId((String) row[0]);
	        quizDTO.setTitle((String) row[1]);

	        String questionId = (String) row[2];
	        QuestionDTO questionDTO = questionMap.computeIfAbsent(questionId, key -> {
	            QuestionDTO newQuestion = new QuestionDTO();
	            newQuestion.setId(questionId);
	            newQuestion.setQuestionText((String) row[3]);
	            return newQuestion;
	        });

	        if (row[4] != null) {
	            AnswerDTO answerDTO = new AnswerDTO();
	            answerDTO.setId((String) row[4]);
	            answerDTO.setAnswerText((String) row[5]);
	            answerDTO.setCorrect((Boolean) row[6]);
	            questionDTO.getAnswers().add(answerDTO);
	        }
	    }

	    quizDTO.setQuestions(new ArrayList<>(questionMap.values()));
	    return quizDTO;
	}

	
}
