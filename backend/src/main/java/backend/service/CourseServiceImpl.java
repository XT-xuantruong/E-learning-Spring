package backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dao.CourseDAO;
import backend.dto.CourseDTO;
import backend.dto.QuizDetailsDTO;
import backend.entity.Course;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{
	@Autowired
    private CourseDAO courseDAO;

	@Override
	public List<Course> readListCourses() {
		return courseDAO.readListCourses();
	}

	@Override
	public Course findById(String id) {
		return courseDAO.findById(id);
	}

	@Override
	public void createCourse(Course theCourse) {
		courseDAO.createCourse(theCourse);
		
	}

	@Override
	public void updateCourse(Course theCourse) {
		courseDAO.updateCourse(theCourse);
		
	}

	@Override
	public void deleteById(String id) {
		courseDAO.deleteById(id);
	}

	@Override
	public CourseDTO findByIdWithLectureAndQuiz(String id) {
		return courseDAO.findByIdWithLectureAndQuiz(id);
	}



}
