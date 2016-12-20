package _04_listItems.model;

import java.sql.SQLException;
import java.util.Collection;

public interface item_DAO_interface {
	public Collection<item_bean> getSelectALL() throws SQLException;
	public void setSelectID(int itemID) ;
	public item_bean getSelect() throws SQLException;
	public item_bean select(int itemID) throws SQLException;
	public int insert(item_bean ib);
	public int delete(int itemID);
}
