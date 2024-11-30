package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.entity.Course;
import backend.entity.Quiz;
import backend.service.CourseService;
import backend.service.QuizService;
import backend.util.ApiResponse;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;
    
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Quiz>>> getAllQuiz() {
        try {
        	List<Quiz> quiz = quizService.readListQuiz();
            if (quiz.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            ApiResponse<List<Quiz>> response = new ApiResponse<>("ok", "Successfully", quiz);
            return ResponseEntity.ok(response);
        } catch(Exception e){
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        			.body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Quiz>> getQuizById(@PathVariable String id) {
    	try {

    		Quiz quiz = quizService.findById(id);
    		System.out.print(quiz);
            if (quiz == null) {
                return ResponseEntity.notFound().build();
            }
            ApiResponse<Quiz> response = new ApiResponse<>("ok", "Successfully", quiz);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Quiz>> createQuiz(
    		@RequestParam("title") String title,
    		@RequestParam("course") String course
    	    ) {      
        try {
        	Quiz quiz = new Quiz();
        	quiz.setTitle(title);
        	Course c = courseService.findById(course);
        	quiz.setCourse(c);
        	quizService.createQuiz(quiz);
            ApiResponse<Quiz> response = new ApiResponse<>("ok", "Successfully", quiz);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}

    }

    @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Quiz>> updateQuiz(
    		@PathVariable String id, 
    		@RequestParam(value="title",required=false) String title,
    		@RequestParam(value="course",required=false) String course
    		){
        
        try {
        	Quiz quiz = quizService.findById(id);
        	if (quiz == null) {
        		ApiResponse<Quiz> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	if(title != null) {
        		quiz.setTitle(title);
        	}
        	if(course!=null) {
        		Course c = courseService.findById(course);
            	quiz.setCourse(c);
        	}
            quizService.updateQuiz(quiz);

            ApiResponse<Quiz> response = new ApiResponse<>("ok", "Successfully", quiz);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Quiz>> deleteQuiz(@PathVariable String id) {
        try {
        	Quiz quiz = quizService.findById(id);
        	if (quiz == null) {
        		ApiResponse<Quiz> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	quizService.deleteById(id);
    		System.out.print(quiz);
            ApiResponse<Quiz> response = new ApiResponse<>("ok", "Successfully", quiz);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
}
