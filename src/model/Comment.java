package model;

public class Comment {

	private String footId;
	private int userId;
	private String comment;
	private int floor;
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public String getFootId() {
		return footId;
	}
	public void setFootId(String footId) {
		this.footId = footId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Comment(String footId, int userId, String comment) {
		super();
		this.footId = footId;
		this.userId = userId;
		this.comment = comment;
	}
	public Comment() {
		// TODO Auto-generated constructor stub
	}

}
