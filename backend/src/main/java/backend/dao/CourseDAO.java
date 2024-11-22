package backend.dao;

import java.util.List;
import java.util.UUID;

import backend.entity.Course;

public interface CourseDAO {
	public List<Course> readListCourses();
    public Course findById(UUID id);
    public void createCourse(Course theCourse);
    public void updateCourse(Course theCourse);
    public void deleteById(UUID id);
}
