package servelt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.BaseBean;
import model.Comm;
import model.Order;
import util.DBUtils;

/**
 * Servlet implementation class FootManServlet
 */
@WebServlet("/CommentListServlet")
public class CommentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentListServlet() {
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
		String footId = request.getParameter("footId");

		/**
		 * ȥ���ݿ���в�ѯ����ȡ���ж�����Ϣ������ArrayList����
		 */
		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect(); // �����ݿ�����
		BaseBean data = new BaseBean(); // ������󣬻ش����ͻ��˵�json����
		ArrayList<Comm> commList = dbUtils.getCommList(footId);
		if (commList.size()>0) {
			data.setCode(1);
			data.setMsg("��ѯ�ɹ�");
			data.setCommList(commList);
		} else { // ע�᲻�ɹ����������û��ϸ�֣�����Ϊ���ݿ����
			data.setCode(0);
			data.setMsg("û������");
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