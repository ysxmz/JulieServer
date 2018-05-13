package servelt;

import com.google.gson.Gson;

import model.BaseBean;
import model.UserBean;
import util.DBUtils;
import util.WithDrawUtils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/ChangeMoneyServlet")
public class ChangeMoneyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int NAME_PWD = 1;
	public static final int ERROR_PWD = 2;
	public static final int NO_NAME = 3;
	public static final int ERROR = 4;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeMoneyServlet() {
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
		String userId = request.getParameter("userId");
		String wallet = request.getParameter("wallet"); // 获取客户端传过来的参数
		String operation = request.getParameter("operation");
		String account = request.getParameter("account");
		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect(); // 打开数据库连接
		BaseBean data = new BaseBean(); // 基类对象，回传给客户端的json对象
		
		if (operation.equals("-")) {
			WithDrawUtils withdraw = new WithDrawUtils();
			boolean flag = withdraw.moneyOut(withdraw.createId(), account, wallet, "交立达提现");
			if (flag) {
				if (!dbUtils.changeMoney(userId, wallet, operation)) {
					data.setCode(1);
					data.setMsg("提现成功");
				} else { // 修改不成功，这里错误没有细分，都归为数据库错误
					data.setCode(500);
					data.setMsg("数据库错误");
				}
			} else {
				data.setCode(3);
				data.setMsg("提现失败");
			}
		}else{
			if (!dbUtils.changeMoney(userId, wallet, operation)) {
				data.setCode(1);
				data.setMsg("充值成功");
			} else { // 修改不成功，这里错误没有细分，都归为数据库错误
				data.setCode(500);
				data.setMsg("数据库错误");
			}
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
