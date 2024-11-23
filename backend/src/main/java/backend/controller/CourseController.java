package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.entity.Category;
import backend.entity.Course;
import backend.entity.User;
import backend.service.CourseService;
import backend.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses() {
        try {
        	List<Course> courses = courseService.readListCourses();
            if (courses.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            ApiResponse<List<Course>> response = new ApiResponse<>("ok", "Successfully", courses);
            return ResponseEntity.ok(response);
        } catch(Exception e){
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable String id) {
    	try {

    		Course course = courseService.findById(id);
    		System.out.print(course);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            ApiResponse<Course> response = new ApiResponse<>("ok", "Successfully", course);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}
    }

    @PostMapping(																																					)
    public ResponseEntity<ApiResponse<Course>> createCourse(
    		@RequestParam("title") String title,
    	    @RequestParam("description") String description,
    	    @RequestParam("thumbnail") String thumbnail
    	    ) {
        // Handle Category
//        if (categoryId != null && !categoryId.equals("null") && !categoryId.trim().isEmpty()) {
//            Category category = new Category();
//            category.setId(categoryId);
//            course.setCategory_id(category);
//        }
//        
//        // Handle User
//        if (createBy != null && !createBy.equals("null") && !createBy.trim().isEmpty()) {
//            User user = new User();
//            user.setId(createBy);
//            course.setCreate_by(user);
//        }
        
        try {
        	Course course = new Course();
            course.setTitle(title);
            course.setDescription(description);
            course.setThumbnail(thumbnail);
        	courseService.createCourse(course);
    		System.out.print(course);
            ApiResponse<Course> response = new ApiResponse<>("ok", "Successfully", course);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}

    }

    @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable String id, @RequestParam("title") String title
){
        
        try {
        	Course course = courseService.findById(id);
        	if (course == null) {
        		ApiResponse<Course> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
            course.setTitle(title);
            courseService.updateCourse(course);
    		System.out.print(course);
            ApiResponse<Course> response = new ApiResponse<>("ok", "Successfully", course);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> deleteCourse(@PathVariable String id) {
        try {
        	Course course = courseService.findById(id);
        	if (course == null) {
        		ApiResponse<Course> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	courseService.deleteById(id);
    		System.out.print(course);
            ApiResponse<Course> response = new ApiResponse<>("ok", "Successfully", course);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	}
    }
}
