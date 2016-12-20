package _06_orderProcess.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import _00_init.HibernateUtil;
import _04_listItems.model.item_bean;
import _05_ShoppingCart.model.OrderBean;
import _05_ShoppingCart.model.OrderItemDAOBean;
import _08_favour.model.FavourBean;

public class ManageOrderDAO {
	private SessionFactory factory = HibernateUtil.getSessionFactory();
	private int userID;
	private int recordsPerPage = 5;
	private int totalPages = -1;
	private int pageNo = 0;
	
	public ManageOrderDAO() {
	}

	@SuppressWarnings("unchecked")
	public boolean deleteOrder(int orderID, int userID) {

		Collection<OrderItemDAOBean> orderItemList = new ArrayList<OrderItemDAOBean>();
		Session session = factory.getCurrentSession();
		boolean result = false;
		Query q = session.createQuery("from OrderItemDAOBean where orderID = :order_id");
		q.setParameter("order_id", orderID);
		orderItemList = q.list();
		for (OrderItemDAOBean oidb : orderItemList) {
			int itemid = oidb.getItemID();
			int qty = oidb.getAmount();
			item_bean ib = (item_bean) session.load(item_bean.class, itemid);
			int stock = ib.getStorage() + qty;
			ib.setStorage(stock);
			// session.update(ib);
			session.delete(oidb);
		}
		OrderBean ob = new OrderBean();
		ob.setOrderID(orderID);
		session.delete(ob);
		result = true;

		return result;
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
	public Collection<OrderBean> getOrderFavour() throws SQLException {

		Collection<OrderBean> coll = null;
		Session session = factory.getCurrentSession();
		int startRecordNo = (pageNo - 1) * recordsPerPage;
		coll = session.createQuery("from OrderBean where userID = :userID order by orderdate desc").setParameter("userID", userID).setFirstResult(startRecordNo).setMaxResults(recordsPerPage)
				.list();
		//System.out.println(startRecordNo +"   "+coll.size());
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
		count = ((Number) session.createCriteria(OrderBean.class).add(Restrictions.eq("userID", userID)).setProjection(Projections.rowCount())
				.uniqueResult()).intValue();
		return count;
	}
	public void setOrderUserID(int userID) {
		this.userID = userID;
	}
	
	//Android
	@SuppressWarnings("unchecked")
	public Collection<OrderBean> getUserOrder() throws SQLException {

		Collection<OrderBean> coll = null;
		Session session = factory.getCurrentSession();
		coll = session.createQuery("from OrderBean where userID = :userID order by orderdate desc").setParameter("userID", userID)
				.list();
		//System.out.println(startRecordNo +"   "+coll.size());
		return coll;
	}

}
