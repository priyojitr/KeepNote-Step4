package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Note;

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
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

	private final SessionFactory sessionFactory;

	@Autowired
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new note
	 */

	public boolean createNote(Note note) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(note);
		session.flush();
		return Boolean.TRUE;

	}

	/*
	 * Remove an existing note
	 */

	public boolean deleteNote(int noteId) {
		boolean flag = Boolean.TRUE;
		try {
			Note note = this.getNoteById(noteId);
			Session session = this.sessionFactory.getCurrentSession();
			session.clear();
			session.delete(note);
			session.flush();
		} catch (NoteNotFoundException e) {
			flag = Boolean.FALSE;
		}
		return flag;
	}

	/*
	 * Retrieve details of all notes by userId
	 */

	public List<Note> getAllNotesByUserId(String userId) {
		final String hql = "FROM Note note where createdBy =  :userId";
		return this.sessionFactory.getCurrentSession().createQuery(hql).getResultList();

	}

	/*
	 * Retrieve details of a specific note
	 */

	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Session session = this.sessionFactory.getCurrentSession();
		Note note = session.get(Note.class, noteId);
		session.flush();
		if (null == note) {
			throw new NoteNotFoundException("note not found exception");
		}
		return note;

	}

	/*
	 * Update an existing note
	 */

	public boolean UpdateNote(Note note) {
		return false;

	}

}
