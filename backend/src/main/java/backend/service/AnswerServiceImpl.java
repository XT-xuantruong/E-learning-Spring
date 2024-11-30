package backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dao.AnswerDAO;
import backend.entity.Answer;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService{
	@Autowired
    private AnswerDAO answerDAO;

	@Override
	public List<Answer> readListAnswer() {
		return answerDAO.readListAnswer();
	}

	@Override
	public Answer findById(String id) {
		return answerDAO.findById(id);
	}

	@Override
	public void createAnswer(Answer theAnswer) {
		answerDAO.createAnswer(theAnswer);
	}

	@Override
	public void updateAnswer(Answer theAnswer) {
		answerDAO.updateAnswer(theAnswer);
	}

	@Override
	public void deleteById(String id) {
		answerDAO.deleteById(id);
	}

}
