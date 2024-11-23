package backend.dao;
import org.springframework.stereotype.Repository;
import backend.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserDAO {
    
    @PersistenceContext
    private EntityManager entityManager;

    public User findByEmail(String email) {
        try {
            return entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}