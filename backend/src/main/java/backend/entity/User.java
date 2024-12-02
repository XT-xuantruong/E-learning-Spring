package backend.entity;

import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.common.hash.Hashing;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {
	@Id
	private String id;
	
	@Column(name = "first_name")
	private String firstName;
	    
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "avatar")    
	private String avatar;
	
	@Enumerated(EnumType.STRING) 
	private Role role;
	    
	@Column(name = "email",unique = true)
	private String email;
	
	@Column(name = "password")    
	private String password;
	    
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	    
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@JsonIgnore
	@OneToMany(mappedBy = "Create_by", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Course> courses = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseEnrollment> courseEnrollments = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuizResult> quizResults = new ArrayList<>();
	
	public User(String id, String firstName, String lastName, String avatar, Role role, String email, String password,
			Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.avatar = avatar;
		this.role = role;
		this.email = email;
		this.password = password;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public User() {
		super();
	}

	public List<CourseEnrollment> getCourseEnrollments() {
		return courseEnrollments;
	}

	public void setCourseEnrollments(List<CourseEnrollment> courseEnrollments) {
		this.courseEnrollments = courseEnrollments;
	}

	public List<Course> getCourses() {
		return courses;
	}

	
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<QuizResult> getQuizResults() {
		return quizResults;
	}

	public void setQuizResults(List<QuizResult> quizResults) {
		this.quizResults = quizResults;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Hashing.sha256()
				  .hashString(password, StandardCharsets.UTF_8)
				  .toString();
	}

	@PrePersist
	protected void onCreate() {
		if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        createdAt = new Date();
	}

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

}
