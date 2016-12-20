package _03_recipes.model;

import java.io.Serializable;

public class processBean implements Serializable{
	
	private int recipeID;
	private int processID;
	private String process;
	private byte[] image;
	
	
	
	public processBean() {
		super();
	}
	public processBean(int recipeID, int processID, String process, byte[] image) {
		super();
		this.recipeID = recipeID;
		this.processID = processID;
		this.process = process;
		this.image = image;
	}
	public int getRecipeID() {
		return recipeID;
	}
	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}
	public int getProcessID() {
		return processID;
	}
	public void setProcessID(int processID) {
		this.processID = processID;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	
	
	
}
