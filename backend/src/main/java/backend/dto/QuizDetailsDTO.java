package backend.dto;

import java.util.List;

public class QuizDetailsDTO {
	private String id;
    private String title;
    private List<QuestionDTO> questions;
	public QuizDetailsDTO() {
		super();
	}
	public QuizDetailsDTO(String id, String title, List<QuestionDTO> questions) {
		super();
		this.id = id;
		this.title = title;
		this.questions = questions;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<QuestionDTO> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}
    
    
}
