package _09_community.model;

import java.io.Serializable;
import java.sql.Blob;

public class HolderImageBean implements Serializable{
	private int userID;
	private int image_id;
	private String Type;
	private Blob image;
	private String imageInfo;
	
	
	
	
	public HolderImageBean() {}
	
	public HolderImageBean(int userID, String type, Blob image, String imageInfo) {
		super();
		this.userID = userID;
		Type = type;
		this.image = image;
		this.imageInfo = imageInfo;
	}

	public HolderImageBean(int userID, int image_id, String type, Blob image, String imageInfo) {
		super();
		this.userID = userID;
		this.image_id = image_id;
		Type = type;
		this.image = image;
		this.imageInfo = imageInfo;
	}
	public HolderImageBean(int image_id, int userID, String type, String imageInfo) {
		super();
		this.userID = userID;
		this.image_id = image_id;
		Type = type;
		this.imageInfo = imageInfo;
	}

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getImage_id() {
		return image_id;
	}
	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public String getImageInfo() {
		return imageInfo;
	}
	public void setImageInfo(String imageInfo) {
		this.imageInfo = imageInfo;
	}
	
}
