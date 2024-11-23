package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.entity.Category;
import backend.entity.Course;
import backend.entity.User;
import backend.service.CourseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.readListCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable UUID id) {
        return courseService.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Course createCourse(
    		@RequestParam("title") String title,
    	    @RequestParam("description") String description,
    	    @RequestParam("thumbnail") String thumbnail,
    	    @RequestParam(value ="category_id",required = false) String categoryId,
    	    @RequestParam(value ="create_by",required = false) String createBy
    	    ) {
    	Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setThumbnail(thumbnail);
        // Handle Category
        if (categoryId != null && !categoryId.equals("null") && !categoryId.trim().isEmpty()) {
            Category category = new Category();
            category.setId(UUID.fromString(categoryId));
            course.setCategory_id(category);
        }
        
        // Handle User
        if (createBy != null && !createBy.equals("null") && !createBy.trim().isEmpty()) {
            User user = new User();
            user.setId(UUID.fromString(createBy));
            course.setCreate_by(user);
        }
        courseService.createCourse(course);
        return course;

    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable UUID id, @RequestBody Course theCourse) {
        theCourse.setId(id);
        courseService.updateCourse(theCourse);
        return theCourse;
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable UUID id) {
        courseService.deleteById(id);
    }
}
