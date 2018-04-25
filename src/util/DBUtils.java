package util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import model.Comm;
import model.Comment;
import model.Discount;
import model.Foot;
import model.InfoShow;
import model.Mess;
import model.MessShow;
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

	public UserBean getUserFromDB(String username, String password2) {
		UserBean userBean = new UserBean();
		try {
			sta = (Statement) conn.createStatement();
			// ִ��SQL��ѯ���
			rs = sta.executeQuery("select * from t_user");// ��ý����
			if (rs != null) {
				while (rs.next()) { // ���������
					if (rs.getString("user_name").equals(username)) {
						if (rs.getString("user_pwd").equals(password2)) {
							userBean.setId(rs.getInt("user_id"));
							userBean.setNickname(rs.getString("user_nickname"));
							userBean.setPassword(rs.getString("user_pwd"));
							userBean.setUsername(rs.getString("user_name"));
							userBean.setUserpicUrl(rs.getString("user_picurl"));
							userBean.setDescribe(rs.getString("user_describe"));
							userBean.setLocation(rs.getString("user_location"));
							userBean.setSex(rs.getString("user_sex"));
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userBean;
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
		String sql = " insert into t_user ( user_name , user_pwd ,user_nickname) values ( " + "'" + username + "', " + "'" + password+ "','" + username
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
	 * 
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
	 * 
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
					Order o = new Order(rs.getString("footId"), rs.getInt("userId"), rs.getInt("receive_userId"),
							rs.getString("user_picurl"), rs.getString("user_nickname"), rs.getString("state"),
							rs.getString("content"), rs.getString("address"), rs.getString("reward"),
							rs.getTimestamp("CreateTime").toString(), rs.getString("phone"));
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
	 * 
	 * @param c
	 * @return
	 */
	public boolean insertCommentToDB(Comment c) {
		String s = "select count(footId) as count from t_comment where footId=" + c.getFootId();
		int count = 1;
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(s);// ��ý����
			if (rs != null) {
				while (rs.next())
					count = rs.getInt("count") + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql = " insert into t_comment ( footId , userId , comment ,floor ) values " + "('" + c.getFootId()
				+ "', " + c.getUserId() + ", '" + c.getComment() + "', " + count + ")";
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
	 * 
	 * @return
	 */
	public ArrayList<Comm> getCommList(String footId) {
		ArrayList<Comm> cList = new ArrayList<>();
		String sql = "select * from t_user, t_comment where t_user.user_id=t_comment.userId and t_comment.footId='"
				+ footId + "'";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// ��ý����
			if (rs != null) {
				while (rs.next()) {
					Comm c = new Comm(rs.getString("footId"), rs.getInt("user_id"), rs.getString("user_picurl"),
							rs.getString("user_nickname"), rs.getInt("floor"), rs.getString("comment"),
							rs.getTimestamp("CreateTime").toString());
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
	 * 
	 * @param footId
	 * @param userId
	 * @param state
	 * @return
	 */
	public boolean updateFootFromDB(String footId, int userId, int state) {
		if (userId == 0) {
			String sql = " update t_foot set state= " + " " + state + " where footId= '" + footId + "';";
			try {
				sta = (Statement) conn.createStatement();
				return sta.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		} else {
			String sql = " update t_foot set state= " + " " + state + ",receive_userId =" + userId + " where footId= '"
					+ footId + "';";
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
	 * 
	 * @param string
	 * @return
	 */
	public String getFootPhone(String footId) {
		String s = "select phone from t_foot where footId='" + footId + "'";
		String phone = "";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(s);// ��ý����
			if (rs != null) {
				while (rs.next())
					phone = rs.getString("phone");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return phone;
	}

	/**
	 * ��ȡ�ӵ�����Ϣ
	 * 
	 * @param footId
	 * @return
	 */
	public UserBean getReceiveFromDB(String footId) {
		// TODO Auto-generated method stub
		UserBean user = new UserBean();
		int userId = 0;
		String s = "select receive_userId from t_foot where footId='" + footId + "'";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(s);// ��ý����
			if (rs != null) {
				while (rs.next())
					userId = rs.getInt("receive_userId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql = "select * from t_user where user_id='" + userId + "'";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// ��ý����
			if (rs != null) {
				while (rs.next()) {
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

	public ArrayList<Discount> getDiscountList(String userId) {
		ArrayList<Discount> discountList = new ArrayList<Discount>();
		String userDiscount = null;
		String sql = "select user_discount from t_user where user_id=" + userId;
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// ��ý����
			if (rs != null) {
				while (rs.next()) {
					userDiscount = rs.getString("user_discount");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (userDiscount == null) {
			return null;
		}
		String[] as = userDiscount.split(",");
		if (as.length == 0) {
			return null;
		} else if (as[0].equals("NULL")) {
			return null;
		} else {
			for (int i = 0; i < as.length; i++) {
				String s = "select * from t_discount where d_id=" + as[i];
				try {
					sta = (Statement) conn.createStatement();
					rs = sta.executeQuery(s);// ��ý����
					if (rs != null) {
						while (rs.next()) {
							Discount discount = new Discount(rs.getInt("d_id"), rs.getString("d_name"),
									rs.getString("d_money"), rs.getString("d_usable"), rs.getString("d_deadline"),
									rs.getString("d_limit"));
							discountList.add(discount);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return discountList;

		}
	}

	/**
	 * ��ѯָ����Ϣ��������Ϣ
	 * 
	 * @return
	 */
	public ArrayList<Comm> getMessCommList(String messId) {
		ArrayList<Comm> cList = new ArrayList<>();
		String sql = "select * from t_user, t_mess_comment where t_user.user_id=t_mess_comment.userId and t_mess_comment.messId='"
				+ messId + "'";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// ��ý����
			if (rs != null) {
				while (rs.next()) {
					Comm c = new Comm(rs.getString("messId"), rs.getInt("user_id"), rs.getString("user_picurl"),
							rs.getString("user_nickname"), rs.getInt("floor"), rs.getString("comment"),
							rs.getTimestamp("CreateTime").toString());
					cList.add(c);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cList;
	}

	/**
	 * �����˷������ۺ������ݿ������Ϣ��Ϣ
	 * 
	 * @param c
	 * @return
	 */
	public boolean insertMessCommentToDB(Comment c) {
		String s = "select count(messId) as count from t_mess_comment where messId=" + c.getFootId();
		int count = 1;
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(s);// ��ý����
			if (rs != null) {
				while (rs.next())
					count = rs.getInt("count") + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql = " insert into t_mess_comment ( messId , userId , comment ,floor ) values " + "('" + c.getFootId()
				+ "', " + c.getUserId() + ", '" + c.getComment() + "', " + count + ")";
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
	 * �����Ϣ�б�
	 * 
	 * @return
	 */
	public ArrayList<MessShow> getMessList() {
		ArrayList<MessShow> messList = new ArrayList<>();
		String sql = "select * from t_user, t_mess where t_user.user_id=t_mess.userId order by createTime desc";

		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// ��ý����

			if (rs != null) {
				while (rs.next()) {

					String messId = rs.getString("messId");
					// String sql2="select count(*) as commentNum from
					// t_mess_comment where messId="+messId;
					// ResultSet rs2=sta.executeQuery(sql2);// ��ý����
					// MessShow o = new
					// MessShow(messId,rs.getString("phone"),rs.getString("name"),
					// rs.getString("content"),rs.getString("wechat"),rs.getString("user_picurl")
					// ,
					// rs.getTimestamp("CreateTime").toString(),rs2.getInt("commentNum"));
					MessShow o = new MessShow(messId, rs.getString("phone"), rs.getString("name"),
							rs.getString("content"), rs.getString("wechat"), rs.getString("user_picurl"),
							rs.getTimestamp("CreateTime").toString());
					messList.add(o);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return messList;
	}

	/**
	 * �����˷�����Ϣ�������ݿ������Ϣ��Ϣ
	 * 
	 * @param mess
	 * @return
	 */
	public boolean insertMessToDB(Mess mess) {
		String sql = " insert into t_mess ( messId , userId , phone , name ,   wechat , content ) values " + "('"
				+ mess.getMessId() + "', '" + mess.getUserId() + "', '" + mess.getPhone() + "', '" + mess.getName()
				+ "', '" + mess.getWechat() + "', '" + mess.getContent() + "')";
		try {
			System.out.println(sql);
			sta = (Statement) conn.createStatement();

			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public Integer getMessNum(String messId) {
		String sql = "select count(*) as commentNum from t_mess_comment where messId='" + messId + "'";

		Integer num = 0;
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// ��ý����

			// num=rs.next().getInt("commentNum");
			if (rs != null) {
				while (rs.next()) {

					num = rs.getInt("commentNum");
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return num;
	}

	// �ϴ�ͼƬ
	public boolean uploadPicToDB(String username, String imageName) {
		String imagePath = "http://39.107.225.80:8080/images/" + imageName;
		String sql = "update t_user set user_picurl='" + imagePath + "' where user_name ='" + username + "';";
		try {
			System.out.println(sql);
			sta = (Statement) conn.createStatement();

			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	// ��ȡ�û���Ϣ
	public ArrayList<InfoShow> getUserInfoList(String username) {
		ArrayList<InfoShow> uiList = new ArrayList<>();
		String sql = "select username,user_sex,user_location,user_describe,user_nickname from t_user where user_name='"
				+ username + "'" + ";";

		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// ��ý����

			if (rs != null) {
				InfoShow o = new InfoShow(rs.getString("user_name"), rs.getString("user_sex"),
						rs.getString("user_location"), rs.getString("user_discribe"), rs.getString("user_nickname"));
				uiList.add(o);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return uiList;
	}

	// �����û���Ϣ
	public boolean uploadInfoToDB(String userId, String usersex, String userlocation, String userdescribe,
			String nickname) {
		String sql = "update t_user set user_sex='" + usersex + "',user_location='" + userlocation + "',user_describe='"
				+ userdescribe + "'," + "user_nickname='" + nickname + "' where user_id=" + userId + ";";
		try {
			System.out.println(sql);
			sta = (Statement) conn.createStatement();

			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

}
