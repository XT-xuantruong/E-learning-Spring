package backend.dao;

import java.util.List;

import backend.entity.Category;

public interface CategoryDAO {
	public List<Category> readListCategory();
    public Category findById(String id);
    public void createCategory(Category theCategory);
    public void updateCategory(Category theCategory);
    public void deleteById(String id);
}
