package backend.service;

import java.util.List;

import backend.dto.QuizDetailsDTO;
import backend.entity.Quiz;

public interface QuizService {
	public List<Quiz> readListQuiz();
    public Quiz findById(String id);
    public void createQuiz(Quiz theQuiz);
    public void updateQuiz(Quiz theQuiz);
    public void deleteById(String id);
    public QuizDetailsDTO getQuizDetails(String id);
}
