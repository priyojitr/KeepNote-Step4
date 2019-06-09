package com.stackroute.keepnote.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

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
public class UserDaoImpl implements UserDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

	private final SessionFactory sessionFactory;

	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new user
	 */

	public boolean registerUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(user);
		session.flush();
		return true;
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(User user) {
		boolean flag = Boolean.FALSE;
		if (null != this.getUserById(user.getUserId())) {
			// update user
			Session session = this.sessionFactory.getCurrentSession();
			session.clear();
			session.update(user);
			session.flush();
			flag = Boolean.TRUE;
		}
		return flag;

	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(String userId) {
		Session session = this.sessionFactory.getCurrentSession();
		User user = session.get(User.class, userId);
		session.flush();
		return user;
	}

	/*
	 * validate an user
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		boolean flag = Boolean.TRUE;
		User user = this.getUserById(userId);
		if (null == user) {
			throw new UserNotFoundException("user not found exception");
		} else {
			if (!password.equals(user.getUserPassword())) {
				flag = Boolean.FALSE;
			}
		}
		return flag;

	}

	/*
	 * Remove an existing user
	 */
	public boolean deleteUser(String userId) {
		boolean flag = Boolean.TRUE;
		User user = this.getUserById(userId);
		if (null != user) {
			Session session = this.sessionFactory.getCurrentSession();
			session.delete(user);
			session.flush();
		} else {
			flag = Boolean.FALSE;
		}
		return flag;

	}

}
