package servelt;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;

import model.BaseBean;
import model.UserBean;
import util.DBUtils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int NAME_PWD = 1;
	public static final int ERROR_PWD = 2;
	public static final int NO_NAME = 3;
	public static final int ERROR = 4;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html;charset=utf-8");
		String username = request.getParameter("username"); // ��ȡ�ͻ��˴������Ĳ���
		String password = request.getParameter("password");

		if (username == null || username.equals("") || password == null || password.equals("")) {
			System.out.println("�û���������Ϊ��");
			return;
		}

		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect(); // �����ݿ�����
		BaseBean data = new BaseBean(); // ������󣬻ش����ͻ��˵�json����
		UserBean userBean = new UserBean(); // user�Ķ���
		if (dbUtils.isExistInDB(username, password) == NAME_PWD
				|| dbUtils.isExistInDB(username, password) == ERROR_PWD) { // �ж��˺��Ƿ����
			data.setCode(-1);
			data.setData(userBean);
			data.setMsg("���˺��Ѵ���");
		} else if (dbUtils.isExistInDB(username, password) == ERROR) {
			data.setCode(500);
			data.setData(userBean);
			data.setMsg("���ݿ��쳣");
		} else if (!dbUtils.insertDataToDB(username, password)) { // ע��ɹ�
			data.setCode(0);
			data.setMsg("ע��ɹ�����");
			ResultSet rs = dbUtils.getUser();
			int id = -1;
			if (rs != null) {
				try {
					while (rs.next()) {
						if (rs.getString("user_name").equals(username) && rs.getString("user_pwd").equals(password)) {
							id = rs.getInt("user_id");
						}
					}
					userBean.setId(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			userBean.setUsername(username);
			userBean.setPassword(password);
			data.setData(userBean);
		} else { // ע�᲻�ɹ����������û��ϸ�֣�����Ϊ���ݿ����
			data.setCode(500);
			data.setData(userBean);
			data.setMsg("���ݿ����");
		}
		Gson gson = new Gson();
		String json = gson.toJson(data); // ������ת����json�ַ���
		try {
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(json); // ��json���ݴ����ͻ���
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // �ر����������Ȼ�ᷢ�������
		}
		dbUtils.closeConnect(); // �ر����ݿ�����
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}