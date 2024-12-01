package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import backend.entity.Course;
import backend.entity.Lecture;
import backend.service.CourseService;
import backend.service.LectureService;
import backend.util.ApiResponse;
import jakarta.servlet.ServletContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/lecture")
public class LectureController {

    @Autowired
    private LectureService lectureService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
	ServletContext context;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Lecture>>> getAllLecture() {
        try {
        	List<Lecture> category = lectureService.readListLecture();
            if (category.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            ApiResponse<List<Lecture>> response = new ApiResponse<>("ok", "Successfully", category);
            return ResponseEntity.ok(response);
        } catch(Exception e){
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        			.body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Lecture>> getLectureById(@PathVariable String id) {
    	try {

    		Lecture lecture = lectureService.findById(id);
    		System.out.print(lecture);
            if (lecture == null) {
                return ResponseEntity.notFound().build();
            }
            ApiResponse<Lecture> response = new ApiResponse<>("ok", "Successfully", lecture);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Lecture>> createLecture(
        @RequestParam("title") String title,
        @RequestParam("content") String content,
        @RequestParam("course_id") String course_id,
        @RequestParam("file") MultipartFile file
    ) {
        try {
        	System.out.println("long "+ file.getBytes());
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("error", "File PDF không hợp lệ", null));
            }

            if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("error", "Only PDF files are allowed", null));
            }

            String projectPath = context.getRealPath("resources/");

            Path uploadPath = Paths.get(projectPath, "pdf", "lectures").toAbsolutePath();
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            String fileUrl = "/resources/pdf/lectures/" + fileName;

            Lecture lecture = new Lecture();
            lecture.setTitle(title);
            lecture.setContent(fileUrl);
            Course course = courseService.findById(course_id);
        	System.out.println("long "+ course);

            lecture.setCourse(course);
            
            lectureService.createLecture(lecture);

            ApiResponse<Lecture> response = new ApiResponse<>("ok", "Upload lecture thành công", lecture);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("error", "Lỗi upload: " + e.getMessage(), null));
        }
    }

    
    @PutMapping(value ="/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Lecture>> updateLecture(
        @PathVariable String id,
        @RequestParam(value="title", required=false) String title,
        @RequestParam(value="file", required=false) MultipartFile file,
        @RequestParam(value="course", required=false) String course
    ) {
        try {
            Lecture lecture = lectureService.findById(id);
            if (lecture == null) {
                ApiResponse<Lecture> response = new ApiResponse<>("error", "Lecture not found", null);
                return ResponseEntity.ok(response);
            }

            if (file != null && !file.isEmpty()) {
                if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>("error", "Only PDF files are allowed", null));
                }

                String projectPath = context.getRealPath("resources/");
                Path uploadPath = Paths.get(projectPath, "pdf", "lectures").toAbsolutePath();

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);

                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                String fileUrl = "/resources/pdf/lectures/" + fileName;
                lecture.setContent(fileUrl);
            }

            if (title != null && !title.trim().isEmpty()) {
                lecture.setTitle(title);
            }

            if (course != null) {
                Course updatedCourse = courseService.findById(course);
                lecture.setCourse(updatedCourse);
            }

            lectureService.updateLecture(lecture);

            ApiResponse<Lecture> response = new ApiResponse<>("ok", "Lecture updated successfully", lecture);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("error", "Error updating lecture: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Lecture>> deleteLecture(@PathVariable String id) {
        try {
        	Lecture lecture = lectureService.findById(id);
        	if (lecture == null) {
        		ApiResponse<Lecture> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	lectureService.deleteById(id);
    		System.out.print(lecture);
            ApiResponse<Lecture> response = new ApiResponse<>("ok", "Successfully", lecture);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
}
