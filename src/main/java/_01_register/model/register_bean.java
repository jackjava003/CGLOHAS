package _01_register.model;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

public class register_bean {
	private int memberId;
	private int userID; //使用者ID auto_increment
	private String account; // 帳號
	private String password; // 密碼
	private String name; // 自訂姓名
	private String email; // 信箱
	private String cellphone; // 手機
	private String sex; //性別
	private java.sql.Timestamp createTime; // 加入會員日期
	private java.sql.Date birthDate; // 生日
	private String birthdate1;
	private int like_count; // 讚數
	private int dislike_count; // 爛數
	private Blob memberImage;  //圖片
	private String verified; //驗證
	

	
	public register_bean(String account, String password, String name, String email, String cellphone,String sex, java.sql.Date birthDate) {
		super();
		this.account = account;
		this.password = password;
		this.name = name;
		this.email = email;
		this.cellphone = cellphone;
		this.sex = sex;
		this.birthDate = birthDate;
		//此建構子為註冊會員使用  將使用者輸入資料塞入此建構子  DAO的insert方法需要一個bean 
		//userID為auto_increment 不須給值
		//讚數 爛數 bd有預設值 為0  createTime db預設值為current_timeStamp 可省略
		//生日需傳入sql的Date型態  需在Controller轉換
		//轉換範例:
		//java.util.Date date2 = new java.util.Date("1999/10/10");   從使用者輸入讀入資料為1999/10/10  確認無誤 直接放入Date
		//java.sql.Date sqdate = new java.sql.Date(date2.getTime()); 使用getTime()轉為long放入sql的DATE
	}
	
	


	public register_bean(int memberId, int userID, String account, String password, String name, String email,
			String cellphone, String sex, Timestamp createTime, Date birthDate, String birthdate1, int like_count,
			int dislike_count, Blob memberImage, String verified) {
		super();
		this.memberId = memberId;
		this.userID = userID;
		this.account = account;
		this.password = password;
		this.name = name;
		this.email = email;
		this.cellphone = cellphone;
		this.sex = sex;
		this.createTime = createTime;
		this.birthDate = birthDate;
		this.birthdate1 = birthdate1;
		this.like_count = like_count;
		this.dislike_count = dislike_count;
		this.memberImage = memberImage;
		this.verified = verified;
	}

	public register_bean(int userID,String account, String name, String email, String cellphone,
			Timestamp createTime, java.sql.Date birthDate, int like_count, int dislike_count) {
		super();
		this.userID=userID;
		this.account = account;
		this.name = name;
		this.email = email;
		this.cellphone = cellphone;
		this.createTime = createTime;
		this.birthDate = birthDate;
		this.like_count = like_count;
		this.dislike_count = dislike_count;
		//此建構子為 顯示會員資料使用
		//密碼不需跟著顯示 所以此建構子無密碼參數
		//需放入LoginOK 
	}
	
	public String getbirthdate1() {
		return birthdate1;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Blob getMemberImage() {
		return memberImage;
	}

	public void setMemberImage(Blob memberImage) {
		this.memberImage = memberImage;
	}

	public register_bean() {

	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public java.sql.Timestamp getCreateTime() {
		return createTime; //注意 此處傳回sql的timestamp 有toString方法  (包含秒 毫秒)
	}

	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime; 
	}

	public java.sql.Date getBirthDate() {
		
		return birthDate ;
		
	}

	public void setBirthDate(java.sql.Date birthDate) {
		this.birthDate = birthDate;
		this.birthdate1= birthDate.toString();
	}

	public int getLike_count() {
		return like_count;
	}

	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}

	public int getDislike_count() {
		return dislike_count;
	}

	public void setDislike_count(int dislike_count) {
		this.dislike_count = dislike_count;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}
	

}
