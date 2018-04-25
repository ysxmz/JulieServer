package model;

public class Order {
    private String footId;
    private int userId;
    private int receiveId;
    public Order(String footId, int userId,int receiveId, String userpicUrl, String username, String state, String content, String address, String reward, String time, String phone) {
        this.receiveId=receiveId;
        this.userId = userId;
        this.footId = footId;
        this.userpicUrl = userpicUrl;
        this.username = username;
        this.state = state;
        this.content = content;
        this.address = address;
        this.reward = reward;
        this.time = time;
        this.phone=phone;
    }

    private String userpicUrl;
    private String username;
    private String state;
    private String content;
    private String address;
    private String reward;
    private String time;
    private String phone;

    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(int receiveId) {
		this.receiveId = receiveId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

    public String getFootId() {
        return footId;
    }

    public void setFootId(String footId) {
        this.footId = footId;
    }

    public String getUserpicUrl() {
        return userpicUrl;
    }

    public void setUserpicUrl(String userpicUrl) {
        this.userpicUrl = userpicUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
