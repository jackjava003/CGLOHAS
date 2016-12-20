package _04_listItems.model;

public class item_bean {
	private int itemID;  //商品ID
	private String name; //商品名稱
	private String info; //商品訊息
	private int price;	//商品價格
	private int storage; //庫存數量
	private String type; //商品種類
	private int popular; //熱門程度
	private byte[] itemImg; //照片
	private double discount;
	
	
	public item_bean() {
		super();
	}
	public item_bean(int itemID, String name, String info, int price, int storage, String type, int popular, byte[] itemImg, double discount) {
		super();
		this.itemID = itemID;
		this.name = name;
		this.info = info;
		this.price = price;
		this.storage = storage;
		this.type = type;
		this.popular = popular;
		this.itemImg = itemImg;
		this.discount = discount;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStorage() {
		return storage;
	}
	public void setStorage(int storage) {
		this.storage = storage;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPopular() {
		return popular;
	}
	public void setPopular(int popular) {
		this.popular = popular;
	}
	public byte[] getItemImg() {
		return itemImg;
	}

	public void setItemImg(byte[] itemImg) {
		this.itemImg = itemImg;
	}
	
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	
}
