package com.rivetlogic.payrollemailsender.model;

public class FileColumn {
	private int id;
	private String name;
	private String nameToUse;
	private Boolean use;
	private Boolean isEmail;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getUse() {
		return use;
	}
	
	public void setUse(Boolean use) {
		this.use = use;
	}
	
	public Boolean getIsEmail() {
		return isEmail;
	}
	
	public void setIsEmail(Boolean isEmail) {
		this.isEmail = isEmail;
	}

	public String getNameToUse() {
		return nameToUse;
	}

	public void setNameToUse(String nameToUse) {
		this.nameToUse = nameToUse;
	}
}
