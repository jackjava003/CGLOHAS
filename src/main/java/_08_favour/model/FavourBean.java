package _08_favour.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class FavourBean implements Serializable {
	private int userID;
	private int recipesID;
	private Timestamp createTime;
	
	
	public FavourBean(){
		
	}
	
	public FavourBean(int userID, int recipesID, Timestamp createTime) {
		super();
		this.userID = userID;
		this.recipesID = recipesID;
		this.createTime = createTime;
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getRecipesID() {
		return recipesID;
	}
	public void setRecipesID(int recipesID) {
		this.recipesID = recipesID;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
}
