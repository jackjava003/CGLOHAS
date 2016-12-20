package _09_community.model;

import java.sql.Timestamp;

import _00_init.TimeService;

public class MessageBean {

	private int messageID;
	private int userID;
	private int holderID;
	private String message;
	private Timestamp createTime;
	private String replyMessage;
	private Timestamp replyTime;
	private String hidden;

	public MessageBean() {
	}
	
	public MessageBean(int userID, int holderID, String message,String hidden) {
		super();
		this.userID = userID;
		this.holderID = holderID;
		this.message = message;
		this.hidden = hidden;
	}
	
	public MessageBean(int userID, int holderID, String message, Timestamp createTime,
			String replyMessage, Timestamp replyTime) {
		super();
		this.userID = userID;
		this.holderID = holderID;
		this.message = message;
		this.createTime = createTime;
		this.replyMessage = replyMessage;
		this.replyTime = replyTime;
	}

	

	public MessageBean(int messageID, int userID, int holderID, String message, Timestamp createTime,
			String replyMessage, Timestamp replyTime, String hidden) {
		super();
		this.messageID = messageID;
		this.userID = userID;
		this.holderID = holderID;
		this.message = message;
		this.createTime = createTime;
		this.replyMessage = replyMessage;
		this.replyTime = replyTime;
		this.hidden = hidden;
	}

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getHolderID() {
		return holderID;
	}

	public void setHolderID(int holderID) {
		this.holderID = holderID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	public String getCreateRealTime(){
		return TimeService.getRelativeTime(createTime);
	}

	public String getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public Timestamp getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Timestamp replyTime) {
		this.replyTime = replyTime;
	}
	
	public String getReplyRealTime(){
		return TimeService.getRelativeTime(replyTime);
	}

}
