package _09_community.model;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import _00_init.HibernateUtil;

public class HolderManagementDAO {
	private SessionFactory factory = HibernateUtil.getSessionFactory();
	private int userID;
	private int recordsPerPage = 5;
	private int totalPages = -1;
	private int pageNo = 0;
	private int imgID;

	public void setHolderID(int userID) {
		this.userID = userID;
	}

	public void setImageID(int imgID) {
		this.imgID = imgID;
	}

	public HolderBean getHolder() {
		HolderBean hb = null;
		Session session = factory.getCurrentSession();
		hb = (HolderBean) session.get(HolderBean.class, userID);
		return hb;
	}

	public int deleteImg(HolderImageBean hib) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			session.delete(hib);
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

	// 需動到2 table 資料庫fk child table 增加cascade屬性 (parent被刪 child一起刪)
	public int delete(int userID) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			HolderImageBean hib = new HolderImageBean();
			HolderBean hb = new HolderBean();
			hib.setUserID(userID);
			hb.setUserID(userID);
			// session.delete(hib);
			session.delete(hb);
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

	public HolderImageBean getSinglePic() {
		HolderImageBean hib = new HolderImageBean();
		Session session = factory.getCurrentSession();
		hib = (HolderImageBean) session.load(HolderImageBean.class, imgID);
		return hib;
	}

	@SuppressWarnings("unchecked")
	public Collection<Object[]> getFoodImageId() {
		Collection<Object[]> coll = null;
		Session session = factory.getCurrentSession();
		coll = session.createSQLQuery("SELECT image_id,imageInfo FROM holder_image WHERE userID = :id and Type = :type")
				.setInteger("id", userID).setString("type", "FOOD").list();
		return coll;
	}

	@SuppressWarnings("unchecked")
	public Collection<Object[]> getEnvImageId() {
		Collection<Object[]> coll = null;
		Session session = factory.getCurrentSession();
		coll = session.createSQLQuery("SELECT image_id,imageInfo FROM holder_image WHERE userID = :id and Type = :type")
				.setInteger("id", userID).setString("type", "ENV").list();
		return coll;
	}

	public int updateImg(int picIdInt, String title) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			HolderImageBean hib = (HolderImageBean) session.get(HolderImageBean.class, picIdInt);
			hib.setImageInfo(title);
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

	public int updateImg(int picIdInt, Blob pic, String title) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			HolderImageBean hibUpdate = (HolderImageBean) session.get(HolderImageBean.class, picIdInt);
			hibUpdate.setImage(pic);
			hibUpdate.setImageInfo(title);
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

	public int insertImage(HolderImageBean hib) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			session.save(hib);
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

	public int insert(HolderBean hb) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		int count = -1;
		try {
			session.saveOrUpdate(hb);
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

	public int getFoodImgRecordCounts() {
		Session session = factory.getCurrentSession();
		int count = -1;
		count = ((Number) session.createCriteria(HolderImageBean.class).add(Restrictions.eq("Type", "FOOD"))
				.add(Restrictions.eq("userID", userID)).setProjection(Projections.rowCount()).uniqueResult())
						.intValue();
		return count;
	}

	public int getEnvImgRecordCounts() throws SQLException {
		Session session = factory.getCurrentSession();
		int count = -1;
		count = ((Number) session.createCriteria(HolderImageBean.class).add(Restrictions.eq("Type", "ENV"))
				.add(Restrictions.eq("userID", userID)).setProjection(Projections.rowCount()).uniqueResult())
						.intValue();
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
	public Collection<HolderBean> getPageHolder() throws SQLException {

		Collection<HolderBean> coll = null;
		Session session = factory.getCurrentSession();
		int startRecordNo = (pageNo - 1) * recordsPerPage;
		coll = session.createQuery("from HolderBean order by createTime desc").setFirstResult(startRecordNo)
				.setMaxResults(recordsPerPage).list();
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
		count = ((Number) session.createCriteria(HolderBean.class).setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
		return count;
	}

}
