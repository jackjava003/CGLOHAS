package _09_community.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import _00_init.TimeService;

public class JoinBean implements Serializable {
	private int holderID;
	private int joinerID;
	private Date startDate;
	private String favourType;
	private String favour_other;
	private String AVLday;
	private String AVLtime;
	private String pplNeed;
	private String vegetarian;
	private String contact;
	private String other_need;
	private Timestamp createTime;
	
	
	public JoinBean(){
		
	}
	
	
	public JoinBean(int holderID, int joinerID, Date startDate, String favourType, String favour_other, String aVLday,
			String aVLtime, String pplNeed, String vegetarian, String contact, String other_need) {
		super();
		this.holderID = holderID;
		this.joinerID = joinerID;
		this.startDate = startDate;
		this.favourType = favourType;
		this.favour_other = favour_other;
		AVLday = aVLday;
		AVLtime = aVLtime;
		this.pplNeed = pplNeed;
		this.vegetarian = vegetarian;
		this.contact = contact;
		this.other_need = other_need;
	}


	public JoinBean(int holderID, int joinerID, Date startDate, String favourType, String favour_other, String aVLday,
			String aVLtime, String pplNeed, String vegetarian, String contact, String other_need,
			Timestamp createTime) {
		super();
		this.holderID = holderID;
		this.joinerID = joinerID;
		this.startDate = startDate;
		this.favourType = favourType;
		this.favour_other = favour_other;
		AVLday = aVLday;
		AVLtime = aVLtime;
		this.pplNeed = pplNeed;
		this.vegetarian = vegetarian;
		this.contact = contact;
		this.other_need = other_need;
		this.createTime = createTime;
	}
	
	public int getHolderID() {
		return holderID;
	}
	public void setHolderID(int holderID) {
		this.holderID = holderID;
	}
	public int getJoinerID() {
		return joinerID;
	}
	public void setJoinerID(int joinerID) {
		this.joinerID = joinerID;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getFavourType() {
		return favourType;
	}
	public void setFavourType(String favourType) {
		this.favourType = favourType;
	}
	public String getFavour_other() {
		return favour_other;
	}
	public void setFavour_other(String favour_other) {
		this.favour_other = favour_other;
	}
	public String getAVLday() {
		return AVLday;
	}
	public void setAVLday(String aVLday) {
		AVLday = aVLday;
	}
	public String getAVLtime() {
		return AVLtime;
	}
	public void setAVLtime(String aVLtime) {
		AVLtime = aVLtime;
	}
	public String getPplNeed() {
		return pplNeed;
	}
	public String getEngPplNeed() {
		return pplNeed.replaceAll("1人", "1 person").replaceAll("2人", "2 people").replaceAll("3人", "3 people")
				.replaceAll("4人", "4 people").replaceAll("5人", "5 people").replaceAll("6人以上", "More than 6 people");
	}
	public void setPplNeed(String pplNeed) {
		this.pplNeed = pplNeed;
	}
	public String getVegetarian() {
		return vegetarian;
	}
	public String getEngVegetarian() {
		return vegetarian.replaceAll("是","Vegetarian").replaceAll("否","non-Vegetarian");
	}
	public void setVegetarian(String vegetarian) {
		this.vegetarian = vegetarian;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getOther_need() {
		return other_need;
	}
	public void setOther_need(String other_need) {
		this.other_need = other_need;
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
	
	public String getOtherNeedTextArea(){
		return other_need.replaceAll("<br>", "\r\n");
	}
	
	public String getFavourTypeString(){
		return favourType.replaceAll("\\|", "&nbsp;&nbsp;&nbsp;").replaceAll("中式正餐", "Chinese-style").replaceAll("西式正餐", "Western-style").replaceAll("甜點", "Dessert").replaceAll("麵包", "Bread")
				.replaceAll("湯品", "Soup").replaceAll("手作小食", "Hand-made snacks").replaceAll("其他", "Other");
	}
	
	public String[] getFavourTypeArr(){
		return favourType.split("\\|");
	}
	
	public String getAvlDayString(){
		return AVLday.replaceAll("\\|", "&nbsp;&nbsp;&nbsp;").replaceAll("星期一", "Mon")
				.replaceAll("星期二", "Tue").replaceAll("星期三", "Wed").replaceAll("星期四", "Thu")
				.replaceAll("星期五", "Fri").replaceAll("星期六", "Sat").replaceAll("星期日", "Sun");
	}
	public String[] getAvlDayArr(){
		return AVLday.split("\\|");
	}

	public String getAvlTimeString(){
		return AVLtime.replaceAll("\\|", "&nbsp;&nbsp;&nbsp;").replaceAll("晚餐", "Dinner")
				.replaceAll("中餐", "Lunch").replaceAll("下午茶", "Afternoon tea").replaceAll("其他", "Other");
	}
	public String[] getAvlTimeArr(){
		return AVLtime.split("\\|");
	}
	
	

	
}	
