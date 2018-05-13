package servelt;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.BaseBean;
import model.Foot;
import model.UserBean;
import util.DBUtils;

/**
 * Servlet implementation class PubFootServlet
 */
@WebServlet("/PubFootServlet")
public class PubFootServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PubFootServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId"); // 获取客户端传过来的参数
		String content = request.getParameter("content");
		String address = request.getParameter("address");
		String reward = request.getParameter("reward");
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String addNeed=request.getParameter("addNeed");
		String udId=request.getParameter("udId");
		String totalMoney=request.getParameter("totalMoney");
		String payOnline=request.getParameter("payOnline");

		
		Foot foot =new Foot(Integer.valueOf(userId),phone,name,address,content,Double.valueOf(reward), Integer.valueOf(payOnline));

		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect(); // 打开数据库连接
		BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象

		if (!dbUtils.insertFootToDB(foot,addNeed,udId,totalMoney)){
			data.setCode(1);
			data.setMsg("发布成功");
		} else { // 不成功，这里错误没有细分，都归为数据库错误
			data.setCode(500);
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
