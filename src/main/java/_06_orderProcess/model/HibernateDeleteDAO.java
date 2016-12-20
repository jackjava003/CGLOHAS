package _06_orderProcess.model;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import _00_init.HibernateUtil;
import _04_listItems.model.item_bean;
import _05_ShoppingCart.model.OrderBean;
import _05_ShoppingCart.model.OrderItemDAOBean;

public class HibernateDeleteDAO {
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public HibernateDeleteDAO() {
	}

	@SuppressWarnings("unchecked")
	public boolean deleteOrder(int orderID, int userID) {

		Collection<OrderItemDAOBean> orderItemList = new ArrayList<OrderItemDAOBean>();
		Session session = sessionFactory.getCurrentSession();
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

}
