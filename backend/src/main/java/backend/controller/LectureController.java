package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import backend.entity.Course;
import backend.entity.Lecture;
import backend.service.CourseService;
import backend.service.LectureService;
import backend.util.ApiResponse;
import jakarta.servlet.ServletContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/lecture")
public class LectureController {

    @Autowired
    private LectureService lectureService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
	ServletContext context;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Lecture>>> getAllLecture() {
        try {
        	List<Lecture> category = lectureService.readListLecture();
            if (category.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            ApiResponse<List<Lecture>> response = new ApiResponse<>("ok", "Successfully", category);
            return ResponseEntity.ok(response);
        } catch(Exception e){
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        			.body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Lecture>> getLectureById(@PathVariable String id) {
    	try {

    		Lecture lecture = lectureService.findById(id);
    		System.out.print(lecture);
            if (lecture == null) {
                return ResponseEntity.notFound().build();
            }
            ApiResponse<Lecture> response = new ApiResponse<>("ok", "Successfully", lecture);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Lecture>> createLecture(
    		@RequestParam("title") String title,
    		@RequestParam("content") MultipartFile content,
    		@RequestParam("course") String course
    	    ) {      
        try {
        	String projectPath = context.getRealPath("resources/");
        	Path uploadPath = Paths.get(projectPath, "lecture").toAbsolutePath();
        	// Tạo thư mục nếu chưa tồn tại
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
        	Lecture lecture = new Lecture();
        	
        	lecture.setTitle(title);
        	
            Path filePath = uploadPath.resolve(content.getOriginalFilename());
        	Files.copy(content.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        	
            String fileUrl = "/resources/lecture/" + content.getOriginalFilename();
        	lecture.setContent(fileUrl);
        	
        	Course c = courseService.findById(course);
        	lecture.setCourse(c);
        	
        	lectureService.createLecture(lecture);
    		System.out.print(lecture);
            ApiResponse<Lecture> response = new ApiResponse<>("ok", "Successfully", lecture);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}

    }

    @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Lecture>> updateLecture(
    		@PathVariable String id, 
    		@RequestParam(value="title",required=false) String title,
    		@RequestParam(value="content",required=false) MultipartFile content,
    		@RequestParam(value="course",required=false) String course
    		){
        
        try {
        	String projectPath = context.getRealPath("resources/");
        	Path uploadPath = Paths.get(projectPath, "lecture").toAbsolutePath();
        	Lecture lecture = lectureService.findById(id);
        	if (lecture == null) {
        		ApiResponse<Lecture> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	if(title !=null) {
        		lecture.setTitle(title);
        	}
        	if(content != null) {
        		Path filePath = uploadPath.resolve(content.getOriginalFilename());
            	Files.copy(content.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                String fileUrl = "/resources/lecture/" + content.getOriginalFilename();
            	lecture.setContent(fileUrl);
        	}
        	if(course != null) {
        		Course c = courseService.findById(course);
            	lecture.setCourse(c);
        	}
        	lectureService.updateLecture(lecture);
    		System.out.print(lecture);
            ApiResponse<Lecture> response = new ApiResponse<>("ok", "Successfully",lecture);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Lecture>> deleteLecture(@PathVariable String id) {
        try {
        	Lecture lecture = lectureService.findById(id);
        	if (lecture == null) {
        		ApiResponse<Lecture> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	lectureService.deleteById(id);
    		System.out.print(lecture);
            ApiResponse<Lecture> response = new ApiResponse<>("ok", "Successfully", lecture);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
}
