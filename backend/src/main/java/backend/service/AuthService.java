package backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.entity.User;
import backend.dao.UserDAO;
import backend.util.JwtUtil;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, Object> login(User user) {
        // Kiểm tra user trong database
        User existingUser = userDAO.findByEmail(user.getEmail());

        // Kiểm tra user tồn tại và mật khẩu đúng
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Tạo token
        String accessToken = jwtUtil.generateAccessToken(existingUser);
        String refreshToken = jwtUtil.generateRefreshToken(existingUser);

        // Tạo response map
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("email", existingUser.getEmail());
        response.put("type", "Bearer");

        return response;
    }
}