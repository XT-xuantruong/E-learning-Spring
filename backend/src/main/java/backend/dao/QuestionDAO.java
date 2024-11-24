package backend.dao;

import java.util.List;

import backend.entity.Question;

public interface QuestionDAO {
	public List<Question> readListQuestion();
    public Question findById(String id);
    public void createQuestion(Question theQuestion);
    public void updateQuestion(Question theQuestion);
    public void deleteById(String id);
}
