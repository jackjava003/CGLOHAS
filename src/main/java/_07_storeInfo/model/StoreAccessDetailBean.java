package _07_storeInfo.model;

import java.io.Serializable;
import java.sql.Blob;

public class StoreAccessDetailBean implements Serializable{
	int locationid;
	int storeid;
	String s_name;
	double lat;
	double longi;
	String address;
	String  phone;
	
	public StoreAccessDetailBean(int locationid, int storeid, String s_name, double lat, double longi, String address,
			String phone) {
		super();
		this.locationid = locationid;
		this.storeid = storeid;
		this.s_name = s_name;
		this.lat = lat;
		this.longi = longi;
		this.address = address;
		this.phone = phone;
	}
	public StoreAccessDetailBean() {
		
	}
	public StoreAccessDetailBean(int storeid, double lat, double longi) {
		// TODO Auto-generated constructor stub
	}
	public int getLocationid() {
		return locationid;
	}
	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}
	public int getStoreid() {
		return storeid;
	}
	public void setStoreid(int storeid) {
		this.storeid = storeid;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLongi() {
		return longi;
	}
	public void setLongi(double longi) {
		this.longi = longi;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	
	
}
