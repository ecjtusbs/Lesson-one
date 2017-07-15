package mainUI.dbOP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * 负责返回单例的数据库连接
 * 建立连接 、关闭连接
 */
public class DBCon
{

	private static Connection con = null;

	private static final String USERNAME = "root";
	private static final String PASSWORD = "201314";
	// private static int times = 0;

	/*
	 * 获取单例数据库连接对象
	 */
	public static Connection getConnection()
	{

		if (con == null)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e)
			{
				System.out.println("找不到数据库驱动");
				e.printStackTrace();
			}

			String url = "jdbc:mysql://localhost:3306/test";

			try
			{
				con = DriverManager.getConnection(url, USERNAME, PASSWORD);
				// System.out.println("第" + (++times) + "次建立连接");
			} catch (SQLException e)
			{
				System.out.println("数据库连接失败");
				e.printStackTrace();
			}
		}

		return con;
	}

	/*
	 * 关闭数据库连接 建立连接的依据是connetcion==null 因此执行close方法后，设置con=null 防止再次插入时新建连接失败
	 */
	public static void closeConnetcion(PreparedStatement pre)
	{
		if (pre != null)
		{
			try
			{
				pre.close();
			} catch (SQLException e)
			{
				// System.out.println("数据库关闭失败");
				e.printStackTrace();
			}
		}

		if (con != null)
		{
			try
			{
				con.close();
				con = null;
				// System.out.println("第" + times + "次关闭连接");
			} catch (SQLException e)
			{
				// System.out.println("数据库关闭失败");
				e.printStackTrace();
			}
		}
	}

	public static void closeConnetcion()
	{

		if (con != null)
		{
			try
			{
				con.close();
				con = null;
				// System.out.println("第" + times + "次关闭连接");
			} catch (SQLException e)
			{
				System.out.println("数据库关闭失败");
				e.printStackTrace();
			}
		}
	}
}
