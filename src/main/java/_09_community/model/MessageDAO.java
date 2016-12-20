package _09_community.model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import _00_init.HibernateUtil;

public class MessageDAO {
	private SessionFactory factory = HibernateUtil.getSessionFactory();
	private int userID;
	private int recordsPerPage = 5;
	private int totalPages = -1;
	private int pageNo = 0;
	private int holderID;

	public int deletReply(int messageID) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			MessageBean mb = (MessageBean) session.get(MessageBean.class, messageID);
			mb.setReplyMessage(null);
			mb.setReplyTime(null);
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

	public MessageBean getMessage(int messageID) {
		Session session = factory.getCurrentSession();
		MessageBean mb = (MessageBean) session.load(MessageBean.class, messageID);
		return mb;
	}

	public int deletMessage(MessageBean mb) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			session.delete(mb);
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

	public int updateReplyMessage(int messageID, String replyMessage) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			MessageBean mb = (MessageBean) session.get(MessageBean.class, messageID);
			mb.setReplyMessage(replyMessage);
			mb.setReplyTime(new Timestamp(System.currentTimeMillis()));
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

	public int insertMessage(MessageBean mb) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			session.save(mb);
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

	public void setHolderID(int holderID) {
		this.holderID = holderID;
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
	public Collection<MessageBean> getPageMessage() throws SQLException {

		Collection<MessageBean> coll = null;
		Session session = factory.getCurrentSession();
		int startRecordNo = (pageNo - 1) * recordsPerPage;
		coll = session.createQuery("from MessageBean where holderID= :hID order by createTime desc")
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
		count = ((Number) session.createCriteria(MessageBean.class).add(Restrictions.eq("holderID", holderID))
				.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return count;
	}
}
