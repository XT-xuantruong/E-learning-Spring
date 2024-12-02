package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.entity.Category;
import backend.service.CategoryService;
import backend.util.ApiResponse;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategorys() {
        try {
        	List<Category> category = categoryService.readListCategory();
            if (category.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            ApiResponse<List<Category>> response = new ApiResponse<>("ok", "Successfully", category);
            return ResponseEntity.ok(response);
        } catch(Exception e){
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        			.body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable String id) {
    	try {

    		Category category = categoryService.findById(id);
    		System.out.print(category);
            if (category == null) {
                return ResponseEntity.notFound().build();
            }
            ApiResponse<Category> response = new ApiResponse<>("ok", "Successfully", category);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
    
    @GetMapping("/get-slug/{slug}")
    public ResponseEntity<ApiResponse<Category>> getCategoryBySlug(@PathVariable String slug) {
    	try {

    		Category category = categoryService.findBySlug(slug);
    		System.out.print(category);
            if (category == null) {
                return ResponseEntity.notFound().build();
            }
            ApiResponse<Category> response = new ApiResponse<>("ok", "Successfully", category);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Category>> createCategory(
    		@RequestParam("title") String title,
    		@RequestParam("slug") String slug
    	    ) {      
        try {
        	Category category = new Category();
        	category.setTitle(title);
        	category.setSlug(slug);
        	categoryService.createCategory(category);
    		System.out.print(category);
            ApiResponse<Category> response = new ApiResponse<>("ok", "Successfully", category);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}

    }

    @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Category>> updateCategory(
    		@PathVariable String id, 
    		@RequestParam("title") String title,
    		@RequestParam("slug") String slug
    		){
        
        try {
        	Category category = categoryService.findById(id);
        	if (category == null) {
        		ApiResponse<Category> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	category.setTitle(title);
        	category.setSlug(slug);
            categoryService.updateCategory(category);
    		System.out.print(category);
            ApiResponse<Category> response = new ApiResponse<>("ok", "Successfully",category);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> deleteCategory(@PathVariable String id) {
        try {
        	Category category = categoryService.findById(id);
        	if (category == null) {
        		ApiResponse<Category> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	categoryService.deleteById(id);
    		System.out.print(category);
            ApiResponse<Category> response = new ApiResponse<>("ok", "Successfully", category);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
}
