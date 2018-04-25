package model;

public class InfoShow {
	private String username;
	private String picString;
	private String sex;
	private String location;
	private String describe;
	private String nickname;
	private String password;
	private int id;
	
	public InfoShow(String username,String sex,String location,String describe,String nickname) {
		this.username = username;
		this.sex = sex;
		this.location = location;
		this.describe = describe;
		this.nickname = nickname;
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPicString() {
		return picString;
	}
	public void setPicString(String picString) {
		this.picString = picString;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
