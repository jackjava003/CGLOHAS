package _03_recipes.model;

public class recipesBean {
	private int recipesID;
	private String name;
	private String culture;
	private String type;
	private byte[] image;
	

	public recipesBean() {
	}
	
	public recipesBean(int recipesID, String name, String culture, String type) {
		super();
		this.recipesID = recipesID;
		this.name = name;
		this.culture = culture;
		this.type = type;
	}




	public int getRecipesID() {
		return recipesID;
	}


	public void setRecipesID(int recipesID) {
		this.recipesID = recipesID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCulture() {
		return culture;
	}


	public void setCulture(String culture) {
		this.culture = culture;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}



}
