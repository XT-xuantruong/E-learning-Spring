package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backend.entity.User;
import backend.service.AuthService;
import backend.util.ApiResponse;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.entity.Category;
import backend.entity.Role; 

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/user") 
public class AuthController {

	 @Autowired
	    private AuthService authService;

	    private String hashPassword(String password) {
	        return DigestUtils.sha256Hex(password);
	    }

	    @PostMapping(value = "/register",
	            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<ApiResponse<User>> createUser(
	            @RequestParam("firstName") String firstName,
	            @RequestParam("lastName") String lastName,
	            @RequestParam(value = "avatar", required = false) String avatar,
	            @RequestParam("role") String roleStr, 
	            @RequestParam("email") String email,
	            @RequestParam("password") String password) {
	        try {
	        	
	        	 Role role;
	             try {
	                 role = Role.valueOf(roleStr.toUpperCase());  
	             } catch (IllegalArgumentException e) {
	                 return ResponseEntity.badRequest()
	                     .body(new ApiResponse<>("error", "Invalid role value: " + roleStr, null));
	             }
	            User user = new User();
	            user.setFirstName(firstName);
	            user.setLastName(lastName);
	            user.setAvatar(avatar);
	            user.setRole(role);
	            user.setEmail(email);
	            user.setPassword(hashPassword(password));
	            System.out.print(user);
	            
	            User createdUser = authService.createUser(user);
	            return ResponseEntity.ok(new ApiResponse<>("ok", "Successfully created user", createdUser));
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(new ApiResponse<>("error", e.getMessage(), null));
	        }
	    }

	    @PostMapping("/login")
	    public ResponseEntity<ApiResponse<Object>> login(
	            @RequestParam("email") String email,
	            @RequestParam("password") String password) {
	        try {
	            Map<String, Object> loginResult = authService.login(email, hashPassword(password));

	            if (loginResult == null) {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                        .body(new ApiResponse<>("error", "Invalid email or password", null));
	            }

	            return ResponseEntity.ok(new ApiResponse<>("ok", "Login successful", loginResult));
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(new ApiResponse<>("error", e.getMessage(), null));
	        }
	    }
	    
	    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable String id) {
        try {
            User user = authService.findById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("error", "User not found", null));
            }
            return ResponseEntity.ok(new ApiResponse<>("ok", "Successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String authHeader) {
    	System.out.print(authHeader);
    	try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                authService.logout(token);
                return ResponseEntity.ok(new ApiResponse<>("ok", "Logout successful", null));
            }
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("error", "Invalid token format", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        try {
            List<User> users = authService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse<>("ok", "Successfully retrieved all users", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable String id) {
        try {
        	User userDelete = authService.findById(id);
        	if (userDelete == null) {
        		ApiResponse<User> response = new ApiResponse<>("error", "Not exists",null);
                return ResponseEntity.ok(response);
        	}
        	authService.deleteById(id);
            ApiResponse<User> response = new ApiResponse<>("ok", "Successfully", userDelete);
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(new ApiResponse<>("error", e.getMessage(), null));
    	}
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable String id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String avatar,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String roleStr) {
        try {
            User updateUser = new User();
            updateUser.setFirstName(firstName);
            updateUser.setLastName(lastName);
            updateUser.setAvatar(avatar);
            updateUser.setEmail(email);
            
            if (roleStr != null) {
                try {
                    Role role = Role.valueOf(roleStr.toUpperCase());
                    updateUser.setRole(role);
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest()
                        .body(new ApiResponse<>("error", "Invalid role value: " + roleStr, null));
                }
            }
            
            User updatedUser = authService.updateUser(id, updateUser);
          
            
            return ResponseEntity.ok(new ApiResponse<>("ok", "Successfully updated user", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }
}