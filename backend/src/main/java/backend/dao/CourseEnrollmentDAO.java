package backend.dao;

import java.util.List;

import backend.entity.CourseEnrollment;

public interface CourseEnrollmentDAO {
	public List<CourseEnrollment> readListCourseEnrollment();
    public CourseEnrollment findById(String id);
    public void createCourseEnrollment(CourseEnrollment theCourseEnrollment);
    public void updateCourseEnrollment(CourseEnrollment theCourseEnrollment);
    public void deleteById(String id);
    public List<CourseEnrollment> findByUserAndCourse(String userId);
}