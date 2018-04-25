package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Mess {

	private String messId; // ����id
    
	private int userId; // �����������˵�id
	private String phone; // ������ϵ�绰
	private String name; // ������ϵ����
	private String content; // ��������
	private String wechat; // weixin
	
	public Mess(int userId,String phone,String name,String content,String wechat){
		this.messId=createMessId();
		this.userId=userId;
		this.phone=phone;
		this.name=name;
		this.content=content;
		this.wechat=wechat;
		
	}
	
	private String createMessId() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
	}
	
	public String getMessId() {
		return messId;
	}
	public void setFootId(String messId) {
		this.messId = messId;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	
	
}
