package backend.service;

import java.util.List;

import backend.entity.CourseEnrollment;

public interface CourseEnrollmentService {
	public List<CourseEnrollment> readListCourseEnrollment();
    public CourseEnrollment findById(String id);
    public void createCourseEnrollment(CourseEnrollment theCourseEnrollment);
    public void updateCourseEnrollment(CourseEnrollment theCourseEnrollment);
    public void deleteById(String id);
}
