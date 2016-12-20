package _01_register.model;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public interface register_DAO_interface {
	public List<register_bean> getSelectALL();
	public register_bean getSelect(int userID);
	public int insert(register_bean rb);
	public int delete(int userID);
	public int saveMember(register_bean mb, InputStream is, long size)
	throws SQLException ;
}
