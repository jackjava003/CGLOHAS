package _09_community.model;

import java.sql.Timestamp;

import javax.persistence.OneToMany;

public class HolderBean {
	private int userID;
	private int zipCode;
	private String city;
	private String area;
	private String location;
	private String cook_exp;
	private String protype;
	private String pro_other;
	private String foodIntro;
	private String cookday;
	private String cooktime;
	private String limitno;
	private String vegetarian;
	private String price;
	private String open_condition;
	private String selfInfo;
	private Timestamp createTime;

	public HolderBean() {

	}

	
	
	public HolderBean(int userID, int zipCode, String city, String area, String location, String cook_exp,
			String protype, String pro_other, String foodIntro, String cookday, String cooktime, String limitno,
			String vegetarian, String price, String open_condition, String selfInfo) {
		super();
		this.userID = userID;
		this.zipCode = zipCode;
		this.city = city;
		this.area = area;
		this.location = location;
		this.cook_exp = cook_exp;
		this.protype = protype;
		this.pro_other = pro_other;
		this.foodIntro = foodIntro;
		this.cookday = cookday;
		this.cooktime = cooktime;
		this.limitno = limitno;
		this.vegetarian = vegetarian;
		this.price = price;
		this.open_condition = open_condition;
		this.selfInfo = selfInfo;
	}



	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCook_exp() {
		return cook_exp;
	}
	
	public String getEngCook_exp() {
		return cook_exp.replaceAll("半年-1年", "6 months-1 year").replaceAll("1-2年", "1-2 years")
				.replaceAll("2-3年", "2-3 years").replaceAll("4-5年", "4-5 years")
				.replaceAll("5年以上", "above 5 years");
	}

	public void setCook_exp(String cook_exp) {
		this.cook_exp = cook_exp;
	}

	public String getProtype() {
		return protype;
	}

	public void setProtype(String protype) {
		this.protype = protype;
	}

	public String getPro_other() {
		return pro_other;
	}

	public void setPro_other(String pro_other) {
		this.pro_other = pro_other;
	}

	public String getFoodIntro() {
		return foodIntro;
	}

	public void setFoodIntro(String foodIntro) {
		this.foodIntro = foodIntro;
	}
	public String getFoodIntroTextArea(){
		return foodIntro.replaceAll("<br>", "\r\n");
	}

	public String getCookday() {
		return cookday;
	}

	public void setCookday(String cookday) {
		this.cookday = cookday;
	}

	public String getCooktime() {
		return cooktime;
	}

	public void setCooktime(String cooktime) {
		this.cooktime = cooktime;
	}

	public String getLimitno() {
		return limitno;
	}
	
	public String getEngLimitno() {
		return limitno.replaceAll("1人", "1 person").replaceAll("2人", "2 people").replaceAll("3人", "3 people")
				.replaceAll("4人", "4 people").replaceAll("5人", "5 people").replaceAll("6人以上", "More than 6 people");
	}


	public void setLimitno(String limitno) {
		this.limitno = limitno;
	}

	public String getVegetarian() {
		return vegetarian;
	}
	
	public String getEngVegetarian() {
		return vegetarian.replaceAll("無提供素食","Unavailable").replaceAll("提供素食","Available");
	}

	public void setVegetarian(String vegetarian) {
		this.vegetarian = vegetarian;
	}

	public String getEngPrice() {
		return price.replaceAll("元", " NTD").replaceAll("以上"," above").replaceAll("以下"," below");
	}
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOpen_condition() {
		return open_condition;
	}
	public String getEngOpen_condition() {
		return open_condition.replaceAll("歡迎搭伙", "Available").replaceAll("目前額滿", "Unavailable");
	}
	
	public void setOpen_condition(String open_condition) {
		this.open_condition = open_condition;
	}

	public String getSelfInfo() {
		return selfInfo;
	}

	public void setSelfInfo(String selfInfo) {
		this.selfInfo = selfInfo;
	}
	public String getSelfInfoTextArea(){
		return selfInfo.replaceAll("<br>", "\r\n");
	}
	
	public String getProTypeString(){
		return protype.replaceAll("\\|", "&nbsp;&nbsp;&nbsp;").replaceAll("中式正餐", "Chinese-style")
				.replaceAll("西式正餐", "Western-style").replaceAll("甜點", "Dessert").replaceAll("麵包", "Bread")
				.replaceAll("湯品", "Soup").replaceAll("手作小食", "Hand-made snacks").replaceAll("其他", "Other");
	}
	
	public String[] getProTypeArr(){
		return protype.split("\\|");
	}
	
	public String getCookDayString(){
		return cookday.replaceAll("\\|", "&nbsp;&nbsp;&nbsp;").replaceAll("星期一", "Mon")
				.replaceAll("星期二", "Tue").replaceAll("星期三", "Wed").replaceAll("星期四", "Thu")
				.replaceAll("星期五", "Fri").replaceAll("星期六", "Sat").replaceAll("星期日", "Sun");
	}
	public String[] getCookDayArr(){
		return cookday.split("\\|");
	}

	public String getCookTimeString(){
		return cooktime.replaceAll("\\|", "&nbsp;&nbsp;&nbsp;").replaceAll("晚餐", "Dinner")
				.replaceAll("中餐", "Lunch").replaceAll("下午茶", "Afternoon tea").replaceAll("其他", "Other");
	}
	public String[] getCookTimeArr(){
		return cooktime.split("\\|");
	}
	public String getHideAddress(){
		String hiddenFrom = null;
		if(location.indexOf("街")!=-1){
			hiddenFrom = "街";
		}else if (location.indexOf("路")!=-1){
			hiddenFrom = "路";
		}else if(location.indexOf("巷")!=-1){
			hiddenFrom = "巷";
		}
		if(hiddenFrom!=null){
			return  location.substring(0, location.indexOf(hiddenFrom)+1) +"*******";
		}else if(hiddenFrom==null && location.length()>5){
			return location.substring(0, 4) +"*******";
		}else{
			return "*******";
		}
		
	}

}
