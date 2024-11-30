package backend.service;

import java.util.List;

import backend.entity.Question;

public interface QuestionService {
	public List<Question> readListQuestion();
    public Question findById(String id);
    public void createQuestion(Question theQuestion);
    public void updateQuestion(Question theQuestion);
    public void deleteById(String id);
}
