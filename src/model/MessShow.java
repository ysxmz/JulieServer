package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MessShow {

	private String messId; // ����id
    private String userpicUrl;
	private int userId; // �����������˵�id
	private String phone; // ������ϵ�绰
	private String name; // ������ϵ����
	private String content; // ��������
	private String wechat; // weixin
	private String time;
	private Integer commentNum;
	
	public MessShow(String messId,String phone,String name,String content,String wechat,String userpicUrl,String time,Integer commentNum){
		this.messId=messId;
		this.phone=phone;
		this.name=name;
		this.content=content;
		this.wechat=wechat;
		this.userpicUrl = userpicUrl;
		this.time=time;
		this.commentNum=commentNum;
	}
	
public MessShow(String messId,String phone,String name,String content,String wechat,String userpicUrl,String time) {
		super();
		this.messId = messId;
		this.userpicUrl = userpicUrl;
		this.userId = userId;
		this.phone = phone;
		this.name = name;
		this.content = content;
		this.wechat = wechat;
		this.time = time;
	}

	//	private String createMessId() {
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//        String newDate=sdf.format(new Date());
//        String result="";
//        Random random=new Random();
//        for(int i=0;i<3;i++){
//            result+=random.nextInt(10);
//        }
//        return newDate+result;
//	}
//	
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
	
	public String getUserpicUrl() {
        return userpicUrl;
    }

    public void setUserpicUrl(String userpicUrl) {
        this.userpicUrl = userpicUrl;
    }
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
	
}
