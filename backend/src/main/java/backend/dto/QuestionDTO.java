package backend.dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionDTO {
	private String id;
    private String questionText;
    private List<AnswerDTO> answers = new ArrayList<>();
	public QuestionDTO() {
		super();
	}
	public QuestionDTO(String id, String questionText, List<AnswerDTO> answers) {
		super();
		this.id = id;
		this.questionText = questionText;
		this.answers = answers;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public List<AnswerDTO> getAnswers() {
		return answers;
	}
	public void setAnswers(List<AnswerDTO> answers) {
		this.answers = answers;
	}
    
    
}
