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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enroll_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrollAt;

    @Column(name = "ispaid", nullable = false)
    private boolean isPaid;

    @PrePersist
    protected void onEnroll() {
        enrollAt = new Date();
    }

	public CourseEnrollment(UUID id, User user, Course course, Date enrollAt, boolean isPaid) {
		super();
		this.id = id;
		this.user = user;
		this.course = course;
		this.enrollAt = enrollAt;
		this.isPaid = isPaid;
	}

	public CourseEnrollment() {
		super();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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
    
}
