package backend.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.dto.AnswerDTO;
import backend.dto.CourseDTO;
import backend.dto.LectureDTO;
import backend.dto.QuestionDTO;
import backend.dto.QuizDTO;
import backend.dto.QuizDetailsDTO;
import backend.entity.Course;

@Repository
public class CourseDAOImpl implements CourseDAO{
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@Transactional	
	public List<Course> readListCourses() {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<Course> theQuery = currentSession.createQuery("FROM Course", Course.class);
        return theQuery.getResultList();
	}

	@Override
	@Transactional
	public Course findById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Course.class, id);
	}

	@Override
	@Transactional
	public void createCourse(Course theCourse) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(theCourse);
		
	}

	@Override
	@Transactional
	public void updateCourse(Course theCourse) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(theCourse);
		
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        Course theCourse = currentSession.get(Course.class, id);
        currentSession.remove(theCourse);
		
	}

	@Override
	@Transactional
	public CourseDTO findByIdWithLectureAndQuiz(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
	    
	    // Get course first
	    Course course = currentSession.get(Course.class, id);
	    if (course == null) {
	        return null;
	    }
	    
	    // Create CourseDTO and set basic info
	    CourseDTO courseDTO = new CourseDTO();
	    courseDTO.setId(course.getId());
	    courseDTO.setTitle(course.getTitle());
	    
	    // Get lectures
	    List<Object[]> lectures = currentSession.createQuery(
	        "SELECT l.id, l.title, l.content " +
	        "FROM Lecture l " +
	        "WHERE l.course.id = :courseId",
	        Object[].class
	    )
	    .setParameter("courseId", id)
	    .list();
	    
	    List<LectureDTO> lectureDTOs = new ArrayList<>();
	    for (Object[] row : lectures) {
	        LectureDTO lecture = new LectureDTO();
	        lecture.setId((String) row[0]);
	        lecture.setTitle((String) row[1]);
	        lecture.setContent((String) row[2]);
	        lectureDTOs.add(lecture);
	    }
	    courseDTO.setLectures(lectureDTOs);
	    
	    // Get quizzes
	    List<Object[]> quizzes = currentSession.createQuery(
	        "SELECT q.id, q.title " +
	        "FROM Quiz q " +
	        "WHERE q.course.id = :courseId",
	        Object[].class
	    )
	    .setParameter("courseId", id)
	    .list();
	    
	    List<QuizDTO> quizDTOs = new ArrayList<>();
	    for (Object[] row : quizzes) {
	        QuizDTO quiz = new QuizDTO();
	        quiz.setId((String) row[0]);
	        quiz.setTitle((String) row[1]);
	        quizDTOs.add(quiz);
	    }
	    courseDTO.setQuizs(quizDTOs);
	    
	    return courseDTO;
	}

	
}