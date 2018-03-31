package util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

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
		int id=-1;
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

}
