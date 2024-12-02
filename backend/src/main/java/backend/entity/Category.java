package backend.entity;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name = "category")
public class Category {
	
	@Id
    private String id;
	
	@Column(name = "title")
	private String Title;
	
	@Column(name = "slug",unique = true)
	private String Slug;
	
	@OneToMany(mappedBy = "Category_id", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Course> courses = new ArrayList<>();
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@PrePersist
    protected void onCreate() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        createdAt = new Date();
        generateSlug();
    }

    @PreUpdate
    protected void onUpdate() {
        generateSlug();
    }
	private void generateSlug() {
        if (Title != null && !Title.isEmpty()) {
            // Convert to lowercase
            String slugValue = Title.toLowerCase();
            
            // Remove diacritical marks (accents)
            slugValue = Normalizer.normalize(slugValue, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            
            // Replace spaces with hyphens
            slugValue = slugValue.replaceAll("\\s+", "-");
            
            // Remove special characters
            slugValue = slugValue.replaceAll("[^a-z0-9-]", "");
            
            // Remove multiple consecutive hyphens
            slugValue = slugValue.replaceAll("-+", "-");
            
            // Remove leading and trailing hyphens
            slugValue = slugValue.replaceAll("^-|-$", "");
            
            this.Slug = slugValue;
        }
    }

	public Category(String id,String title, String slug, Date createdAt) {
		super();
		this.id = id;
		this.Title = title;
        this.Slug = slug;
        this.createdAt = createdAt;
	}

	public Category() {
		super();
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
