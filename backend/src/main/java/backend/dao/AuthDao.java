package backend.dao;

import java.util.List;

import backend.entity.User;

public interface AuthDao {
    User create(User user);
    User findById(String id);
    User findByEmail(String email);
    List<User> findAll();
    User update(User user);
}