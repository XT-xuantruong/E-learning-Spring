package backend.dao;

import java.util.List;

import backend.entity.User;

public interface AuthDao {
    User create(User user);
    User findById(String id);
    User findByEmail(String email);
	public void deleteById(String id);
    List<User> findAll();
    User update(User user);
}