package _09_community.model;

import java.sql.SQLException;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import _00_init.HibernateUtil;

public class JoinDAO {
	private SessionFactory factory = HibernateUtil.getSessionFactory();
	private int holderID;
	private int joinerID;
	private int recordsPerPage = 5;
	private int totalPages = -1;
	private int pageNo = 0;

	public void setHolderID(int holderID) {
		this.holderID = holderID;
	}

	public void setJoinerID(int joinerID) {
		this.joinerID = joinerID;
	}

	public JoinBean getJoinerMessage() {
		JoinBean jb = null;
		Session session = factory.getCurrentSession();
		JoinBean jb2 = new JoinBean();
		jb2.setHolderID(holderID);
		jb2.setJoinerID(joinerID);
		jb = (JoinBean) session.get(JoinBean.class, jb2);
		return jb;
	}

	public int insert(JoinBean jb) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			session.saveOrUpdate(jb);
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

	public int delete(JoinBean jb) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			session.delete(jb);
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

	public int getTotalPages() throws SQLException {
		// 計算總共有幾頁
		if (totalPages == -1) {
			// 注意下一列的double型態轉換
			totalPages = (int) (Math.ceil(getRecordCounts() / (double) recordsPerPage));
		}
		return totalPages;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	@SuppressWarnings("unchecked")
	public Collection<JoinBean> getPageJoiner() throws SQLException {

		Collection<JoinBean> coll = null;
		Session session = factory.getCurrentSession();
		int startRecordNo = (pageNo - 1) * recordsPerPage;
		coll = session.createQuery("from JoinBean where holderID = :hID order by createTime desc")
				.setParameter("hID", holderID).setFirstResult(startRecordNo).setMaxResults(recordsPerPage).list();
		return coll;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getRecordCounts() throws SQLException {
		Session session = factory.getCurrentSession();
		int count = -1;
		count = ((Number) session.createCriteria(JoinBean.class).add(Restrictions.eq("holderID", holderID))
				.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return count;
	}
}
