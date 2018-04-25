package model;

import java.util.ArrayList;

public class BaseBean {
	private int code;
	private String msg;
	private Object data;
	private ArrayList<Order> orderList;
	private ArrayList<Comm> commList;
	private ArrayList<Discount> discountList;
	private ArrayList<MessShow> messList;
	private ArrayList<InfoShow> infoList;

	
	public ArrayList<InfoShow> getInfoList() {
		return infoList;
	}

	public void setInfoList(ArrayList<InfoShow> infoList) {
		this.infoList = infoList;
	}

	public ArrayList<MessShow> getMessList() {
		return messList;
	}

	public void setMessList(ArrayList<MessShow> messList) {
		this.messList = messList;
	}

	public ArrayList<Discount> getDiscountList() {
		return discountList;
	}

	public void setDiscountList(ArrayList<Discount> discountList) {
		this.discountList = discountList;
	}

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
