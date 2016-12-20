package _02_login.model;

import java.sql.SQLException;
import java.util.List;

import _01_register.model.register_bean;

public interface LoginServiceDAO {
	public void populateMemberList() throws SQLException ;
	public register_bean checkAccountPassword(String Account, String password) ;
	public List<register_bean> getMemberList();
	public void addNewMember(register_bean mb);
}
