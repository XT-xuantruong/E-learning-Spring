package backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dao.QuestionDAO;
import backend.entity.Answer;
import backend.entity.Question;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{
	@Autowired
    private QuestionDAO questionDAO;

	@Override
	public List<Question> readListQuestion() {
		return questionDAO.readListQuestion();
	}

	@Override
	public Question findById(String id) {
		return questionDAO.findById(id);
	}

	@Override
	public void createQuestion(Question theQuestion) {
		questionDAO.createQuestion(theQuestion);
	}

	@Override
	public void updateQuestion(Question theQuestion) {
		questionDAO.updateQuestion(theQuestion);
	}

	@Override
	public void deleteById(String id) {
		questionDAO.deleteById(id);
	}

	@Override
	public List<Answer> getAnswers(String id) {
		
		return questionDAO.getAnswers(id);
	}

}
