package backend.dto;

public class QuizDTO {
	private String id;
    private String title;
	public QuizDTO(String id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	public QuizDTO() {
		super();
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
    
}
