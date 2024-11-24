package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.entity.Lecture;
import backend.service.LectureService;
import backend.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/lecture")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Lecture>>> getAllCategorys() {
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
    public ResponseEntity<ApiResponse<Lecture>> createCategory(
    		@RequestParam("title") String title,
    		@RequestParam("content") String content
    	    ) {      
        try {
        	Lecture lecture = new Lecture();
        	lecture.setTitle(title);
        	lecture.setContent(content);
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
    public ResponseEntity<ApiResponse<Lecture>> updateCourse(
    		@PathVariable String id, 
    		@RequestParam("title") String title,
    		@RequestParam("content") String content
    		){
        
        try {
        	Lecture lecture = lectureService.findById(id);
        	if (lecture == null) {
        		ApiResponse<Lecture> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	lecture.setTitle(title);
        	lecture.setContent(content);
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
