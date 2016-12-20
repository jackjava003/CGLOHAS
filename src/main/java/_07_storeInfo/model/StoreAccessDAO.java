package _07_storeInfo.model;

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

import _03_recipes.model.recipesBean;

public class StoreAccessDAO {
	private DataSource ds = null;
	private int storeid;

	public StoreAccessDAO() throws NamingException {

		Context context = new InitialContext();
		ds = (DataSource) context.lookup("java:comp/env/jdbc/cglohas");

	}

	public Collection<StoreAccessBean> getSelectALL() throws SQLException {
		// jsp get使用
		return selectALL();
	}

	// 選取所有店家資訊
	public Collection<StoreAccessBean> selectALL() throws SQLException {

		PreparedStatement pStmt = null;
		Connection connection = null;
		ResultSet rs = null;

		Collection<StoreAccessBean> coll = new ArrayList<StoreAccessBean>();
		try {
			// 店家資訊 FROM store ,圖片FROM storeimage TABLE
			String queryPageString = "SELECT  " + " storeid, storename, shortdesc, longdesc FROM store  ";
			connection = ds.getConnection();
			pStmt = connection.prepareStatement(queryPageString);

			System.out.println("queryPageString==>" + queryPageString);

			rs = pStmt.executeQuery();

			// 如果ResultSet內含有未讀取的紀錄
			while (rs.next()) {
				// 建立一個新的StoreAccessBean物件
				StoreAccessBean bean = new StoreAccessBean();
				// 將此紀錄內的資料放入StoreAccessBean物件
				bean.setStoreid(rs.getInt(1));
				bean.setStorename(rs.getString(2));
				bean.setShortdesc(rs.getString(3));
				bean.setLongdesc(rs.getString(4));
				// bean.setImage_type(rs.getString(5));
				// bean.setImage(rs.getBlob(6));
				// 最後將StoreAccessBean物件放入大的容器內
				coll.add(bean);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pStmt != null) {
				pStmt.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return coll;
	}

	public Collection<StoreAccessBean> getSelectOne() throws SQLException {
		// jsp get使用
		return selectOne(storeid);
	}

	// 選取單一店家資訊
	public Collection<StoreAccessBean> selectOne(int storeid) throws SQLException {

		PreparedStatement pStmt = null;
		Connection connection = null;
		ResultSet rs = null;

		Collection<StoreAccessBean> coll = new ArrayList<StoreAccessBean>();
		try {
			// 店家資訊 from store ,圖片from storeimage TABLE
			String queryPageString = "SELECT  "
					+ " storeid, storename, shortdesc, longdesc FROM store WHERE storeid = ? ";
			connection = ds.getConnection();
			pStmt = connection.prepareStatement(queryPageString);
			pStmt.setInt(1, storeid);
			System.out.println("queryPageString==>" + queryPageString);
			rs = pStmt.executeQuery();

			// 如果ResultSet內含有未讀取的紀錄
			while (rs.next()) {
				// 建立一個新的StoreAccessBean物件
				StoreAccessBean bean = new StoreAccessBean();
				// 將此紀錄內的資料放入StoreAccessBean物件
				bean.setStoreid(rs.getInt(1));
				bean.setStorename(rs.getString(2));
				bean.setShortdesc(rs.getString(3));
				bean.setLongdesc(rs.getString(4));
				// bean.setImage_type(rs.getString(5));
				// bean.setImage(rs.getBlob(6));
				// 最後將StoreAccessBean物件放入大的容器內
				coll.add(bean);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pStmt != null) {
				pStmt.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return coll;
	}

	public void setSelectStoreID(int storeid) {
		// jsp set使用
		this.storeid = storeid;
	}

	// public int getSelectStoreID(int storeid) {
	// return storeid;
	// }

	public Collection<StoreAccessDetailBean> getSelectLoc() {
		return selectLoc(storeid);
	}

	// 抓單一個store的分店資訊(FROM locations）

	public Collection<StoreAccessDetailBean> selectLoc(int storeid) {

		Collection<StoreAccessDetailBean> coll = new ArrayList<StoreAccessDetailBean>();

		StoreAccessDetailBean sab = null;
		ResultSet rs = null;
		String queryString = "SELECT * FROM location WHERE storeid = ?";
		try (Connection connection = ds.getConnection();
				PreparedStatement pStmt = connection.prepareStatement(queryString);) {
			pStmt.setInt(1, storeid);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				sab = new StoreAccessDetailBean();
				sab.setLocationid(rs.getInt(1));
				sab.setStoreid(rs.getInt(2));
				sab.setS_name(rs.getString(3));
				sab.setLat(rs.getDouble(4));
				sab.setLongi(rs.getDouble(5));
				sab.setAddress(rs.getString(7));
				sab.setPhone(rs.getString(8));

				coll.add(sab);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		System.out.println(coll.size());
		return coll;
	}

	public List<StoreAccessDetailBean> getSelectLocDetail() {
		return selectLocDetail(storeid);
	}
	
	public List<StoreAccessDetailBean> getSelectLocDetailAD(int storeid) {
		return selectLocDetail(storeid);
	}

	// 抓單一個store的分店資訊(FROM locations）

	public List<StoreAccessDetailBean> selectLocDetail(int storeid) {

		List<StoreAccessDetailBean> coll = new ArrayList<StoreAccessDetailBean>();

		StoreAccessDetailBean sab = null;
		ResultSet rs = null;
		String queryString = "SELECT * FROM location WHERE storeid = ?";
		try (Connection connection = ds.getConnection();
				PreparedStatement pStmt = connection.prepareStatement(queryString);) {
			pStmt.setInt(1, storeid);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				sab = new StoreAccessDetailBean();
				sab.setLocationid(rs.getInt(1));
				sab.setStoreid(rs.getInt(2));
				sab.setS_name(rs.getString(3));
				sab.setLat(rs.getDouble(4));
				sab.setLongi(rs.getDouble(5));
				sab.setAddress(rs.getString(7));
				sab.setPhone(rs.getString(8));

				coll.add(sab);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		System.out.println(coll.size());
		return coll;
	}

	// 回上一頁的部分
	public void setPageNo(int pageNo) {
		// TODO Auto-generated method stub

	}

	// 回上一頁的部分
	public Collection<recipesBean> getPageRecipes() {
		// TODO Auto-generated method stub
		return null;
	}

	// 回上一頁的部分
	public Object getTotalPages() {
		// TODO Auto-generated method stub
		return null;
	}

	// public List<StoreAccessDetailBean> getSelectLocDetail() {
	// return selectLocDetail(storeid);
	// }

	// 抓單一個store的經緯度locations

	// public List<StoreAccessDetailBean> selectLocDetail(int storeid) {
	//
	// List<StoreAccessDetailBean> list = new
	// ArrayList<StoreAccessDetailBean>();
	//
	//
	// ResultSet rs = null;
	// String queryString = "SELECT storeid, lat AND longi FROM location WHERE
	// storeid = ?";
	// try (Connection connection = ds.getConnection();
	// PreparedStatement pStmt = connection.prepareStatement(queryString);) {
	// pStmt.setInt(1, storeid);
	// rs = pStmt.executeQuery();
	// while (rs.next()) {
	//
	//
	// int id = rs.getInt("storeid");
	// double lat = rs.getDouble("lat");
	// double longi = rs.getDouble("longi");
	// StoreAccessDetailBean sab = new StoreAccessDetailBean(id, lat, longi);
	// // sab.setLat(rs.getDouble(0));
	//// sab.setLongi(rs.getDouble(1));
	//
	// list.add(sab);
	// }
	//
	// }catch(SQLException se){
	// se.printStackTrace();
	// }
	// System.out.println(list.size());
	// return list;
	// }

	// 以下為ANDROID
	public int insert(StoreAccessDetailBean shop, byte[] image) {
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

	public int update(StoreAccessDetailBean shop, byte[] image) {
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
	//

	public int delete(int id) {
		int count = 0;
		// String sql = "DELETE FROM Spot WHERE id = ?;";
		// Connection connection = null;
		// PreparedStatement ps = null;
		// try {
		// connection = DriverManager.getConnection(Common.URL, Common.USER,
		// Common.PASSWORD);
		// ps = connection.prepareStatement(sql);
		// ps.setInt(1, id);
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

	public byte[] getImage(int id, int storeid) {
		String sql = "SELECT image FROM location WHERE locationid = ? AND storeid = ?;";
		byte[] image = null;
		try (Connection connection = ds.getConnection(); PreparedStatement ps = connection.prepareStatement(sql);) {

			ps.setInt(1, id);
			ps.setInt(2, storeid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				image = rs.getBytes(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	
	public byte[] getMainStoreImage(int id) {
		String sql = "SELECT image FROM store_image WHERE storeid = ?;";
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

	public StoreAccessDetailBean findById(int id) {
		// String sql = "SELECT name, phoneNo, address, latitude, longitude FROM
		// Spot WHERE id = ?;";
		// Connection conn = null;
		// PreparedStatement ps = null;
		StoreAccessDetailBean shop = null;
		// try {
		// conn = DriverManager.getConnection(Common.URL, Common.USER,
		// Common.PASSWORD);
		// ps = conn.prepareStatement(sql);
		// ps.setInt(1, id);
		// ResultSet rs = ps.executeQuery();
		// if (rs.next()) {
		// String name = rs.getString(1);
		// String phoneNo = rs.getString(2);
		// String address = rs.getString(3);
		// double latitude = rs.getDouble(4);
		// double longitude = rs.getDouble(5);
		// shop = new Shop(id, name, phoneNo, address, latitude, longitude);
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// if (ps != null) {
		// ps.close();
		// }
		// if (conn != null) {
		// conn.close();
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }
		return shop;
	}

	public List<StoreAccessDetailBean> getAll() {
		String sql = "SELECT locationid, storeid, s_name, lat, longi, address, phone "
				+ "FROM location ORDER BY locationid DESC;";
		List<StoreAccessDetailBean> shopList = new ArrayList<StoreAccessDetailBean>();
		try (Connection connection = ds.getConnection(); PreparedStatement ps = connection.prepareStatement(sql);) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int locationid = rs.getInt(1);
				int storeid = rs.getInt(2);
				String s_name = rs.getString(3);
				double lat = rs.getDouble(4);
				double longi = rs.getDouble(5);
				// Blob image = rs.getBlob(6);
				String address = rs.getString(6);
				String phone = rs.getString(7);
				StoreAccessDetailBean shop = new StoreAccessDetailBean(locationid, storeid, s_name, lat, longi, address,
						phone);
				shopList.add(shop);
			}
			return shopList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shopList;
	}
	
	public List<MainStoreAccessBean> getSelectMainStoreALL() throws SQLException {
		// Android 選取主店使用
		return selectMainStoreALL();
	}

	// 選取所有店家資訊
	public List<MainStoreAccessBean> selectMainStoreALL() throws SQLException {

		PreparedStatement pStmt = null;
		Connection connection = null;
		ResultSet rs = null;

		List<MainStoreAccessBean> list = new ArrayList<MainStoreAccessBean>();
		try {
			// 店家資訊 FROM store ,圖片FROM storeimage TABLE
			String queryPageString = "SELECT  " + " storeid, storename, shortdesc, longdesc FROM store  ";
			connection = ds.getConnection();
			pStmt = connection.prepareStatement(queryPageString);

			System.out.println("queryPageString==>" + queryPageString);

			rs = pStmt.executeQuery();

			// 如果ResultSet內含有未讀取的紀錄
			while (rs.next()) {
				// 建立一個新的StoreAccessBean物件
				MainStoreAccessBean bean = new MainStoreAccessBean();
				// 將此紀錄內的資料放入StoreAccessBean物件
				bean.setStoreid(rs.getInt(1));
				bean.setStorename(rs.getString(2));
				bean.setShortdesc(rs.getString(3));
				bean.setLongdesc(rs.getString(4));
				// bean.setImage_type(rs.getString(5));
				// bean.setImage(rs.getBlob(6));
				// 最後將StoreAccessBean物件放入大的容器內
				list.add(bean);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pStmt != null) {
				pStmt.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		return list;
	}
}
