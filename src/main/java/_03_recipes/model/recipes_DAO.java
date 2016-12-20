package _03_recipes.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import _00_init.HibernateUtil;

public class recipes_DAO {

	private int recipesID;
	private int pageNo = 0;
	private int recordsPerPage = 4;
	private int totalPages = -1;
	private String type;
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	static private List<String> typeList = new ArrayList<>();

	public recipes_DAO() throws NamingException {
		if (typeList.isEmpty()) {
			populatetypeList();
		}
	}

	@SuppressWarnings("unchecked")
	private void populatetypeList() {
		Session session = sessionFactory.getCurrentSession();
		typeList = session.createCriteria(recipesBean.class).setProjection(Projections.property("type"))
				.setProjection(Projections.groupProperty("type")).list();
	}

	public void setType(String type) {
		if (typeList.contains(type.trim())) {
			this.type = type.trim();
		} else {
			this.type = null;
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<processBean> getRecipesProcess() {
		Collection<processBean> coll = null;
		Session session = sessionFactory.getCurrentSession();
		coll = session.createQuery("from processBean where recipeID = :recipes_id order by processID")
				.setParameter("recipes_id", recipesID).list();
		return coll;
	}

	@SuppressWarnings("unchecked")
	public Collection<recipes_itemBean> getRecipesItems() {
		Collection<recipes_itemBean> coll = null;
		Session session = sessionFactory.getCurrentSession();
		coll = session.createQuery("from recipes_itemBean where recipesID = :recipes_id")
				.setParameter("recipes_id", recipesID).list();
		return coll;
	}
	
	//Android use only
	@SuppressWarnings("unchecked")
	public Collection<processBean> getAndroidRecipesProcess() {
		Collection<processBean> coll = null;
		Collection<processBean> collmpImage = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		coll = session.createQuery("from processBean where recipeID = :recipes_id order by processID")
				.setParameter("recipes_id", recipesID).list();
		for(processBean pb:coll){
			processBean pbNoImage = new processBean();
			pbNoImage.setProcessID(pb.getProcessID());
			pbNoImage.setRecipeID(pb.getRecipeID());
			pbNoImage.setProcess(pb.getProcess());
			collmpImage.add(pbNoImage);
		}
		return collmpImage;
	}

	public int getTotalPages() throws SQLException {
		// 計算總共有幾頁
		if (totalPages == -1) {
			// 注意下一列的double型態轉換
			totalPages = (int) (Math.ceil(getRecordCounts() / (double) recordsPerPage));
		}
		return totalPages;
	}
	
	//android 
	@SuppressWarnings("unchecked")
	public Collection<recipesBean> getSelectALL() {

		Collection<recipesBean> coll = null;
		Collection<recipesBean> collnopic = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		if(type==null||type.trim().length()==0){
		coll = session.createQuery("from recipesBean order by recipesID").list();
		}else{
			coll = session.createQuery("from recipesBean where type = :type order by recipesID").setParameter("type", type).list();	
		}
		for(recipesBean rb:coll){
			recipesBean rbnopic = new recipesBean();
			rbnopic.setRecipesID(rb.getRecipesID());
			rbnopic.setName(rb.getName());
			rbnopic.setType(rb.getType());
			collnopic.add(rbnopic);
		}
		return collnopic;
	}
	
	//android 
	@SuppressWarnings("unchecked")
	public recipesBean getOneRecipe(int recipeID) {

			recipesBean coll = new recipesBean();
			Session session = sessionFactory.getCurrentSession();
			coll = (recipesBean) session.get(recipesBean.class, recipeID);
			return coll;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	// 與jsp配合 抓取指定筆數 使用(limit)
	@SuppressWarnings("unchecked")
	public Collection<recipesBean> getPageRecipes() throws SQLException {

		Collection<recipesBean> coll = null;
		Session session = sessionFactory.getCurrentSession();
		int startRecordNo = (pageNo - 1) * recordsPerPage;
		String queryPageString;
		if (type == null || type.trim().length() == 0) {
			queryPageString = "from recipesBean";
		} else {
			queryPageString = "from recipesBean where TYPE='" + type + "'";
		}
		coll = session.createQuery(queryPageString).setFirstResult(startRecordNo).setMaxResults(recordsPerPage).list();
		return coll;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setSelectID(int recipesID) {
		// jsp set使用
		this.recipesID = recipesID;
	}

	public recipesBean getSelect() throws SQLException {
		// jsp get使用
		return select(recipesID);
	}

	// 抓單一個item
	public recipesBean select(int recipesID) throws SQLException {

		recipesBean rb = null;
		Session session = sessionFactory.getCurrentSession();
		Integer ipk = Integer.valueOf(recipesID);
		rb = (recipesBean) session.get(recipesBean.class, ipk);
		return rb;

	}

	// 傳回資料庫有多少筆資料 (換頁使用)
	public int getRecordCounts() throws SQLException {
		Session session = sessionFactory.getCurrentSession();
		int count = -1;
		if (type == null || type.trim().length() == 0) {
			count = ((Number) session.createCriteria(recipesBean.class).setProjection(Projections.rowCount())
					.uniqueResult()).intValue();
		} else {
			count = ((Number) session.createCriteria(recipesBean.class).add(Restrictions.eq("type",type)).setProjection(Projections.rowCount())
					.uniqueResult()).intValue();
		}
		
		return count;
	}

	public int insert(recipesBean rb) {
		// 此方法 商品"文字"資料傳入Database
		// table = item
		return 0;
	}

	public int delete(int recipesID) {

		return 0;
	}

	public byte[] getImage(int id) {
		recipesBean rb = null;
		Session session = sessionFactory.getCurrentSession();
		rb = (recipesBean) session.get(recipesBean.class, id);
		return rb.getImage();
	}
	public byte[] getProcessImage(int processID,int recipeID) {
		processBean pb2 = new processBean();
		pb2.setProcessID(processID);
		pb2.setRecipeID(recipeID);
		processBean pb = null;
		Session session = sessionFactory.getCurrentSession();
		pb = (processBean) session.get(processBean.class, pb2);
		return pb.getImage();
	}

}
