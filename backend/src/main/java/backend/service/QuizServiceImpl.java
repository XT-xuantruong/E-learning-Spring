package backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dao.QuizDAO;
import backend.dto.QuizDetailsDTO;
import backend.dto.QuizMapper;
import backend.entity.Quiz;

@Service
@Transactional
public class QuizServiceImpl implements QuizService{
	@Autowired
    private QuizDAO quizDAO;
	
	@Autowired
    private QuizMapper quizMapper;

	@Override
	public List<Quiz> readListQuiz() {
		return quizDAO.readListQuiz();
	}

	@Override
	public Quiz findById(String id) {
		return quizDAO.findById(id);
	}

	@Override
	public void createQuiz(Quiz theQuiz) {
		quizDAO.createQuiz(theQuiz);
	}

	@Override
	public void updateQuiz(Quiz theQuiz) {
		quizDAO.updateQuiz(theQuiz);
	}

	@Override
	public void deleteById(String id) {
		quizDAO.deleteById(id);
	}
	@Override
    public QuizDetailsDTO getQuizDetails(String id) {
        QuizDetailsDTO quiz = quizDAO.findQuizWithQuestionsAndAnswers(id);
        return quiz;
    }

}
