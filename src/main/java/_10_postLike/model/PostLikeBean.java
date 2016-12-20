package _10_postLike.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class PostLikeBean implements Serializable {

    private int postLikeID;
    private int holderID;
    private int posterID;
    private String postType;
    private Timestamp postTime;

    public PostLikeBean() {
    }
    
    

	public PostLikeBean(int holderID, int posterID ,String postType) {
		super();
		this.holderID = holderID;
		this.posterID = posterID;
		this.postType=postType;
	}



	public PostLikeBean(int postLikeID, int holderID, int posterID, Timestamp postTime) {
		super();
		this.postLikeID = postLikeID;
		this.holderID = holderID;
		this.posterID = posterID;
		this.postTime = postTime;
	}



	public int getPostLikeID() {
		return postLikeID;
	}

	public void setPostLikeID(int postLikeID) {
		this.postLikeID = postLikeID;
	}

	public int getHolderID() {
		return holderID;
	}

	public void setHolderID(int holderID) {
		this.holderID = holderID;
	}

	public int getPosterID() {
		return posterID;
	}

	public void setPosterID(int posterID) {
		this.posterID = posterID;
	}

	public Timestamp getPostTime() {
		return postTime;
	}

	public void setPostTime(Timestamp postTime) {
		this.postTime = postTime;
	}



	public String getPostType() {
		return postType;
	}



	public void setPostType(String postType) {
		this.postType = postType;
	}

	
    
    

}