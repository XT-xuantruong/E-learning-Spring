package backend.dto;

import java.util.List;

public class CourseDTO {
	private String id;
    private String title;
    private List<LectureDTO> lectures;
    private List<QuizDTO> quizs;
	public CourseDTO(String id, String title, List<LectureDTO> lectures, List<QuizDTO> quizs) {
		super();
		this.id = id;
		this.title = title;
		this.lectures = lectures;
		this.quizs = quizs;
	}
	public CourseDTO() {
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
	public List<LectureDTO> getLectures() {
		return lectures;
	}
	public void setLectures(List<LectureDTO> lectures) {
		this.lectures = lectures;
	}
	public List<QuizDTO> getQuizs() {
		return quizs;
	}
	public void setQuizs(List<QuizDTO> quizs) {
		this.quizs = quizs;
	}
    
    
}
