package mainUI.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import mainUI.dbOP.DBCon;
import mainUI.entity.Record;


/**
 * @author ckw
 *
 */

public class RecordDAO
{

	//���ݿ�����Ƿ�ɹ�
	private static final boolean DBOP_SUCCESS = true;
	private static final boolean DBOP_FAIL = false;

	
	/**
	* @Title: addUser
	* @Description: TODO ���Record�����ݿ�
	* @param     record һ����¼
	* @return boolean    �Ƿ����ɹ�
	*/
	public static boolean addUser(Record record)
	{
		
		Connection con = DBCon.getConnection();
		PreparedStatement preStat = null;

		try
		{
			
			//�����ж��Ƿ������ͬ�ļ�¼
			String preCheck = "select username from admins where website=?";

			preStat = con.prepareStatement(preCheck);
			preStat.setString(1, record.getWebsite());

			ResultSet rs = preStat.executeQuery();
			
			rs.last();

			int count = rs.getRow();

			if (count != 0)
			{
				System.out.println("��¼�Ѵ���");
				
				preStat.close();
				return DBOP_FAIL;
			}

			// ��¼�����ڣ����в������
			
			preStat.close();

			String sql = "insert into admins(website,username,password) values(?,?,?)";

			preStat = con.prepareStatement(sql);
			preStat.setString(1, record.getWebsite());
			preStat.setString(2, record.getUsername());
			preStat.setString(3, record.getPassword());

			int resultCode = preStat.executeUpdate();
			
			
			if (resultCode > 0)
			{
				return DBOP_SUCCESS;
			}
		} catch (Exception e)
		{
//			System.out.println("--------------->���ݿ��������쳣");
			e.printStackTrace();
		} finally
		{
			DBCon.closeConnetcion(preStat);
		}
		return DBOP_FAIL;
	}

	
	/**
	* @Title: delRec
	* @Description: TODOɾ��ָ���ļ�¼
	* @param     rec ��ɾ���ļ�¼
	* @return boolean    �Ƿ�ɹ�
	* @throws
	*/
	public static boolean delRec(Record rec)
	{

		Connection con = DBCon.getConnection();
		PreparedStatement preStat = null;

		String sql = "delete from admins where website=?";
		try
		{
			preStat = con.prepareStatement(sql);
			preStat.setString(1, rec.getWebsite());

			preStat.executeUpdate();

			return DBOP_SUCCESS;

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DBOP_FAIL;
		} finally
		{
			DBCon.closeConnetcion(preStat);
		}
	}
	
	

	/**
	* @Title: delRec
	* @Description: TODO ��������ʽ����ɾ����¼
	* @param     records ��ɾ���ļ�¼�� 
	* @return boolean    ��������
	* @throws
	*/
	public static boolean delRec(Record[] records)
	{

		Connection con = DBCon.getConnection();
		PreparedStatement preStat = null;

		String sql = "delete from admins where website=?";

		try
		{
			for (int i = 0; i < records.length; i++)
			{

				preStat = con.prepareStatement(sql);
				preStat.setString(1, records[i].getWebsite());

				preStat.executeUpdate();
			}

			return DBOP_SUCCESS;

		} catch (SQLException e)
		{
			e.printStackTrace();
			return DBOP_FAIL;
			
		} finally
		{
			DBCon.closeConnetcion(preStat);
		}
	}


	/**
	* @Title: queryAll
	* @Description: TODO ��ѯ���ݿⷵ�ز�ѯ���
	* @param     colName����  data ��ѯ��������
	* @return void   
	*/
	
	public static void queryAll(Vector<String> colName, Vector<Vector<String>> data)
	{
		// System.out.println("queryAll()");
		String sql = "select website,username,password from admins";

		Connection con = DBCon.getConnection();
		Statement state;
		ResultSet rs;
		ResultSetMetaData rsMetaData;

		try
		{
			state = con.createStatement();
			rs = state.executeQuery(sql);
			rsMetaData = rs.getMetaData();

			//��ȡ��ͷ
			for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
			{
				colName.addElement(rsMetaData.getColumnName(i));
			}

			
			/*��ȡ��¼��ÿ����¼��һ��Vector<String>
			ȫ���ļ�¼��Vector<Vector<String>>������*/
			
			while (rs.next())
			{

				Vector<String> rec = new Vector<String>();

				for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
				{
					rec.addElement(rs.getString(i));
				}

				data.addElement(rec);
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			DBCon.closeConnetcion();
		}
	}
}
