package com.example.contact;

public class Bean {
	private String imgId;
	private String name;
	private String phone;
	
	
	
	public String getImgId() {
		return imgId;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Bean [imgId=" + imgId + ", name=" + name + ", phone=" + phone
				+ "]";
	}
	
	
}
