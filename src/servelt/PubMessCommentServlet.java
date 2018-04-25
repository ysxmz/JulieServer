package servelt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BaseBean;
import model.Comment;
import util.DBUtils;

import com.google.gson.Gson;

/**
 * Servlet implementation class PubMessCommentServlet
 */
@WebServlet("/PubMessCommentServlet")
public class PubMessCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PubMessCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId"); // ��ȡ�ͻ��˴������Ĳ���
		String comment = request.getParameter("comment");
		String messId = request.getParameter("messId");
		Comment c=new Comment(messId,Integer.valueOf(userId),comment);

		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect(); // �����ݿ�����
		BaseBean data = new BaseBean(); // ������󣬻ش����ͻ��˵�json����

		if (!dbUtils.insertMessCommentToDB(c)){
			data.setCode(1);
			data.setMsg("���۳ɹ�");
		} else { // ���ɹ����������û��ϸ�֣�����Ϊ���ݿ����
			data.setCode(500);
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
