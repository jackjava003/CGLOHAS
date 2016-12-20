package _10_postLike.model;

import java.sql.SQLException;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import _00_init.HibernateUtil;

public class PostLikeDAO {
	private SessionFactory factory = HibernateUtil.getSessionFactory();
	private int posterID;
	private int holderID;

	public PostLikeDAO() {

	}

	@SuppressWarnings("unchecked")
	public PostLikeBean checkType(int holderID, int userID) {
		Session session = factory.getCurrentSession();
		PostLikeBean pb = null;
		Collection<PostLikeBean> coll = session
				.createQuery("from PostLikeBean where holderID = :hID and posterID = :pID")
				.setParameter("hID", holderID).setParameter("pID", userID).list();
		for (PostLikeBean plb : coll) {
			pb = plb;
		}
		return pb;
	}

	public int deleteLike(PostLikeBean plb) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			session.delete(plb);
			tx.commit();
			count = 1;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}

	public int insertLike(PostLikeBean plb) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			session.save(plb);
			tx.commit();
			count = 1;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}

	public void setholderID(int holderID) {
		this.holderID = holderID;
	}

	public void setPosterID(int posterID) {
		this.posterID = posterID;
	}

	public int getCheckLike() throws SQLException {
		Session session = factory.getCurrentSession();
		int count = -1;
		count = ((Number) session.createCriteria(PostLikeBean.class).add(Restrictions.eq("holderID", holderID))
				.add(Restrictions.eq("posterID", posterID)).setProjection(Projections.rowCount()).uniqueResult())
						.intValue();
		return count;
	}

}
