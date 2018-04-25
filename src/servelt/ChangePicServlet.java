package servelt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.jdbc.util.Base64Decoder;

import model.BaseBean;
import model.UserBean;
import sun.misc.BASE64Decoder;
import util.Base64Utils;
import util.DBUtils;

@WebServlet("/ChangePicServlet")
public class ChangePicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int NAME_PWD = 1;
	public static final int ERROR_PWD = 2;
	public static final int NO_NAME = 3;
	public static final int ERROR = 4;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePicServlet() {
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
		String userpicstring = request.getParameter("userpicstring");

		if (username == null || username.equals("") || userpicstring == null || userpicstring.equals("")) {
			System.out.println("�û�����ͼƬΪ��");
			return;
		}

		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect(); // �����ݿ�����
		BaseBean data = new BaseBean(); // ������󣬻ش����ͻ��˵�json����
		UserBean userBean = new UserBean(); // user�Ķ���

		String imageName = username + ".jpg";
		System.out.println(getServletContext().getRealPath("/images"));
		String path = getServletContext().getRealPath("/images/" + imageName);
		int id = dbUtils.isNameExistInDB(username);

		if (!Base64Utils.GenerateImage(userpicstring, path)) {
			data.setCode(-2);
			data.setData(data);
			data.setMsg("ͼƬ������");
		}
		if (id == -1) { // �ж��˺��Ƿ����
			data.setCode(-1);
			data.setData(userBean);
			data.setMsg("���˺Ų�����");
		} else if (!dbUtils.uploadPicToDB(username, imageName)) {
			data.setCode(1);
			data.setData(userBean);
			data.setMsg("�޸ĳɹ�");
		}else { // �޸Ĳ��ɹ����������û��ϸ�֣�����Ϊ���ݿ����
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
