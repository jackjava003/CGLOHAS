package _04_listItems.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class item_DAO implements item_DAO_interface {
	// JNDI context已經設定完成
	// 帳號:root 密碼:password url="jdbc:mysql://127.0.0.1:3306/cglohas"
	// 資料庫名稱: cglohas 若連線出現問題 可嘗試將resources_jar中的driver匯入
	// 匯入方法 專案右鍵 Build Path ->Configure Build Path -> ADD JAR
	// CGLOHAS -> src -> main -> resources_jar 底下的jar檔匯入
	// Servers須加入jar檔 底下Servers視窗->Tomcat v9.0雙按->Open launch configuration
	// ->Class path -> ADD JAR

	// 若使用JNDI連線需使用 --> context.lookup("java:comp/env/jdbc/cglohas");

	private int itemID;
	private DataSource ds = null;
	private int pageNo = 0;
	private int recordsPerPage = 12;
	private int totalPages = -1;
	private String type;
	static private List<String> typeList = new ArrayList<>();

	public item_DAO() throws NamingException {

		Context context = new InitialContext();
		ds = (DataSource) context.lookup("java:comp/env/jdbc/cglohas");
		if (typeList.isEmpty()) {
			populatetypeList();
		}
	}

	private void populatetypeList() {
		String typeString = "SELECT TYPE from item group by TYPE";
		try (Connection connection = ds.getConnection();
				PreparedStatement pStmt = connection.prepareStatement(typeString);
				ResultSet rs = pStmt.executeQuery();) {
			while (rs.next()) {
				typeList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkStorage(int itemID, int qty) {
		String storageString = "SELECT storage from item where itemID = ?";
		ResultSet rs = null;
		int storage = 0;
		try (Connection connection = ds.getConnection();
				PreparedStatement pStmt = connection.prepareStatement(storageString);) {
			pStmt.setInt(1, itemID);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				storage = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return storage >= qty;
	}

	public int getTotalPages() throws SQLException {
		// 計算總共有幾頁
		if (totalPages == -1) {
			// 注意下一列的double型態轉換
			totalPages = (int) (Math.ceil(getRecordCounts() / (double) recordsPerPage));
		}
		return totalPages;
	}

	@Override
	public Collection<item_bean> getSelectALL() throws SQLException {

		Collection<item_bean> coll = new ArrayList<item_bean>();
		String queryAllBooksString = "SELECT * from item ORDER BY itemID";
		ResultSet rs = null;
		try (Connection connection = ds.getConnection();
				PreparedStatement pStmt = connection.prepareStatement(queryAllBooksString);) {

			rs = pStmt.executeQuery();
			while (rs.next()) {
				item_bean bean = new item_bean();
				// 將此紀錄內的資料放入BookBean物件
				bean.setItemID(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setInfo(rs.getString(3));
				bean.setPrice(rs.getInt(4));
				bean.setStorage(rs.getInt(5));
				bean.setType(rs.getString(6));
				bean.setPopular(rs.getInt(7));
				bean.setItemImg(rs.getBytes(8));
				bean.setDiscount(rs.getDouble(9));
				coll.add(bean);
			}
		}
		return coll;

	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public void setType(String type) {
		if (typeList.contains(type.trim())) {
			this.type = type.trim();
		} else {
			this.type = null;
		}
	}

	// 與jsp配合 抓取指定筆數 使用(limit)
	public Collection<item_bean> getPageItems() throws SQLException {
		ResultSet rs = null;
		Collection<item_bean> coll = new ArrayList<item_bean>();
		String queryPageString;
		if (type == null || type.trim().length() == 0) {
			queryPageString = "SELECT * from item limit ?, ?";
		} else {
			queryPageString = "SELECT * from item where TYPE='" + type + "' limit ?, ?";
		}

		try (Connection connection = ds.getConnection();
				PreparedStatement pStmt = connection.prepareStatement(queryPageString);) {

			int startRecordNo = (pageNo - 1) * recordsPerPage;
			// int recordsPerPage = (pageNo) * recordsPerPage;
			// PreparedStatement物件內所有的問號都要有值，否則執行pStmt.executeQuery()
			// 或pStmt.executeUpdate()時程式一定會掛掉。
			pStmt.setInt(1, startRecordNo);
			pStmt.setInt(2, recordsPerPage);
			rs = pStmt.executeQuery();
			// 如果ResultSet內含有未讀取的紀錄
			while (rs.next()) {
				// 建立一個新的BookBean物件
				item_bean bean = new item_bean();
				// 將此紀錄內的資料放入BookBean物件
				bean.setItemID(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setInfo(rs.getString(3));
				bean.setPrice(rs.getInt(4));
				bean.setStorage(rs.getInt(5));
				bean.setType(rs.getString(6));
				bean.setPopular(rs.getInt(7));
				bean.setItemImg(rs.getBytes(8));
				bean.setDiscount(rs.getDouble(9));
				// 最後將BookBean物件放入大的容器內
				coll.add(bean);
			}
		}
		return coll;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	@Override
	public void setSelectID(int itemID) {
		// jsp set使用
		this.itemID = itemID;
	}

	@Override
	public item_bean getSelect() throws SQLException {
		// jsp get使用
		return select(itemID);
	}

	// 抓單一個item
	@Override
	public item_bean select(int itemID) throws SQLException {
		item_bean bean = null;
		ResultSet rs = null;
		String queryString = "SELECT * FROM item WHERE itemID = ?";
		try (Connection connection = ds.getConnection();
				PreparedStatement pStmt = connection.prepareStatement(queryString);) {
			pStmt.setInt(1, itemID);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				bean = new item_bean();
				bean.setItemID(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setInfo(rs.getString(3));
				bean.setPrice(rs.getInt(4));
				bean.setStorage(rs.getInt(5));
				bean.setType(rs.getString(6));
				bean.setPopular(rs.getInt(7));
				bean.setItemImg(rs.getBytes(8));
				bean.setDiscount(rs.getDouble(9));
			}
		}
		return bean;
	}

	// 傳回資料庫有多少筆資料 (換頁使用)
	public int getRecordCounts() throws SQLException {
		String queryPageString;
		if (type == null || type.trim().length() == 0) {
			queryPageString = "SELECT count(*) FROM item";
		} else {
			queryPageString = "SELECT count(*) FROM item where TYPE='" + type + "'";
			System.out.println(queryPageString);
		}
		ResultSet rs = null;
		int result = 0;
		try (Connection connection = ds.getConnection();
				PreparedStatement pStmt = connection.prepareStatement(queryPageString);) {

			rs = pStmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public int insert(item_bean ib) {
		// 此方法 商品"文字"資料傳入Database
		// table = item
		return 0;
	}

	@Override
	public int delete(int itemID) {

		return 0;
	}

	// 以下為ANDROID使用
	public byte[] getImage(int id) {
		String sql = "SELECT IMAGE FROM item WHERE ITEMID = ?";
		byte[] image = null;
		try (Connection connection = ds.getConnection(); PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return image;
	}

	public List<item_bean> getAll() {
		String sql="";
		if (type == null || type.trim().length() == 0) {
			sql = "SELECT ITEMID, NAME, INFO, PRICE, STORAGE, TYPE, POPULAR, DISCOUNT "
					+ "FROM item ORDER BY ITEMID";
		} else {
			sql = "SELECT ITEMID, NAME, INFO, PRICE, STORAGE, TYPE, POPULAR, DISCOUNT "
					+ "FROM item where TYPE='"+ type +"' ORDER BY ITEMID";
		}
		List<item_bean> itemList = new ArrayList<item_bean>();
		try (Connection connection = ds.getConnection(); PreparedStatement ps = connection.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				item_bean ib = new item_bean();
				ib.setItemID(rs.getInt(1));
				ib.setName(rs.getString(2));
				ib.setInfo(rs.getString(3));
				ib.setPrice(rs.getInt(4));
				ib.setStorage(rs.getInt(5));
				ib.setType(rs.getString(6));
				ib.setPopular(rs.getInt(7));
				ib.setDiscount(rs.getDouble(8));
				itemList.add(ib);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemList;
	}

	public int insert(item_bean item, byte[] image) {
		int count = 0;
		// String sql = "INSERT INTO Spot"
		// + "(name, phoneNo, address, latitude, longitude, image) "
		// + "VALUES(?, ?, ?, ?, ?, ?);";
		// Connection connection = null;
		// PreparedStatement ps = null;
		// try {
		// connection = DriverManager.getConnection(Common.URL, Common.USER,
		// Common.PASSWORD);
		// ps = connection.prepareStatement(sql);
		// ps.setString(1, shop.getName());
		// ps.setString(2, shop.getPhoneNo());
		// ps.setString(3, shop.getAddress());
		// ps.setDouble(4, shop.getLatitude());
		// ps.setDouble(5, shop.getLongitude());
		// ps.setBytes(6, image);
		// count = ps.executeUpdate();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// if (ps != null) {
		// // When a Statement object is closed,
		// // its current ResultSet object is also closed
		// ps.close();
		// }
		// if (connection != null) {
		// connection.close();
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }
		return count;
	}

	//

	public int update(item_bean item, byte[] image) {
		int count = 0;
		// String sql = "UPDATE Spot SET name = ?, phoneNo = ?, address = ?,
		// latitude = ?, longitude = ?, image = ? WHERE id = ?;";
		// Connection connection = null;
		// PreparedStatement ps = null;
		// try {
		// connection = DriverManager.getConnection(Common.URL, Common.USER,
		// Common.PASSWORD);
		// ps = connection.prepareStatement(sql);
		// ps.setString(1, shop.getName());
		// ps.setString(2, shop.getPhoneNo());
		// ps.setString(3, shop.getAddress());
		// ps.setDouble(4, shop.getLatitude());
		// ps.setDouble(5, shop.getLongitude());
		// ps.setBytes(6, image);
		// ps.setInt(7, shop.getId());
		// count = ps.executeUpdate();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// if (ps != null) {
		// // When a Statement object is closed,
		// // its current ResultSet object is also closed
		// ps.close();
		// }
		// if (connection != null) {
		// connection.close();
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }
		return count;
	}
	
	public item_bean findById(int id) {
//		String sql = "SELECT name, phoneNo, address, latitude, longitude FROM Spot WHERE id = ?;";
//		Connection conn = null;
//		PreparedStatement ps = null;
		item_bean item = null;
//		try {
//			conn = DriverManager.getConnection(Common.URL, Common.USER,
//					Common.PASSWORD);
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, id);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				String name = rs.getString(1);
//				String phoneNo = rs.getString(2);
//				String address = rs.getString(3);
//				double latitude = rs.getDouble(4);
//				double longitude = rs.getDouble(5);
//				shop = new Shop(id, name, phoneNo, address, latitude, longitude);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (ps != null) {
//					ps.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		return item;
	}
	
	// 抓單一個itemName Android use
		public item_bean selectName(int itemID) throws SQLException {
			item_bean bean = null;
			ResultSet rs = null;
			String queryString = "SELECT ITEMID, NAME, INFO, PRICE, STORAGE, TYPE, POPULAR, DISCOUNT FROM item "
					+ "WHERE itemID = ?";
			
			try (Connection connection = ds.getConnection();
					PreparedStatement pStmt = connection.prepareStatement(queryString);) {
				pStmt.setInt(1, itemID);
				rs = pStmt.executeQuery();
				if (rs.next()) {
					bean = new item_bean();
					bean.setItemID(rs.getInt(1));
					bean.setName(rs.getString(2));
					bean.setInfo(rs.getString(3));
					bean.setPrice(rs.getInt(4));
					bean.setStorage(rs.getInt(5));
					bean.setType(rs.getString(6));
					bean.setPopular(rs.getInt(7));
					bean.setDiscount(rs.getDouble(8));
				}
			}
			return bean;
		}

	

}
