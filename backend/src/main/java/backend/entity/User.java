package backend.entity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.common.hash.Hashing;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
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
	
	@OneToMany(mappedBy = "create_by", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Course> courses = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseEnrollment> courseEnrollments = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();
	
	public User(UUID id, String firstName, String lastName, String avatar, Role role, String email, String password,
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
	
	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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
	  createdAt =updatedAt = new Date();
	}

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

}
