package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */

@Repository
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

	private final SessionFactory sessionFactory;

	@Autowired
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category) {
		Session session = this.sessionFactory.getCurrentSession();
		session.clear();
		session.save(category);
		session.flush();
		return Boolean.TRUE;

	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId) {
		boolean flag = Boolean.TRUE;
		try {
			Category category = this.getCategoryById(categoryId);
			Session session = this.sessionFactory.getCurrentSession();
			session.clear();
			session.delete(category);
			session.flush();
		} catch (CategoryNotFoundException e) {
			flag = Boolean.FALSE;
		}
		return flag;

	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) {
		boolean flag = Boolean.TRUE;
		try {
			if (null != this.getCategoryById(category.getCategoryId())) {
				Session session = this.sessionFactory.getCurrentSession();
				session.clear();
				session.update(category);
				session.flush();
			}
		} catch (CategoryNotFoundException e) {
			flag = Boolean.FALSE;
		}
		return flag;

	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		Session session = this.sessionFactory.getCurrentSession();
		Category category = session.get(Category.class, categoryId);
		if (null == category) {
			throw new CategoryNotFoundException("category not found exception");
		}
		return category;

	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		final String hql = "From Category category where categoryCreatedBy = :userId";
		return this.sessionFactory.getCurrentSession().createQuery(hql).getResultList();

	}

}
