package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import backend.dto.CourseDTO;
import backend.entity.Category;
import backend.entity.Course;
import backend.entity.User;
import backend.service.AuthService;
import backend.service.CategoryService;
import backend.service.CourseService;
import backend.util.ApiResponse;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private AuthService userService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
	ServletContext context;
	
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
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        			.body(new ApiResponse<>("error", e.getMessage(), null));
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
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
    @GetMapping("/get-more/{id}")
    public ResponseEntity<ApiResponse<CourseDTO>> getCourseByIdWithLectureAndQuiz(
    		@PathVariable String id) {
    	try {

    		CourseDTO course = courseService.findByIdWithLectureAndQuiz(id);
    		System.out.print(course);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            ApiResponse<CourseDTO> response = new ApiResponse<CourseDTO>("ok", "Successfully", course);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @PostMapping(																																					)
    public ResponseEntity<ApiResponse<Course>> createCourse(
    		@RequestParam("title") String title,
    	    @RequestParam("description") String description,
    	    @RequestParam("thumbnail") MultipartFile  thumbnail,
    	    @RequestParam("createBy") String createBy,
    	    @RequestParam("category") String category,
    	    @RequestParam("price") String price
    ) {
        
        try {
        	String projectPath = context.getRealPath("resources/");
        	Path uploadPath = Paths.get(projectPath, "images", "thumbnail").toAbsolutePath();
        	System.out.print(projectPath);
        	if (thumbnail.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                		.body(new ApiResponse<>("error", "thumbnail invalid", null));
            }
        	
        	String fileName = UUID.randomUUID().toString() + "_" + thumbnail.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(thumbnail.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String fileUrl = "/resources/images/thumbnail/" + fileName;
            
        	Course course = new Course();
            course.setTitle(title);
            course.setDescription(description);
            course.setThumbnail(fileUrl);
            
            User user = userService.findById(createBy);
            course.setCreate_by(user);
            
            Category cate = categoryService.findById(category);
            course.setCategory_id(cate);
            
            course.setPrice(Float.parseFloat(price));
            
        	courseService.createCourse(course);

            ApiResponse<Course> response = new ApiResponse<>("ok", "Successfully", course);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}

    }

    @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Course>> updateCourse(
    		@PathVariable String id, 
    		@RequestParam(value ="title", required = false) String title,
    	    @RequestParam(value ="description", required = false) String description,
    	    @RequestParam(value ="thumbnail", required = false) MultipartFile  thumbnail,
    	    @RequestParam(value ="createBy", required = false) String createBy,
    	    @RequestParam(value ="category", required = false) String category
    ){
        
        try {
        	String projectPath = context.getRealPath("resources/");
        	Path uploadPath = Paths.get(projectPath, "images", "thumbnail").toAbsolutePath();
        	
        	Course course = courseService.findById(id);
        	if (course == null) {
        		ApiResponse<Course> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	
        	if (title != null) {
        		course.setTitle(title);
        	}
        	
            if (description != null) {
            	course.setDescription(description);
            }
            
            if(thumbnail !=null) {
            	String fileName = UUID.randomUUID().toString() + "_" + thumbnail.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(thumbnail.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                String fileUrl = "/resources/images/thumbnail/" + fileName;
                
                course.setThumbnail(fileUrl);
            }
            
            if(createBy != null) {
            	User user = userService.findById(createBy);
                course.setCreate_by(user);
            }
            if(category!=null) {
            	Category cate = categoryService.findById(category);
                course.setCategory_id(cate);
            }
                           
            courseService.updateCourse(course);
            ApiResponse<Course> response = new ApiResponse<>("ok", "Successfully", course);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
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
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
}
