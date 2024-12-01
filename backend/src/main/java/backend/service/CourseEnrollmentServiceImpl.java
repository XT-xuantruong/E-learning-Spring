package backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dao.CourseEnrollmentDAO;
import backend.entity.CourseEnrollment;

@Service
@Transactional
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService{
	@Autowired
    private CourseEnrollmentDAO courseEnrollmentDAO;

	@Override
	public List<CourseEnrollment> readListCourseEnrollment() {
		return courseEnrollmentDAO.readListCourseEnrollment();
	}

	@Override
	public CourseEnrollment findById(String id) {
		return courseEnrollmentDAO.findById(id);
	}

	@Override
	public void createCourseEnrollment(CourseEnrollment theCourseEnrollment) {
		courseEnrollmentDAO.createCourseEnrollment(theCourseEnrollment);
		
	}

	@Override
	public void updateCourseEnrollment(CourseEnrollment theCourseEnrollment) {
		courseEnrollmentDAO.updateCourseEnrollment(theCourseEnrollment);
		
	}

	@Override
	public void deleteById(String id) {
		courseEnrollmentDAO.deleteById(id);
	}

	@Override
	public List<CourseEnrollment> findByUserAndCourse(String userId) {

		return courseEnrollmentDAO.findByUserAndCourse(userId);
	}

	

}
