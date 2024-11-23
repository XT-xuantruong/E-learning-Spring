package backend.dao;

import java.util.List;

import backend.entity.Course;

public interface CourseDAO {
	public List<Course> readListCourses();
    public Course findById(String id);
    public void createCourse(Course theCourse);
    public void updateCourse(Course theCourse);
    public void deleteById(String id);
}