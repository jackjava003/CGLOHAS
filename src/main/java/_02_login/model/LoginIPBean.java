package _02_login.model;

import java.sql.Timestamp;

public class LoginIPBean {

	private String IPAddress;
	private int WrongTimes;
	private Timestamp LastTryTime;

	public LoginIPBean() {

	}

	public LoginIPBean(String iPAddress, int wrongTimes, Timestamp lastTryTime) {
		super();
		IPAddress = iPAddress;
		WrongTimes = wrongTimes;
		LastTryTime = lastTryTime;
	}

	public String getIPAddress() {
		return IPAddress;
	}

	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}

	public int getWrongTimes() {
		return WrongTimes;
	}

	public void setWrongTimes(int wrongTimes) {
		WrongTimes = wrongTimes;
	}

	public Timestamp getLastTryTime() {
		return LastTryTime;
	}

	public void setLastTryTime(Timestamp lastTryTime) {
		LastTryTime = lastTryTime;
	}

}
