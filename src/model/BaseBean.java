package model;

import java.util.ArrayList;

public class BaseBean {
	private int code;
	private String msg;
	private Object data;
	private ArrayList<Order> orderList;
	private ArrayList<Comm> commList;
	public ArrayList<Comm> getCommList() {
		return commList;
	}

	public void setCommList(ArrayList<Comm> commList) {
		this.commList = commList;
	}

	public ArrayList<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayList<Order> orderList) {
		this.orderList = orderList;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
