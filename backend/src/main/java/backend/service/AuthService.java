package backend.service;

import java.util.List;
import java.util.Map;

import backend.entity.User;

public interface AuthService {
	public User findById(String id);
	public User createUser(User user) ;
	public Map<String, Object> login(String email, String password) ;
	public void logout(String token) ;
	List<User> getAllUsers();
	User updateUser(String id, User user);
}
