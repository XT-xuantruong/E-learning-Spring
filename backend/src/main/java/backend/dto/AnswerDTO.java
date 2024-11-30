package backend.dto;

public class AnswerDTO {
	 private String id;
	 private String answerText;
	 private boolean isCorrect;
	public AnswerDTO(String id, String answerText, boolean isCorrect) {
		super();
		this.id = id;
		this.answerText = answerText;
		this.isCorrect = isCorrect;
	}
	public AnswerDTO() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	 
}
