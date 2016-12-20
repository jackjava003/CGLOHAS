package _04_listItems.model;

public class OrderItemBean {

	int itemid;
	int qty;
	double unitPrice;
	double discount;
	int storage;

	

	public OrderItemBean(int itemid, int qty, double unitPrice, double discount) {
		super();
		this.itemid = itemid;
		this.qty = qty;
		this.unitPrice = unitPrice;
		this.discount = discount;
	}

	public OrderItemBean() {
		
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}
	
	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}


}
