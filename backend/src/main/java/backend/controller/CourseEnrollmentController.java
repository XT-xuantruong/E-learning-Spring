package backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import backend.entity.Category;
import backend.entity.Course;
import backend.entity.CourseEnrollment;
import backend.entity.User;
import backend.service.AuthService;
import backend.service.CategoryService;
import backend.service.CourseService;
import backend.service.CourseEnrollmentService;
import backend.util.ApiResponse;


@RestController
@RequestMapping("/api/courseEnrollment")
public class CourseEnrollmentController {
	@Autowired
    private CourseEnrollmentService courseEnrollmentService;
	
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private AuthService userService;
    
    @Autowired
    private CategoryService categoryService;
    
	
    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseEnrollment>>> getAllCourseEnrollment() {
        try {
        	List<CourseEnrollment> courseEnrollment = courseEnrollmentService.readListCourseEnrollment();
            if (courseEnrollment.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            ApiResponse<List<CourseEnrollment>> response = new ApiResponse<>("ok", "Successfully", courseEnrollment);
            return ResponseEntity.ok(response);
        } catch(Exception e){
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        			.body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseEnrollment>> getCourseById(@PathVariable String id) {
    	try {

    		CourseEnrollment courseEnrollment = courseEnrollmentService.findById(id);

            if (courseEnrollment == null) {
                return ResponseEntity.notFound().build();
            }
            ApiResponse<CourseEnrollment> response = new ApiResponse<>("ok", "Successfully", courseEnrollment);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @PostMapping(																																					)
    public ResponseEntity<ApiResponse<CourseEnrollment>> createCourseEnrollment(
    		@RequestParam("user") String user,
    	    @RequestParam("course") String course,
    	    @RequestParam("isPaid") String  isPaid
    ) {
        
        try {
            CourseEnrollment courseEnrollment = new CourseEnrollment();
            
            User u = userService.findById(user);
            courseEnrollment.setUser(u);
            
            Course c = courseService.findById(course);
            courseEnrollment.setCourse(c);
            
        	courseEnrollment.setPaid(Boolean.parseBoolean(isPaid));;
            
            courseEnrollmentService.createCourseEnrollment(courseEnrollment);

            ApiResponse<CourseEnrollment> response = new ApiResponse<>("ok", "Successfully", courseEnrollment);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}

    }

    @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<CourseEnrollment>> updateCourse(
    		@PathVariable String id, 
    		@RequestParam(value ="user", required = false) String user,
    	    @RequestParam(value ="course", required = false) String course,
    	    @RequestParam(value ="isPaid", required = false) String isPaid,
    	    @RequestParam(value ="isFinish", required = false) String isFinish,
    	    @RequestParam(value ="lastLecture", required = false) String lastLecture,
    	    @RequestParam(value ="lastquiz", required = false) String lastquiz
    ){
        
        try {
        	CourseEnrollment courseEnrollment = courseEnrollmentService.findById(id);
        	if (courseEnrollment == null) {
        		ApiResponse<CourseEnrollment> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	
        	if (user != null) {
        		User u = userService.findById(user);
                courseEnrollment.setUser(u);
        	}
        	
            if (course != null) {
            	Course c = courseService.findById(course);
                courseEnrollment.setCourse(c);
            }
            
            if (isPaid != null) {
            	courseEnrollment.setPaid(Boolean.parseBoolean(isPaid));
            }
            
            if (isFinish != null) {
            	courseEnrollment.setFinish(Boolean.parseBoolean(isFinish));
            }
            
            if (lastLecture != null) {
            	courseEnrollment.setLastLecture(lastLecture);
            }
            
            if (lastquiz != null) {
            	courseEnrollment.setLastquiz(lastquiz);
            }
                           
            courseEnrollmentService.updateCourseEnrollment(courseEnrollment);
            ApiResponse<CourseEnrollment> response = new ApiResponse<>("ok", "Successfully", courseEnrollment);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseEnrollment>> deleteCourseEnrollment(@PathVariable String id) {
        try {
        	CourseEnrollment courseEnrollment = courseEnrollmentService.findById(id);
        	if (courseEnrollment == null) {
        		ApiResponse<CourseEnrollment> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	courseEnrollmentService.deleteById(id);

            ApiResponse<CourseEnrollment> response = new ApiResponse<>("ok", "Successfully", courseEnrollment);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
}
