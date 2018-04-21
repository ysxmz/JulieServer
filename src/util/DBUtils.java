package util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import model.Comm;
import model.Comment;
import model.Foot;
import model.Order;
import model.UserBean;

public class DBUtils {
	private Connection conn;
	private String url = "jdbc:mysql://127.0.0.1:3306/juliedb"; // ָ���������ݿ��URL
	private String user = "root"; // ָ���������ݿ���û���
	private String password = "123456"; // ָ���������ݿ������

	private Statement sta;
	private ResultSet rs;
	public static final int NAME_PWD = 1;
	public static final int ERROR_PWD = 2;
	public static final int NO_NAME = 3;
	public static final int ERROR = 4;

	// �����ݿ�����
	public void openConnect() {
		try {
			// �������ݿ�����
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(url, user, password);// �������ݿ�����
			if (conn != null) {
				System.out.println("���ݿ����ӳɹ�"); // ���ӳɹ�����ʾ��Ϣ
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	// ��ò�ѯuser�������ݼ�
	public ResultSet getUser() {
		// ���� statement����
		try {
			sta = (Statement) conn.createStatement();
			// ִ��SQL��ѯ���
			rs = sta.executeQuery("select * from t_user");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// �ж����ݿ����Ƿ����ĳ���û�����������,ע��͵�¼��ʱ���ж�
	public int isExistInDB(String username, String password) {
		int isFlag = NO_NAME;
		// ���� statement����
		try {
			sta = (Statement) conn.createStatement();
			// ִ��SQL��ѯ���
			rs = sta.executeQuery("select * from t_user");// ��ý����
			if (rs != null) {
				while (rs.next()) { // ���������
					if (rs.getString("user_name").equals(username)) {
						if (rs.getString("user_pwd").equals(password)) {
							isFlag = NAME_PWD;
							break;
						} else {
							isFlag = ERROR_PWD;
							break;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isFlag = ERROR;
		}
		return isFlag;
	}

	// �ж����ݿ����Ƿ����ĳ���û���,���ҷ����û�id,�޸������ʱ���ж�
	public int isNameExistInDB(String username) {
		int id = -1;
		// ���� statement����
		try {
			sta = (Statement) conn.createStatement();
			// ִ��SQL��ѯ���
			rs = sta.executeQuery("select * from t_user");// ��ý����
			if (rs != null) {
				while (rs.next()) { // ���������
					if (rs.getString("user_name").equals(username)) {
						return rs.getInt("user_id");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return id;
	}

	// ע�� ���û�����������뵽���ݿ�(id���õ����������ģ���˲���Ҫ����)
	public boolean insertDataToDB(String username, String password) {
		String sql = " insert into t_user ( user_name , user_pwd ) values ( " + "'" + username + "', " + "'" + password
				+ "' )";
		try {
			sta = (Statement) conn.createStatement();
			// ִ��SQL��ѯ���
			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// �������� �޸�����
	public boolean updatePwdToDB(int id, String password) {
		String sql = " update t_user set user_pwd= " + " '" + password + "' where user_id= " + id + ";";
		try {
			sta = (Statement) conn.createStatement();
			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// �ر����ݿ�����
	public void closeConnect() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (sta != null) {
				sta.close();
			}
			if (conn != null) {
				conn.close();
			}
			System.out.println("�ر����ݿ����ӳɹ�");
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * �����˷��������������ݿ���붩����Ϣ
	 * 
	 * @param foot
	 * @return
	 */
	public boolean insertFootToDB(Foot foot) {
		String sql = " insert into t_foot ( footId , userId , phone , name , address , content , reward ,state) values "
				+ "('" + foot.getFootId() + "', '" + foot.getUserId() + "', '" + foot.getPhone() + "', '"
				+ foot.getName() + "', '" + foot.getAddress() + "', '" + foot.getContent() + "', '" + foot.getReward()
				+ "', '" + foot.getState() + "')";
		try {
			System.out.println(sql);
			sta = (Statement) conn.createStatement();

			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * ��ѯ��������Ƿ����˷�������
	 * @return
	 */
	public String queryIfNewFoot() {
		String content = "null";
		String sql = "select content from t_foot where CreateTime >= now()-interval 5 minute order by CreateTime desc limit 1;";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// ��ý����
			if (rs != null) {
				while (rs.next())
					return rs.getString("content");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * ��ö����б�
	 * @return
	 */
	public ArrayList<Order> getOrderList() {
		ArrayList<Order> orderList = new ArrayList<>();
		String sql = "select * from t_user, t_foot where t_user.user_id=t_foot.userId order by createTime desc";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// ��ý����
			if (rs != null) {
				while (rs.next()) {
					Order o = new Order(rs.getString("footId"), rs.getString("user_picurl"),rs.getString("user_nickname"), rs.getString("state"), rs.getString("content"),
							rs.getString("address"), rs.getString("reward"), rs.getTimestamp("CreateTime").toString(),rs.getString("phone"));
					orderList.add(o);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orderList;
	}

	/**
	 * �����˷������ۺ������ݿ���붩����Ϣ
	 * @param c
	 * @return
	 */
	public boolean insertCommentToDB(Comment c) {
		String s ="select count(footId) as count from t_comment where footId="+c.getFootId();
		int count=1;
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(s);// ��ý����
			if (rs != null) {
				while (rs.next())
					count= rs.getInt("count")+1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql = " insert into t_comment ( footId , userId , comment ,floor ) values "
				+ "('" + c.getFootId() + "', " + c.getUserId() + ", '" + c.getComment()  + "', " + count
				 + ")";
		try {
			System.out.println(sql);
			sta = (Statement) conn.createStatement();
			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * ��ѯָ��������������Ϣ
	 * @return
	 */
	public ArrayList<Comm> getCommList(String footId) {
		ArrayList<Comm> cList = new ArrayList<>();
		String sql = "select * from t_user, t_comment where t_user.user_id=t_comment.userId and t_comment.footId='"+footId+"'";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// ��ý����
			if (rs != null) {
				while (rs.next()) {
					Comm c = new Comm(rs.getString("footId"), rs.getInt("user_id"),rs.getString("user_picurl"),rs.getString("user_nickname"), rs.getInt("floor"), rs.getString("comment"),rs.getTimestamp("CreateTime").toString());
					cList.add(c);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cList;
	}

	/**
	 * ������������¶���״̬2,���ӽӵ���id
	 * @param footId
	 * @param userId
	 * @param state
	 * @return
	 */
	public boolean updateFootFromDB(String footId, int userId, int state) {
		if(userId==0){
			String sql = " update t_foot set state= " + " " + state +" where footId= '" + footId + "';";
			try {
				sta = (Statement) conn.createStatement();
				return sta.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}else{
			String sql = " update t_foot set state= " + " " + state + ",receive_userId ="+userId+" where footId= '" + footId + "';";
			try {
				sta = (Statement) conn.createStatement();
				return sta.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		
	}

	/**
	 * ����ָ�������ķ����˵绰
	 * @param string
	 * @return
	 */
	public String getFootPhone(String footId) {
		String s ="select phone from t_foot where footId='"+footId+"'";
		String phone="";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(s);// ��ý����
			if (rs != null) {
				while (rs.next())
					phone= rs.getString("phone");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return phone;
	}

	/**
	 * ��ȡ�ӵ�����Ϣ
	 * @param footId
	 * @return
	 */
	public UserBean getReceiveFromDB(String footId) {
		// TODO Auto-generated method stub
		UserBean user=new UserBean();
		int userId=0;
		String s ="select receive_userId from t_foot where footId='"+footId+"'";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(s);// ��ý����
			if (rs != null) {
				while (rs.next())
					userId= rs.getInt("receive_userId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql ="select * from t_user where user_id='"+userId+"'";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// ��ý����
			if (rs != null) {
				while (rs.next()){
					user.setNickname(rs.getString("user_nickname"));
					user.setUserpicUrl(rs.getString("user_picurl"));
					user.setUsername(rs.getString("user_name"));
					user.setId(rs.getInt("user_id"));
					return user;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
