package _11_notification.model;

import java.io.Serializable;
import java.sql.Timestamp;

import _00_init.TimeService;

public class NotifyBean implements Serializable{
    
    // 儲存通知用的類別
    private int notifyID;	// 通知流水號
    private int senderID;		// 送件者
    private int receiverID;		// 收件者
    private String senderName;		// 發送者姓名
    private int notifyType;		// 通知類型，1，搭伙申請，2，點讚通知，3，留言通知，4，開伙者對留言者回復
    private  int checkRead;		// 0，未讀通知。 1，已讀通知。
    private Timestamp sendTime;		// 通知時間點
    private String realTime;
    
    public NotifyBean() {	
    }

	public NotifyBean(int senderID, int receiverID, String senderName, int notifyType, int check) {
		super();
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.senderName = senderName;
		this.notifyType = notifyType;
		this.checkRead = check;
	}



	public NotifyBean(int notifyID, int senderID, int receiverID, String senderName, int notifyType, int check,
			Timestamp sendTime) {
		super();
		this.notifyID = notifyID;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.senderName = senderName;
		this.notifyType = notifyType;
		this.checkRead = check;
		this.sendTime = sendTime;
		this.realTime = TimeService.getRelativeTime(sendTime);
	}



	public int getNotifyID() {
		return notifyID;
	}

	public void setNotifyID(int notifyID) {
		this.notifyID = notifyID;
	}

	public int getSenderID() {
		return senderID;
	}

	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}

	public int getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(int receiverID) {
		this.receiverID = receiverID;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

	public int getCheckRead() {
		return checkRead;
	}

	public void setCheckRead(int check) {
		this.checkRead = check;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
		this.realTime = TimeService.getRelativeTime(sendTime);
	}
	
	public void setRealTime(){
		
	}
	
	public String getSendRealTime() {
		return TimeService.getRelativeTime(sendTime);
	}
    
}