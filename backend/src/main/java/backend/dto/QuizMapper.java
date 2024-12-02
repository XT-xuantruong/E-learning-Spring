package backend.dto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import backend.entity.*;

@Component
public class QuizMapper {
    
    public QuizDetailsDTO toDTO(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        QuizDetailsDTO quizDTO = new QuizDetailsDTO();
        quizDTO.setId(quiz.getId());
        quizDTO.setTitle(quiz.getTitle());
        
        if (quiz.getQuestions() != null) {
            List<QuestionDTO> questionDTOs = quiz.getQuestions().stream()
                .map(this::toQuestionDTO)
                .collect(Collectors.toList());
            quizDTO.setQuestions(questionDTOs);
        }
        
        return quizDTO;
    }
    
    private QuestionDTO toQuestionDTO(Question question) {
        if (question == null) {
            return null;
        }

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setQuestionText(question.getQuestionText());
        
        if (question.getAnswers() != null) {
            List<AnswerDTO> answerDTOs = question.getAnswers().stream()
                .map(this::toAnswerDTO)
                .collect(Collectors.toList());
            questionDTO.setAnswers(answerDTOs);
        }
        
        return questionDTO;
    }
    
    private AnswerDTO toAnswerDTO(Answer answer) {
        if (answer == null) {
            return null;
        }

        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(answer.getId());
        answerDTO.setAnswerText(answer.getAnswerText());
        answerDTO.setCorrect(answer.isCorrect());
        
        return answerDTO;
    }
}