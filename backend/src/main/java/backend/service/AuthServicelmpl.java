package backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.entity.User;
import backend.dao.AuthDao;
import org.springframework.transaction.annotation.Transactional;
import backend.util.JwtUtil;

@Service
public class AuthServicelmpl implements AuthService  {

    @Autowired
    private AuthDao userDao;
    
    @Autowired
    private JwtUtil jwtUtil;
    

    public User createUser(User user) {
         return userDao.create(user);
    }

    public User findById(String id)   {
        return userDao.findById(id);
    }
    
    @Override
    @Transactional
    public Map<String, Object> login(String email, String password)  {
        User user = userDao.findByEmail(email);

        if (user != null) {
            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("access_token", accessToken);
            response.put("refresh_token", refreshToken);

            return response;
        }

        return null;
    }
    
    @Override
    public void logout(String token)  {
        jwtUtil.blacklistToken(token);
    }
    
    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    
    @Override
    @Transactional
    public User updateUser(String id, User updatedUser) {
        User existingUser = userDao.findById(id);
        if (existingUser != null) {
                existingUser.setFirstName(updatedUser.getFirstName());

                existingUser.setLastName(updatedUser.getLastName());
        
                existingUser.setAvatar(updatedUser.getAvatar());
          
                existingUser.setEmail(updatedUser.getEmail());
          
                existingUser.setRole(updatedUser.getRole());
            
            return userDao.update(existingUser);
        }
        return null;
    }
}