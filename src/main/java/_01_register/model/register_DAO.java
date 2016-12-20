
package _01_register.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import _00_init.SendMailThread;
import _02_login.model.LoginServiceDB;

public class register_DAO implements register_DAO_interface {
	// JNDI context已經設定完成
	// 帳號:root 密碼:password url="jdbc:mysql://127.0.0.1:3306/cglohas"
	// 資料庫名稱: cglohas 若連線出現問題 可嘗試將resources_jar中的driver匯入
	// 匯入方法 專案右鍵 Build Path ->Configure Build Path -> ADD JAR
	// CGLOHAS -> src -> main -> resources_jar 底下的jar檔匯入

	// 若使用JNDI連線需使用 --> context.lookup("java:comp/env/jdbc/cglohas");
	private List<register_bean> memberList;
	private int userID;
	private DataSource ds = null;

	public register_DAO() {
		try {
			Context context = new InitialContext();
			ds = (DataSource) context.lookup("java:comp/env/jdbc/cglohas");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 檢查用 如果前面正確 到這邊錯誤 就會阻擋
	public boolean accountExists(String account) throws IOException {
		boolean exist = false; // 檢查帳戶是否已經存在
		register_DAO dao = new register_DAO();
		List<register_bean> list = dao.getSelectALL();
		for (register_bean rb : list) {
			if (rb.getAccount().equals(account)) {
				exist = true;
			}
		}
		return exist;
	}

	public boolean emailExists(String email) throws IOException {
		boolean exist = false; // 檢查帳戶是否已經存在
		register_DAO dao = new register_DAO();
		List<register_bean> list = dao.getSelectALL();
		for (register_bean rb : list) {
			if (rb.getEmail().equals(email)) {
				exist = true;
			}
		}
		return exist;
	}

	public boolean phoneExists(String phone) throws IOException {
		boolean exist = false; // 檢查帳戶是否已經存在
		register_DAO dao = new register_DAO();
		List<register_bean> list = dao.getSelectALL();
		for (register_bean rb : list) {
			if (rb.getCellphone().equals(phone)) {
				exist = true;
			}
		}
		return exist;
	}

	public int updateImg(int userID, InputStream is, long sizeInBytes) {

		int count = 0;
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("UPDATE register_user SET userphoto = ?  where userID = ?");) {
			pstmt.setBinaryStream(1, is, sizeInBytes);
			pstmt.setInt(2, userID);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<register_bean> getSelectALL() {
		List<register_bean> list = new ArrayList<>();
		register_bean temp = null;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement("select * from register_user");
				ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				temp = new register_bean();
				temp.setMemberId(rs.getInt(1));
				temp.setAccount(rs.getString(2));
				temp.setPassword(rs.getString(3));
				temp.setName(rs.getString(4));
				temp.setEmail(rs.getString(5));
				temp.setCellphone(rs.getString(6));
				temp.setSex(rs.getString(7));
				temp.setCreateTime(rs.getTimestamp(8));
				temp.setBirthDate(rs.getDate(9));
				temp.setLike_count(rs.getInt(10));
				temp.setDislike_count(rs.getInt(11));
				temp.setVerified(rs.getString(13));
				list.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void setSelectID(int userID) {
		// jsp set使用
		this.userID = userID;
	}

	public register_bean getSelect() {
		return getSelect(userID);
	}

	@Override
	public register_bean getSelect(int userID) {
		// 取出會員"文字"資料 (使用get開頭 讓jsp也可使用)
		register_bean temp = null;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement("select * from register_user where userID = ?")) {
			stmt.setInt(1, userID);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					temp = new register_bean();
					temp.setMemberId(rs.getInt(1));
					temp.setAccount(rs.getString(2));
					temp.setPassword(rs.getString(3));
					temp.setName(rs.getString(4));
					temp.setEmail(rs.getString(5));
					temp.setCellphone(rs.getString(6));
					temp.setSex(rs.getString(7));
					temp.setCreateTime(rs.getTimestamp(8));
					temp.setBirthDate(rs.getDate(9));
					temp.setLike_count(rs.getInt(10));
					temp.setDislike_count(rs.getInt(11));
					temp.setVerified(rs.getString(13));

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	synchronized public int saveMember(register_bean rb, InputStream is, long size) throws SQLException {
		int result = -1;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"insert into register_user (account, password, name, email, cellphone, sex, birthDate, userPhoto) values(?,?,?,?,?,?,?,?)");) {
			stmt.setString(1, rb.getAccount());
			stmt.setString(2, rb.getPassword());
			stmt.setString(3, rb.getName());
			stmt.setString(4, rb.getEmail());
			stmt.setString(5, rb.getCellphone());
			stmt.setString(6, rb.getSex());
			stmt.setDate(7, rb.getBirthDate());
			stmt.setBinaryStream(8, is, size);
			result = stmt.executeUpdate();
			System.out.println("create OK!!");
			SendMailThread smt = new SendMailThread(rb.getEmail(), rb.getAccount(), rb.getPassword());
			smt.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	@Override
	public int delete(int userID) {
		int result = -1;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement("delete from register_user where userID = ?");

		) {
			stmt.setInt(1, userID);
			result = stmt.executeUpdate();
			System.out.println("delete OK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int insert(register_bean rb) {
		return 0;
	}

	public register_bean forgetPw(String account, String email, String mobile) {
		register_bean temp = null;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"select * from register_user where account = ? and email=? and cellphone=?")) {
			stmt.setString(1, account);
			stmt.setString(2, email);
			stmt.setString(3, mobile);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					temp = new register_bean();
					temp.setMemberId(rs.getInt(1));
					temp.setAccount(rs.getString(2));
					temp.setPassword(rs.getString(3));
					temp.setName(rs.getString(4));
					temp.setEmail(rs.getString(5));
					temp.setCellphone(rs.getString(6));
					temp.setSex(rs.getString(7));
					temp.setCreateTime(rs.getTimestamp(8));
					temp.setBirthDate(rs.getDate(9));
					temp.setLike_count(rs.getInt(10));
					temp.setDislike_count(rs.getInt(11));
					temp.setVerified(rs.getString(13));

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	public register_bean forgetAc(String password, String email, String mobile) {
		register_bean temp = null;
		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"select * from register_user where password = ? and email=? and cellphone=?")) {
			stmt.setString(1, password);
			stmt.setString(2, email);
			stmt.setString(3, mobile);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					temp = new register_bean();
					temp.setMemberId(rs.getInt(1));
					temp.setAccount(rs.getString(2));
					temp.setPassword(rs.getString(3));
					temp.setName(rs.getString(4));
					temp.setEmail(rs.getString(5));
					temp.setCellphone(rs.getString(6));
					temp.setSex(rs.getString(7));
					temp.setCreateTime(rs.getTimestamp(8));
					temp.setBirthDate(rs.getDate(9));
					temp.setLike_count(rs.getInt(10));
					temp.setDislike_count(rs.getInt(11));
					temp.setVerified(rs.getString(13));

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	public int update(int userId, String columnName, String value) throws SQLException {

		int result = -1;
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("UPDATE register_user SET " + columnName + " = ?  where userID = ?" );) {
			pstmt.setString(1, value);
			pstmt.setInt(2, userId);
		
			result = pstmt.executeUpdate();

			if (columnName.equalsIgnoreCase("password")) {
				try {
					new LoginServiceDB(true);
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}

		}

		return result;
	}

	public int updateEmail(int userId, String value, String account, String password) {

		int result = -1;
		try (Connection conn = ds.getConnection();) {
			try (PreparedStatement pstmt = conn
					.prepareStatement("UPDATE register_user SET " + "email" + " = ?  where userID = ?");
					PreparedStatement pstmtVerified = conn
							.prepareStatement("UPDATE register_user SET  verified = ?  where userID = ?");) {
				conn.setAutoCommit(false);
				pstmt.setString(1, value);
				pstmt.setInt(2, userId);
				pstmtVerified.setString(1, "F");
				pstmtVerified.setInt(2, userId);
				result = result + pstmt.executeUpdate();

				result = result + pstmtVerified.executeUpdate();

				SendMailThread smt = new SendMailThread(value, account, password);
				smt.start();
				conn.commit();
				conn.setAutoCommit(true);
			} catch (Exception ex) {
				System.out.println("資料還原");
				ex.printStackTrace();
				conn.rollback();
				conn.setAutoCommit(true);
				result = -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

}
