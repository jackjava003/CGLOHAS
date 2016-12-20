package _05_ShoppingCart.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import _00_init.GlobalService;

public class OrderDAO {
	private static final long serialVersionUID = 1L;
	private String memberId = null;
	private DataSource ds = null;

	public OrderDAO() throws NamingException {
		Context ctx = new InitialContext();
		ds = (DataSource) ctx.lookup(GlobalService.JNDI_DB_NAME);
	}

	synchronized public String insertOrder(OrderBean ob) throws SQLException {
		String sqlOrder = "Insert Into orders "
				+ " (userID, address, totalamount, cellphone, CancelTag,ShippingDate,Description,Discount) "
				+ " values(?, ?, ?, ?, ?, ?,?,?) ";
		PreparedStatement pStmt = null; // insert 訂單
		ResultSet generatedKeys = null;
		PreparedStatement pStmt2 = null; // insert 訂單細項
		PreparedStatement pStmt3 = null; // 取出現有庫存
		PreparedStatement pStmt4 = null; // update 扣去購買數量後的庫存
		String result = "T";
		try (Connection conn = ds.getConnection();) {
			try {
				conn.setAutoCommit(false); // 開啟JDBC Transaction

				String sqlItem = "Insert Into OrderItems (itemid, unitPrice, Discount, amount, orderID) "
						+ " values(?, ?, ?, ?, ?) ";
				String getStorage = "Select storage from item where itemID = ?";
				String updateStorage = "Update item SET storage = ? where itemID = ?";
				List<OrderItemDAOBean> items = ob.getItems();
				pStmt2 = conn.prepareStatement(sqlItem);
				pStmt3 = conn.prepareStatement(getStorage);
				pStmt4 = conn.prepareStatement(updateStorage);

				for (OrderItemDAOBean oib : items) {
					int n = 0;
					pStmt3.setInt(1, oib.getItemID());
					ResultSet rs = pStmt3.executeQuery();
					int storage = 0;
					while (rs.next()) {
						storage = rs.getInt(1);
					}
					pStmt3.clearParameters();
					storage = storage - oib.getAmount();
					if(storage<0){
						throw new NumberFormatException(String.valueOf(oib.getItemID()));
					}else{
					pStmt4.setInt(1, storage);
					pStmt4.setInt(2, oib.getItemID());
					n = pStmt4.executeUpdate();}
					pStmt4.clearParameters();
				}
				pStmt = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
				pStmt.setInt(1, ob.getUserID());
				pStmt.setString(2, ob.getShippingAddress());
				System.out.print(ob.getTotalAmount());
				pStmt.setDouble(3, ob.getTotalAmount());
				pStmt.setString(4, ob.getCellphone());
				pStmt.setString(5, ob.getCancelTag());
				pStmt.setDate(6, ob.getShippingDate());
				pStmt.setString(7, ob.getDescription());
				pStmt.setDouble(8, ob.getDiscount());
				int updateResult =  pStmt.executeUpdate();
				if(updateResult!=1){
					throw new SQLException();
				}
				int id = 0;
				generatedKeys = pStmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					id = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating user failed, no generated key obtained.");
				}
				for (OrderItemDAOBean oib : items) {
					pStmt2.setInt(1, oib.getItemID());
					pStmt2.setDouble(2, oib.getUnitPrice());
					pStmt2.setDouble(3, oib.getDiscount());
					pStmt2.setInt(4, oib.getAmount());
					pStmt2.setInt(5, id);
					pStmt2.executeUpdate();
					pStmt2.clearParameters();
				}
				conn.commit();
			} catch (SQLException ex) {
				ex.printStackTrace();
				conn.rollback();
				result = "F";
			} catch(NumberFormatException x){
				conn.rollback();
				return x.toString() + "6666666";
			}
		}
		return result;
	}

	public OrderBean getOrder(int orderId) throws SQLException {
		String sqlOrder = "SELECT * FROM orders WHERE orderID = ? ";
		String sqlOrderItems = "SELECT * FROM orderItems WHERE orderId = ? order by seqNo";
		List<OrderItemDAOBean> items = new ArrayList<OrderItemDAOBean>();
		PreparedStatement pStmt = null;
		PreparedStatement pStmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		OrderBean ob = null;
		try (Connection conn = ds.getConnection();) {
			pStmt = conn.prepareStatement(sqlOrder);
			pStmt.setInt(1, orderId);
			rs = pStmt.executeQuery();

			if (rs.next()) {
				ob = new OrderBean();
				ob.setOrderID(rs.getInt(1));
				ob.setUserID(rs.getInt(2));
				ob.setShippingAddress(rs.getString(3).replaceAll("\\s", "&nbsp;"));
				ob.setTotalAmount(rs.getDouble(4));
				ob.setCellphone(rs.getString(5));
				ob.setOrderDate(rs.getTimestamp(6));
				ob.setCancelTag(rs.getString(7));
				ob.setShippingDate(rs.getDate(8));
				ob.setDescription(rs.getString(9).replaceAll("\\s", "&nbsp;").replaceAll("\\n", "<br>"));
				ob.setDiscount(rs.getInt(10));
			}
			if (ob == null) {
				throw new SQLException("資料庫邏輯錯誤：無此紀錄, 訂單編號=" + orderId);
			} else {
				pStmt2 = conn.prepareStatement(sqlOrderItems);
				pStmt2.setInt(1, orderId);
				rs2 = pStmt2.executeQuery();
				OrderItemDAOBean oib = null;
				while (rs2.next()) {
					oib = new OrderItemDAOBean();
					oib.setSeqNo(rs2.getInt(1));
					oib.setItemID(rs2.getInt(2));
					oib.setUnitPrice(rs2.getDouble(3));
					oib.setDiscount(rs2.getDouble(4));
					oib.setAmount(rs2.getInt(5));
					oib.setOrderNo(rs2.getInt(6));

					items.add(oib);
				}
			}
			ob.setItems(items);
		}
		return ob;
	}

	public Collection<OrderBean> getMemberOrders() throws SQLException {
		Collection<OrderBean> coll = new ArrayList<OrderBean>();
		String sqlOrder = "SELECT * FROM orders where userId = ? Order by orderDate desc ";
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		OrderBean ob = null;
		try (Connection conn = ds.getConnection();) {

			pStmt = conn.prepareStatement(sqlOrder);
			pStmt.setInt(1, Integer.valueOf(memberId));
			rs = pStmt.executeQuery();

			while (rs.next()) {
				ob = new OrderBean();
				ob.setOrderID(rs.getInt(1));
				ob.setUserID(rs.getInt(2));
				ob.setShippingAddress(rs.getString(3).replaceAll("\\s", "&nbsp;"));
				ob.setTotalAmount(rs.getDouble(4));
				ob.setCellphone(rs.getString(5));
				ob.setOrderDate(rs.getTimestamp(6));
				ob.setCancelTag(rs.getString(7));
				ob.setShippingDate(rs.getDate(8));
				ob.setDescription(rs.getString(9).replaceAll("\\s", "&nbsp;").replaceAll("\\n", "<br>"));
				ob.setDiscount(rs.getInt(10));
				coll.add(ob);
			}
		}
		return coll;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
