package backend.service;

import java.util.List;

import backend.entity.Answer;

public interface AnswerService {
	public List<Answer> readListAnswer();
    public Answer findById(String id);
    public void createAnswer(Answer theAnswer);
    public void updateAnswer(Answer theAnswer);
    public void deleteById(String id);
}
