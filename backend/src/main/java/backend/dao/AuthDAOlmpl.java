package backend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.entity.Category;
import backend.entity.User;

@Repository
public class AuthDAOlmpl implements AuthDao {
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public User create(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(user);
        return user;
    }

    @Override
    @Transactional
    public User findById(String id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(User.class, id);
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User where email = :email", User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    @Transactional
    public List<User> findAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> query = currentSession.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public User update(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(user);
        return user;
       
    }
    
    @Override
	@Transactional
	public void deleteById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		User theUser = currentSession.get(User.class, id);
        currentSession.remove(theUser);
		
	}
	
}