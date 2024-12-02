package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.entity.Answer;
import backend.entity.Question;
import backend.entity.Quiz;
import backend.service.QuestionService;
import backend.service.QuizService;
import backend.util.ApiResponse;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private QuizService quizService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Question>>> getAllQuestion() {
        try {
        	List<Question> quiz = questionService.readListQuestion();
            if (quiz.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            ApiResponse<List<Question>> response = new ApiResponse<>("ok", "Successfully", quiz);
            return ResponseEntity.ok(response);
        } catch(Exception e){
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        			.body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }
    
    @GetMapping("/get-answer/{id}")
    public ResponseEntity<ApiResponse<List<Answer>>> getAnswer(@PathVariable String id) {
        try {
        	List<Answer> answer = questionService.getAnswers(id);
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
    public ResponseEntity<ApiResponse<Question>> getQuestionById(@PathVariable String id) {
    	try {

    		Question q = questionService.findById(id);
            if (q == null) {
                return ResponseEntity.notFound().build();
            }
            ApiResponse<Question> response = new ApiResponse<>("ok", "Successfully", q);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Question>> createQuestion(
    		@RequestParam("question_text") String question_text,
    		@RequestParam("quiz") String quiz
    	    ) {      
        try {
        	Question question = new Question();
        	question.setQuestionText(question_text);
        	Quiz q = quizService.findById(quiz);
        	question.setQuiz(q);
        	questionService.createQuestion(question);;
            ApiResponse<Question> response = new ApiResponse<>("ok", "Successfully", question);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}

    }

    @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Question>> updateQuiz(
    		@PathVariable String id, 
    		@RequestParam(value="question_text",required=false) String question_text,
    		@RequestParam(value="quiz",required=false) String quiz
    		){
        
        try {
        	Question question = questionService.findById(id);
        	if (question == null) {
        		ApiResponse<Question> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	if(question_text != null) {
        		question.setQuestionText(question_text);
        	}
        	if(quiz!=null) {
        		Quiz q = quizService.findById(quiz);
        		question.setQuiz(q);
        	}
        	questionService.updateQuestion(question);

            ApiResponse<Question> response = new ApiResponse<>("ok", "Successfully", question);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Question>> deleteCategory(@PathVariable String id) {
        try {
        	Question question = questionService.findById(id);
        	if (question == null) {
        		ApiResponse<Question> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	questionService.deleteById(id);
            ApiResponse<Question> response = new ApiResponse<>("ok", "Successfully", question);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
}
