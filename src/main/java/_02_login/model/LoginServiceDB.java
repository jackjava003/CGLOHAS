package _02_login.model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.*;
import javax.sql.*;

import _00_init.*;
import _01_register.model.register_bean;
public class LoginServiceDB implements LoginServiceDAO {
	static private List<register_bean> memberList = new ArrayList<>();
	private DataSource ds = null;

	public LoginServiceDB() throws NamingException, SQLException {
		Context ctx = new InitialContext();
		ds = (DataSource) ctx.lookup("java:comp/env/jdbc/cglohas");
		if (memberList.isEmpty()) {
			populateMemberList();
		}
	}
	
	public LoginServiceDB(boolean reset) throws NamingException, SQLException {
		if(reset == true){
		Context ctx = new InitialContext();
		ds = (DataSource) ctx.lookup("java:comp/env/jdbc/cglohas");
		memberList.clear();
		populateMemberList();
		}
	}
	
	public void populateMemberList() throws SQLException {
		// 由Database讀取會員資料
		
		register_bean temp =null;
		try(Connection conn = ds.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from register_user");
			ResultSet rs = stmt.executeQuery();
				){
				while(rs.next())
				{
					temp= new register_bean();
					temp.setUserID(rs.getInt(1));
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
					memberList.add(temp);
				}
		}catch(Exception e){
			e.printStackTrace();
		}
	
	
}

	public register_bean checkAccountPassword(String Account, String password) {
		// 檢查userId與password是否正確
		
		for (register_bean mb : memberList) {
			if ((mb.getAccount().trim()).equals(Account.trim())) {
				String encrypedString = GlobalService.encryptString(password.trim());
				String pswd = GlobalService.getMD5Endocing(encrypedString);
				String mbpswd = mb.getPassword().trim();
				if (mbpswd.equals(pswd.trim())) {
					return mb;
				}
			}
		}
		return null;
	}
	
	public register_bean checkAccountPasswordWithoutEncode(String Account, String password) {
		// 檢查userId與password是否正確
		
		for (register_bean mb : memberList) {
			if ((mb.getAccount().trim()).equals(Account.trim())) {
				String mbpswd = mb.getPassword().trim();
				if (mbpswd.equals(password.trim())) {
					return mb;
				}
			}
		}
		return null;
	}
	

	public List<register_bean> getMemberList() {
		return memberList;
	}
	public void addNewMember(register_bean mb) {
		memberList.add(mb);
	}


	
}
