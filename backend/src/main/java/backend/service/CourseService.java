package backend.service;

import java.util.List;
import java.util.UUID;

import backend.entity.Course;

public interface CourseService {
	public List<Course> readListCourses();
    public Course findById(UUID id);
    public void createCourse(Course theCourse);
    public void updateCourse(Course theCourse);
    public void deleteById(UUID id);
}
