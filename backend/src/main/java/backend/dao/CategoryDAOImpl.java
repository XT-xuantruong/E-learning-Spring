package backend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import backend.entity.Category;

@Repository
public class CategoryDAOImpl implements CategoryDAO{
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@Transactional	
	public List<Category> readListCategory() {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<Category> theQuery = currentSession.createQuery("FROM Category", Category.class);
        return theQuery.getResultList();
	}

	@Override
	@Transactional
	public Category findById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Category.class, id);
	}
	
	@Override
	@Transactional
	public Category findBySlug(String slug) {
		System.out.print(slug);
		Session currentSession = sessionFactory.getCurrentSession();
		try {
	                
			String hql = "FROM Category c WHERE c.Slug = :slugParam";
	        Query<Category> query = currentSession.createQuery(hql, Category.class);
	        query.setParameter("slugParam", slug);
	        
	        return query.uniqueResult();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null; // or throw your custom exception
	    }
	}
	
	@Override
	@Transactional
	public void createCategory(Category theCategory) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(theCategory);
		
	}

	@Override
	@Transactional
	public void updateCategory(Category theCategory) {
		Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(theCategory);
		
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Category theCategory = currentSession.get(Category.class, id);
        currentSession.remove(theCategory);
		
	}
	
}
