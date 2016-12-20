package _02_login.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import _00_init.HibernateUtil;

public class LoginIPDAO {
	
	private SessionFactory factory = HibernateUtil.getSessionFactory();
	
	public LoginIPDAO(){
		
	}
	
	public int insertIP(LoginIPBean lib){
		int count = 0;
		Session session = factory.getCurrentSession();
		session.save(lib);
		count = 1;
		return count;
	}
	
	public LoginIPBean getIPBean(String ip){
		LoginIPBean lib= null;
		Session session = factory.getCurrentSession();
		lib = (LoginIPBean) session.get(LoginIPBean.class,ip);
		return lib;
	}
	
	public int delIP (LoginIPBean lib){
		int count = 0;
		Session session = factory.getCurrentSession();
		session.delete(lib);
		count = 1;
		return count;
	}
	
	
}
