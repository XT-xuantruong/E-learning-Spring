package backend.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "category")
public class Category {
	
	@Column(name = "title")
	private String Title;
	
	@Column(name = "slug")
	private String Slug;
	
	@OneToMany(mappedBy = "category_id", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Course> courses = new ArrayList<>();
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@PrePersist
	protected void onCreate() {
	  createdAt = new Date();
	}

	public Category(String title, String slug, List<Course> courses, Date createdAt) {
		super();
		Title = title;
		Slug = slug;
		this.courses = courses;
		this.createdAt = createdAt;
	}

	public Category() {
		super();
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getSlug() {
		return Slug;
	}

	public void setSlug(String slug) {
		Slug = slug;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
