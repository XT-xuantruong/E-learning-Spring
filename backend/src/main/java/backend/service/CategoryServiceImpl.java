package backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dao.CategoryDAO;
import backend.entity.Category;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
	@Autowired
    private CategoryDAO categoryDAO;

	@Override
	public List<Category> readListCategory() {
		return categoryDAO.readListCategory();
	}

	@Override
	public Category findById(String id) {
		return categoryDAO.findById(id);
	}
	
	@Override
	public Category findBySlug(String slug) {
		return categoryDAO.findBySlug(slug);
	}

	@Override
	public void createCategory(Category theCategory) {
		categoryDAO.createCategory(theCategory);
		
	}

	@Override
	public void updateCategory(Category theCategory) {
		categoryDAO.updateCategory(theCategory);
		
	}

	@Override
	public void deleteById(String id) {
		categoryDAO.deleteById(id);
	}

}
