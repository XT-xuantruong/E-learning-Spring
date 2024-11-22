package backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dao.CourseDAO;
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
	public Course findById(UUID id) {
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
	public void deleteById(UUID id) {
		courseDAO.deleteById(id);
	}

}
