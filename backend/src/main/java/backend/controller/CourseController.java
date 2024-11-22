package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.entity.Course;
import backend.service.CourseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.readListCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable UUID id) {
        return courseService.findById(id);
    }

    @PostMapping
    public Course createCourse(@RequestBody Course theCourse) {
        courseService.createCourse(theCourse);
        return theCourse;
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable UUID id, @RequestBody Course theCourse) {
        theCourse.setId(id);
        courseService.updateCourse(theCourse);
        return theCourse;
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable UUID id) {
        courseService.deleteById(id);
    }
}
