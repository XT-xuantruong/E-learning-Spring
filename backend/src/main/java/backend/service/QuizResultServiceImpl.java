package backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dao.QuizResultDAO;
import backend.entity.QuizResult;

@Service
@Transactional
public class QuizResultServiceImpl implements QuizResultService{
	@Autowired
    private QuizResultDAO quizResultDAO;

	@Override
	public List<QuizResult> readListQuizResult() {
		return quizResultDAO.readListQuizResult();
	}

	@Override
	public QuizResult findById(String id) {
		return quizResultDAO.findById(id);
	}

	@Override
	public void createQuizResult(QuizResult theQuizResult) {
		quizResultDAO.createQuizResult(theQuizResult);
	}

	@Override
	public void updateQuizResult(QuizResult theQuizResult) {
		quizResultDAO.updateQuizResult(theQuizResult);
	}

	@Override
	public void deleteById(String id) {
		quizResultDAO.deleteById(id);
	}

}
