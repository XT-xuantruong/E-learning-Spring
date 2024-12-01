package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.entity.Course;
import backend.entity.Quiz;
import backend.entity.QuizResult;
import backend.entity.User;
import backend.service.AuthService;
import backend.service.CourseService;
import backend.service.QuizResultService;
import backend.service.QuizService;
import backend.util.ApiResponse;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/quiz-result")
public class QuizResultController {

    @Autowired
    private QuizResultService quizResultService;
    
    @Autowired
    private QuizService quizService;
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuizResult>>> getAllQuizResult() {
        try {
        	List<QuizResult> quiz_result = quizResultService.readListQuizResult();
            if (quiz_result.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            ApiResponse<List<QuizResult>> response = new ApiResponse<>("ok", "Successfully", quiz_result);
            return ResponseEntity.ok(response);
        } catch(Exception e){
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        			.body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }
    @GetMapping("/get-by-user/{id}")
    public ResponseEntity<ApiResponse<List<QuizResult>>> getQuizResultByUser(
    		@PathVariable String id) {
        try {
        	List<QuizResult> quiz_result = quizResultService.getListQuizResultbyUser(id);
            if (quiz_result.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            ApiResponse<List<QuizResult>> response = new ApiResponse<>("ok", "Successfully", quiz_result);
            return ResponseEntity.ok(response);
        } catch(Exception e){
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        			.body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizResult>> getQuizResultById(@PathVariable String id) {
    	try {

    		QuizResult quiz_result = quizResultService.findById(id);
            if (quiz_result == null) {
                return ResponseEntity.notFound().build();
            }
            ApiResponse<QuizResult> response = new ApiResponse<>("ok", "Successfully", quiz_result);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<QuizResult>> createQuizResult(
    		@RequestParam("score") String score,
    		@RequestParam("quiz") String quiz,
    		@RequestParam("user") String user
    	    ) {      
        try {
        	QuizResult quiz_result = new QuizResult();
        	
        	quiz_result.setScore(Float.parseFloat(score));
        	
        	Quiz q = quizService.findById(quiz);
        	quiz_result.setQuiz(q);
        	
        	User u = authService.findById(user);
        	quiz_result.setUser(u);
        	
        	quizResultService.createQuizResult(quiz_result);
            ApiResponse<QuizResult> response = new ApiResponse<>("ok", "Successfully", quiz_result);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}

    }

    @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<QuizResult>> updateQuizResult(
    		@PathVariable String id, 
    		@RequestParam(value="score",required=false) String score,
    		@RequestParam(value="quiz",required=false) String quiz
    		){
        
        try {
        	QuizResult quiz_result = quizResultService.findById(id);
        	if (quiz_result == null) {
        		ApiResponse<QuizResult> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	if(score != null) {
        		quiz_result.setScore(Float.parseFloat(score));
        	}
        	if(quiz!=null) {
        		Quiz q = quizService.findById(quiz);
        		quiz_result.setQuiz(q);
        	}
        	if(quiz!=null) {
        		Quiz q = quizService.findById(quiz);
        		quiz_result.setQuiz(q);
        	}
        	quizResultService.updateQuizResult(quiz_result);

            ApiResponse<QuizResult> response = new ApiResponse<>("ok", "Successfully", quiz_result);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<QuizResult>> deleteQuizResult(@PathVariable String id) {
        try {
        	QuizResult quiz_result = quizResultService.findById(id);
        	if (quiz_result == null) {
        		ApiResponse<QuizResult> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	quizResultService.deleteById(id);
            ApiResponse<QuizResult> response = new ApiResponse<>("ok", "Successfully", quiz_result);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
}
