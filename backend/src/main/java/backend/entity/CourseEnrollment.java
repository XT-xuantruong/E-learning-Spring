package backend.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "course_enrollment")
public class CourseEnrollment {
	@Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enroll_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrollAt;
    
    @Column(name = "price")
	private Float price;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;
    
    @Column(name = "is_finish", nullable = true)
    private boolean isFinish;
    
    @Column(name = "last_lecture", nullable = true)
    private String lastLecture;
    
    @Column(name = "last_quiz", nullable = true)
    private String lastquiz;

    @PrePersist
    protected void onCreate() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        enrollAt = new Date();
    }

	public CourseEnrollment() {
		super();
	}

	public CourseEnrollment(String id, User user, Course course, Date enrollAt, Float price, boolean isPaid,
			boolean isFinish, String lastLecture, String lastquiz) {
		super();
		this.id = id;
		this.user = user;
		this.course = course;
		this.enrollAt = enrollAt;
		this.price = price;
		this.isPaid = isPaid;
		this.isFinish = isFinish;
		this.lastLecture = lastLecture;
		this.lastquiz = lastquiz;
	}
	
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Date getEnrollAt() {
		return enrollAt;
	}

	public void setEnrollAt(Date enrollAt) {
		this.enrollAt = enrollAt;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}


	public boolean isFinish() {
		return isFinish;
	}


	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}


	public String getLastLecture() {
		return lastLecture;
	}


	public void setLastLecture(String lastLecture) {
		this.lastLecture = lastLecture;
	}


	public String getLastquiz() {
		return lastquiz;
	}


	public void setLastquiz(String lastquiz) {
		this.lastquiz = lastquiz;
	}
	
    
}
