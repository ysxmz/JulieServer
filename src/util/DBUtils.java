package util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import model.Foot;

public class DBUtils {
	private Connection conn;
	private String url = "jdbc:mysql://127.0.0.1:3306/juliedb"; // 指定连接数据库的URL
	private String user = "root"; // 指定连接数据库的用户名
	private String password = "123456"; // 指定连接数据库的密码

	private Statement sta;
	private ResultSet rs;
	public static final int NAME_PWD = 1;
	public static final int ERROR_PWD = 2;
	public static final int NO_NAME = 3;
	public static final int ERROR = 4;

	// 打开数据库连接
	public void openConnect() {
		try {
			// 加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(url, user, password);// 创建数据库连接
			if (conn != null) {
				System.out.println("数据库连接成功"); // 连接成功的提示信息
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	// 获得查询user表后的数据集
	public ResultSet getUser() {
		// 创建 statement对象
		try {
			sta = (Statement) conn.createStatement();
			// 执行SQL查询语句
			rs = sta.executeQuery("select * from t_user");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 判断数据库中是否存在某个用户名及其密码,注册和登录的时候判断
	public int isExistInDB(String username, String password) {
		int isFlag = NO_NAME;
		// 创建 statement对象
		try {
			sta = (Statement) conn.createStatement();
			// 执行SQL查询语句
			rs = sta.executeQuery("select * from t_user");// 获得结果集
			if (rs != null) {
				while (rs.next()) { // 遍历结果集
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

	// 判断数据库中是否存在某个用户名,并且返回用户id,修改密码的时候判断
	public int isNameExistInDB(String username) {
		int id=-1;
		// 创建 statement对象
		try {
			sta = (Statement) conn.createStatement();
			// 执行SQL查询语句
			rs = sta.executeQuery("select * from t_user");// 获得结果集
			if (rs != null) {
				while (rs.next()) { // 遍历结果集
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

	// 注册 将用户名和密码插入到数据库(id设置的是自增长的，因此不需要插入)
	public boolean insertDataToDB(String username, String password) {
		String sql = " insert into t_user ( user_name , user_pwd ) values ( " + "'" + username + "', " + "'" + password
				+ "' )";
		try {
			sta = (Statement) conn.createStatement();
			// 执行SQL查询语句
			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 忘记密码 修改密码
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

	// 关闭数据库连接
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
			System.out.println("关闭数据库连接成功");
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/**
	 * 当有人发布订单后，向数据库插入订单信息
	 * @param foot
	 * @return
	 */
	public boolean insertFootToDB(Foot foot) {
		String sql = " insert into t_foot ( footId , userId , phone , name , address , content , reward ,state) values "
				+ "('" + foot.getFootId() + "', '" + foot.getUserId()+ "', '" +foot.getPhone()+ "', '" +foot.getName()+ "', '" +foot.getAddress()+ "', '" +foot.getContent()+ "', '" +foot.getReward()+ "', '" +foot.getState()
				+ "')";
		try {
			System.out.println(sql);
			sta = (Statement) conn.createStatement();
			
			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public String queryIfNewFoot(){
		String content ="null";
		String sql ="select content from t_foot where CreateTime >= now()-interval 5 minute order by CreateTime desc limit 1;";
		try {
			sta = (Statement) conn.createStatement();
			rs = sta.executeQuery(sql);// 获得结果集
			if (rs != null) {
				while(rs.next())
				return rs.getString("content");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return content;
	}

}
