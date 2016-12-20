package _01_register.model;

import java.sql.Blob;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import _00_init.HibernateUtil;

public class Hibernate_register_DAO {
	private SessionFactory factory = HibernateUtil.getSessionFactory();

	public Hibernate_register_DAO() {

	}
	
	public Blob getUserImage(int userID){
		Session session = factory.getCurrentSession();
		register_bean rb = (register_bean) session.load(register_bean.class, userID);
		return rb.getMemberImage();
	}
	
	public String getUserName(int userID){
		Session session = factory.getCurrentSession();
		register_bean rb = (register_bean) session.load(register_bean.class, userID);
		return rb.getName();
	}

	public int updateDisLike(int userID, String action) {

		int count = -1;
		Session session = factory.getCurrentSession();
		register_bean rb = (register_bean) session.load(register_bean.class, userID);
		if (action.equalsIgnoreCase("add")) {
			rb.setDislike_count(rb.getDislike_count() + 1);
		} else {
			rb.setDislike_count(rb.getDislike_count() - 1);
		}
		count = 1;
		return count;
	}

	public int updateLike(int userID, String action) {

		int count = -1;
		Session session = factory.getCurrentSession();
		register_bean rb = (register_bean) session.load(register_bean.class, userID);
		if (action.equalsIgnoreCase("add")) {
			rb.setLike_count(rb.getLike_count() + 1);
		} else {
			rb.setLike_count(rb.getLike_count() - 1);
		}
		count = 1;
		return count;
	}
}
