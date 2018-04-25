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
		String username = request.getParameter("username"); // 获取客户端传过来的参数
		String userpicstring = request.getParameter("userpicstring");

		if (username == null || username.equals("") || userpicstring == null || userpicstring.equals("")) {
			System.out.println("用户名或图片为空");
			return;
		}

		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect(); // 打开数据库连接
		BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
		UserBean userBean = new UserBean(); // user的对象

		String imageName = username + ".jpg";
		System.out.println(getServletContext().getRealPath("/images"));
		String path = getServletContext().getRealPath("/images/" + imageName);
		int id = dbUtils.isNameExistInDB(username);

		if (!Base64Utils.GenerateImage(userpicstring, path)) {
			data.setCode(-2);
			data.setData(data);
			data.setMsg("图片出错了");
		}
		if (id == -1) { // 判断账号是否存在
			data.setCode(-1);
			data.setData(userBean);
			data.setMsg("该账号不存在");
		} else if (!dbUtils.uploadPicToDB(username, imageName)) {
			data.setCode(1);
			data.setData(userBean);
			data.setMsg("修改成功");
		}else { // 修改不成功，这里错误没有细分，都归为数据库错误
			data.setCode(500);
			data.setData(userBean);
			data.setMsg("数据库错误");
		}

		Gson gson = new Gson();
		String json = gson.toJson(data); // 将对象转化成json字符串
		try {
			response.setHeader("Content-Type", "text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(json); // 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // 关闭这个流，不然会发生错误的
		}
		dbUtils.closeConnect(); // 关闭数据库连接
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
