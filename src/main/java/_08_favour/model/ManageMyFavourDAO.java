package _08_favour.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import _00_init.HibernateUtil;
import _03_recipes.model.recipesBean;
import _03_recipes.model.recipes_DAO;

public class ManageMyFavourDAO {
	private SessionFactory factory = HibernateUtil.getSessionFactory();
	private int userID;
	private int recordsPerPage = 5;
	private int totalPages = -1;
	private int pageNo = 0;

	public void setFavourUserID(int userID) {
		this.userID = userID;
	}

	public Collection<FavourBean> getUserFavour() {
		return selectFavour(userID);
	}

	@SuppressWarnings("unchecked")
	public Collection<FavourBean> selectFavour(int userID) {
		Session session = factory.getCurrentSession();
		Collection<FavourBean> coll = null;
		coll = session.createQuery("from FavourBean where userID = :userID order by createTime desc")
				.setParameter("userID", userID).list();
		return coll;
	}
	
	public int delete(FavourBean favour) {
		Session session = factory.getCurrentSession();
		int updateCount = -1;
		session.delete(favour);
		updateCount = 1;
		return updateCount;
	}

	public int insert(FavourBean favour) {
		Session session = factory.getCurrentSession();
		//Transaction tx = session.getTransaction();
		int updateCount = -1;
		FavourBean fb = (FavourBean) session.get(FavourBean.class, favour);
		if (fb != null) {
			session.delete(fb);
		}
		session.save(favour);
		updateCount = 1;
		//tx.commit();
		return updateCount;
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
	public Collection<FavourBean> getPageFavour() throws SQLException {

		Collection<FavourBean> coll = null;
		Session session = factory.getCurrentSession();
		int startRecordNo = (pageNo - 1) * recordsPerPage;
		coll = session.createQuery("from FavourBean where userID = :userID order by createTime desc").setParameter("userID", userID).setFirstResult(startRecordNo).setMaxResults(recordsPerPage)
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
		count = ((Number) session.createCriteria(FavourBean.class).add(Restrictions.eq("userID", userID)).setProjection(Projections.rowCount())
				.uniqueResult()).intValue();
		return count;
	}
	
	//Android
	@SuppressWarnings("unchecked")
	public List<recipesBean> getUserFavourAndroid() throws SQLException, NamingException {

		Collection<FavourBean> coll = null;
		Session session = factory.getCurrentSession();
		coll = session.createQuery("from FavourBean where userID = :userID order by createTime desc").setParameter("userID", userID).list();
		//System.out.println(startRecordNo +"   "+coll.size());
		recipes_DAO rdao = new recipes_DAO();
		List<recipesBean> list = new ArrayList<>();
		for(FavourBean fb:coll){
			int recipesID = fb.getRecipesID();
			recipesBean rb = rdao.getOneRecipe(recipesID);
			recipesBean rb2 = new recipesBean();
			rb2.setName(rb.getName());
			rb2.setRecipesID(rb.getRecipesID());
			rb2.setType(rb.getType());
			rb2.setCulture(rb.getCulture());
			list.add(rb2);
		}
		return list;
	}
}
