package backend.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class Course {
	@Id
	private String id;
	
	@Column(name = "title")
	private String Title;
	
	@Column(name = "description")
	private String Description;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "thumbnail")
	private String Thumbnail;
	
	@ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category Category_id;
	
	@ManyToOne
    @JoinColumn(name = "create_by", nullable = true)
    private User Create_by;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<CourseEnrollment> courseEnrollments = new ArrayList<>();
	
	@OneToMany(mappedBy = "course",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Quiz> quizzes = new ArrayList<>();
	
	@OneToMany(mappedBy = "course",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Lecture> lectures = new ArrayList<>();
		
	@PrePersist
	protected void onCreate() {
		if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        createdAt = new Date();
	}

	

	public Course() {
		super();
	}



	public Course(String id, String title, String description, Float price, String thumbnail, Category category_id,
			User create_by) {
		super();
		this.id = id;
		Title = title;
		Description = description;
		this.price = price;
		Thumbnail = thumbnail;
		Category_id = category_id;
		Create_by = create_by;
	}



	public Float getPrice() {
		return price;
	}



	public void setPrice(Float price) {
		this.price = price;
	}



	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}


	public List<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	public List<CourseEnrollment> getCourseEnrollments() {
		return courseEnrollments;
	}

	public void setCourseEnrollments(List<CourseEnrollment> courseEnrollments) {
		this.courseEnrollments = courseEnrollments;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getThumbnail() {
		return Thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		Thumbnail = thumbnail;
	}

	public Category getCategory_id() {
		return Category_id;
	}

	public void setCategory_id(Category category_id) {
		Category_id = category_id;
	}

	public User getCreate_by() {
		return Create_by;
	}

	public void setCreate_by(User create_by) {
		Create_by = create_by;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
