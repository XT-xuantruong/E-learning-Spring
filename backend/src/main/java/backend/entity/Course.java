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
@Table(name = "course")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "title")
	private String Title;
	
	@Column(name = "description")
	private String Description;
	
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
	
	@PrePersist
	protected void onCreate() {
	  createdAt = new Date();
	}

	public Course(UUID id, String title, String description, String thumbnail, Category category_id, User create_by,
			Date createdAt) {
		super();
		this.id = id;
		Title = title;
		Description = description;
		Thumbnail = thumbnail;
		Category_id = category_id;
		Create_by = create_by;
		this.createdAt = createdAt;
	}

	public Course() {
		super();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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
