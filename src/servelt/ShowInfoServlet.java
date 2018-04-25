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
import model.InfoShow;
import util.DBUtils;

/**
 * Servlet implementation class ShowInfoServlet
 */
@WebServlet("/ShowInfoServlet")
public class ShowInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	response.setContentType("text/html;charset=utf-8");
	String username = request.getParameter("username");
	
	DBUtils dbUtils = new DBUtils();
	dbUtils.openConnect();
	BaseBean data = new BaseBean();
	ArrayList<InfoShow> uiList = dbUtils.getUserInfoList(username);
	if(uiList.size()>0) {
		data.setCode(1);
		data.setMsg("��ѯ�ɹ�");
		data.setInfoList(uiList);
	}else {
		data.setCode(0);
		data.setMsg("û����Ϣ");
	}
	Gson gson = new Gson();
	String json = gson.toJson(data);
	try {
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(json); // ��json���ݴ����ͻ���
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		response.getWriter().close(); // �ر����������Ȼ�ᷢ�������
	}
	dbUtils.closeConnect(); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
