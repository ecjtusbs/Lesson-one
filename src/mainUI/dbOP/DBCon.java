package mainUI.dbOP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * ���𷵻ص��������ݿ�����
 * �������� ���ر�����
 */
public class DBCon
{

	private static Connection con = null;

	private static final String USERNAME = "root";
	private static final String PASSWORD = "201314";
	// private static int times = 0;

	/*
	 * ��ȡ�������ݿ����Ӷ���
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
				System.out.println("�Ҳ������ݿ�����");
				e.printStackTrace();
			}

			String url = "jdbc:mysql://localhost:3306/test";

			try
			{
				con = DriverManager.getConnection(url, USERNAME, PASSWORD);
				// System.out.println("��" + (++times) + "�ν�������");
			} catch (SQLException e)
			{
				System.out.println("���ݿ�����ʧ��");
				e.printStackTrace();
			}
		}

		return con;
	}

	/*
	 * �ر����ݿ����� �������ӵ�������connetcion==null ���ִ��close����������con=null ��ֹ�ٴβ���ʱ�½�����ʧ��
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
				// System.out.println("���ݿ�ر�ʧ��");
				e.printStackTrace();
			}
		}

		if (con != null)
		{
			try
			{
				con.close();
				con = null;
				// System.out.println("��" + times + "�ιر�����");
			} catch (SQLException e)
			{
				// System.out.println("���ݿ�ر�ʧ��");
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
				// System.out.println("��" + times + "�ιر�����");
			} catch (SQLException e)
			{
				System.out.println("���ݿ�ر�ʧ��");
				e.printStackTrace();
			}
		}
	}
}
