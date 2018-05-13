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
		String wallet = request.getParameter("wallet"); // ��ȡ�ͻ��˴������Ĳ���
		String operation = request.getParameter("operation");
		String account = request.getParameter("account");
		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect(); // �����ݿ�����
		BaseBean data = new BaseBean(); // ������󣬻ش����ͻ��˵�json����
		
		if (operation.equals("-")) {
			WithDrawUtils withdraw = new WithDrawUtils();
			boolean flag = withdraw.moneyOut(withdraw.createId(), account, wallet, "����������");
			if (flag) {
				if (!dbUtils.changeMoney(userId, wallet, operation)) {
					data.setCode(1);
					data.setMsg("���ֳɹ�");
				} else { // �޸Ĳ��ɹ����������û��ϸ�֣�����Ϊ���ݿ����
					data.setCode(500);
					data.setMsg("���ݿ����");
				}
			} else {
				data.setCode(3);
				data.setMsg("����ʧ��");
			}
		}else{
			if (!dbUtils.changeMoney(userId, wallet, operation)) {
				data.setCode(1);
				data.setMsg("��ֵ�ɹ�");
			} else { // �޸Ĳ��ɹ����������û��ϸ�֣�����Ϊ���ݿ����
				data.setCode(500);
				data.setMsg("���ݿ����");
			}
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
