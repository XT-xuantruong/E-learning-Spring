package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.entity.Answer;
import backend.entity.Question;
import backend.service.AnswerService;
import backend.service.QuestionService;
import backend.util.ApiResponse;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;
    
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Answer>>> getAllAnswer() {
        try {
        	List<Answer> answer = answerService.readListAnswer();
            if (answer.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            ApiResponse<List<Answer>> response = new ApiResponse<>("ok", "Successfully", answer);
            return ResponseEntity.ok(response);
        } catch(Exception e){
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        			.body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Answer>> getAnswerById(@PathVariable String id) {
    	try {

    		Answer answer = answerService.findById(id);

            if (answer == null) {
                return ResponseEntity.notFound().build();
            }
            ApiResponse<Answer> response = new ApiResponse<>("ok", "Successfully", answer);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Answer>> createAnswer(
    		@RequestParam("question") String question,
    		@RequestParam("answer_text") String answer_text,
    		@RequestParam("is_correct") String is_correct
    	    ) {      
        try {
        	Answer answer = new Answer();
        	
        	Question q = questionService.findById(question);
        	answer.setQuestion(q);
        	
        	answer.setAnswerText(answer_text);
        	
        	answer.setCorrect(Boolean.parseBoolean(is_correct));
        	
        	answerService.createAnswer(answer);
        	
            ApiResponse<Answer> response = new ApiResponse<>("ok", "Successfully", answer);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}

    }

    @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Answer>> updateAnswer(
    		@PathVariable String id, 
    		@RequestParam(value="question",required=false) String question,
    		@RequestParam(value="answer_text",required=false) String answer_text,
    		@RequestParam(value="is_correct",required=false) String is_correct
    		){
        
        try {
        	Answer answer = answerService.findById(id);
        	
        	if (answer == null) {
        		ApiResponse<Answer> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	
        	if(question != null) {
        		Question q = questionService.findById(question);
            	answer.setQuestion(q);
        	}
        	
        	if(answer_text!=null) {
        		answer.setAnswerText(answer_text);
        	}
        	
        	if(is_correct!=null) {
        		answer.setCorrect(Boolean.parseBoolean(is_correct));
        	}
        	answerService.updateAnswer(answer);

            ApiResponse<Answer> response = new ApiResponse<>("ok", "Successfully", answer);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Answer>> deleteAnswer(@PathVariable String id) {
        try {
        	Answer answer = answerService.findById(id);
        	if (answer == null) {
        		ApiResponse<Answer> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	answerService.deleteById(id);

            ApiResponse<Answer> response = new ApiResponse<>("ok", "Successfully", answer);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
}
