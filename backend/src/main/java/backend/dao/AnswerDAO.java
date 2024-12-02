package backend.dao;

import java.util.List;

import backend.entity.Answer;

public interface AnswerDAO {
	public List<Answer> readListAnswer();
    public Answer findById(String id);
    public void createAnswer(Answer theAnswer);
    public void updateAnswer(Answer theAnswer);
    public void deleteById(String id);
}
