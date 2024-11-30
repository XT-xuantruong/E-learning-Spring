package backend.dao;

import java.util.List;

import backend.entity.QuizResult;

public interface QuizResultDAO {
	public List<QuizResult> readListQuizResult();
    public QuizResult findById(String id);
    public void createQuizResult(QuizResult theQuizResult);
    public void updateQuizResult(QuizResult theQuizResult);
    public void deleteById(String id);
}
