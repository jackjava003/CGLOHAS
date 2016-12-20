package _07_storeInfo.model;

import java.io.Serializable;
import java.sql.Blob;

public class StoreAccessBean implements Serializable  {
	
	int storeid;
	String storename;
	String shortdesc;
	String longdesc;
//	String image_type;
//	Blob image;
	
	

	public StoreAccessBean() {
		
	}

	public StoreAccessBean(int storeid, String storename, String shortdesc, String longdesc) {
		super();
		this.storeid = storeid;
		this.storename = storename;
		this.shortdesc = shortdesc;
		this.longdesc = longdesc;
		
	}
	
	public int getStoreid() {
		return storeid;
	}

	public void setStoreid(int storeid) {
		this.storeid = storeid;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getShortdesc() {
		return shortdesc;
	}

	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}

	public String getLongdesc() {
		return longdesc;
	}

	public void setLongdesc(String longdesc) {
		this.longdesc = longdesc;
	}

	
	
	
	
}
