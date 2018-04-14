package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Foot {

	private String footId; // 订单id
	private int userId; // 发布订单的人的id
	private String phone; // 订单联系电话
	private String name; // 订单联系姓名
	private String address; // 送达地址
	private String content; // 订单内容
	private double reward; // 赏金
	private int state; // 订单状态 1新发布 2被抢了 3已送达 4已结单
	

	public Foot(int userId, String phone, String name, String address, String content, double reward) {
		this.footId = createFootId();
		this.userId = userId;
		this.phone = phone;
		this.name = name;
		this.address = address;
		this.content = content;
		this.reward = reward;
		this.state = 1;
	}

	private String createFootId() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getReward() {
		return reward;
	}

	public void setReward(double reward) {
		this.reward = reward;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
