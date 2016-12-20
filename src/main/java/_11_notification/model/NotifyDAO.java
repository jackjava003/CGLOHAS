package _11_notification.model;

import java.sql.SQLException;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import _00_init.HibernateUtil;
import _10_postLike.model.PostLikeBean;

public class NotifyDAO {
	private SessionFactory factory = HibernateUtil.getSessionFactory();
	private int recordsPerPage = 5;
	private int totalPages = -1;
	private int pageNo = 0;
	private int receiverID;
	
	public NotifyDAO() {

	}
	
	public int insertNotify(NotifyBean nb){
		int count =-1;
		Session session = factory.getCurrentSession();
		session.save(nb);
		count = 1;
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public int delNotify(int holderID, int userID){
		int count = -1;
		Session session = factory.getCurrentSession();
		NotifyBean pb=null;
		Collection<NotifyBean> coll=  session.createQuery("from NotifyBean where senderID = :sID and receiverID = :rID").setParameter("sID", userID).setParameter("rID", holderID).list();
		for(NotifyBean plb:coll){
			pb=plb;
		}
		session.delete(pb);
		count = 1;
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public int getUpdateCheckRead(){
		Session session = factory.getCurrentSession();
		Collection<NotifyBean> coll = session.createQuery("from NotifyBean where receiverID = :rID and checkRead = :cR")
				.setParameter("rID", receiverID).setParameter("cR", 0).list();
		for(NotifyBean nb:coll){
			nb.setCheckRead(1);
		}
		return 1;
	}
	
	public void setReceiverID(int receiverID){
		this.receiverID=receiverID;
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
	public Collection<NotifyBean> getPageNotify() throws SQLException {

		Collection<NotifyBean> coll = null;
		Session session = factory.getCurrentSession();
		int startRecordNo = (pageNo - 1) * recordsPerPage;
		coll = session.createQuery("from NotifyBean where receiverID = :rID order by sendTime desc")
				.setParameter("rID", receiverID).setFirstResult(startRecordNo).setMaxResults(recordsPerPage).list();
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
		count = ((Number) session.createCriteria(NotifyBean.class).add(Restrictions.eq("receiverID", receiverID)).setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
		return count;
	}
	
	public int getRecordUnreadCounts() throws SQLException {
		Session session = factory.getCurrentSession();
		int count = -1;
		count = ((Number) session.createCriteria(NotifyBean.class).add(Restrictions.eq("receiverID", receiverID)).add(Restrictions.eq("checkRead", 0)).setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
		return count;
	}
}
