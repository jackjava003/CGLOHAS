package _03_recipes.model;

public class recipes_itemBean {
	private int recipesID;
	private int itemID;
	private String detail;
	
	
	public recipes_itemBean(){
		
	}
	
	public recipes_itemBean(int recipesID, int itemID, String detail) {
		super();
		this.recipesID = recipesID;
		this.itemID = itemID;
		this.detail = detail;
	}
	public int getRecipesID() {
		return recipesID;
	}
	public void setRecipesID(int recipesID) {
		this.recipesID = recipesID;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
