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

	//数据库操作是否成功
	private static final boolean DBOP_SUCCESS = true;
	private static final boolean DBOP_FAIL = false;

	
	/**
	* @Title: addUser
	* @Description: TODO 添加Record至数据库
	* @param     record 一条记录
	* @return boolean    是否插入成功
	*/
	public static boolean addUser(Record record)
	{
		
		Connection con = DBCon.getConnection();
		PreparedStatement preStat = null;

		try
		{
			
			//首先判断是否存在相同的记录
			String preCheck = "select username from admins where website=?";

			preStat = con.prepareStatement(preCheck);
			preStat.setString(1, record.getWebsite());

			ResultSet rs = preStat.executeQuery();
			
			rs.last();

			int count = rs.getRow();

			if (count != 0)
			{
				System.out.println("记录已存在");
				
				preStat.close();
				return DBOP_FAIL;
			}

			// 记录不存在，进行插入操作
			
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
//			System.out.println("--------------->数据库插入操作异常");
			e.printStackTrace();
		} finally
		{
			DBCon.closeConnetcion(preStat);
		}
		return DBOP_FAIL;
	}

	
	/**
	* @Title: delRec
	* @Description: TODO删除指定的记录
	* @param     rec 待删除的记录
	* @return boolean    是否成功
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
	* @Description: TODO 按数组形式批量删除记录
	* @param     records 待删除的记录们 
	* @return boolean    返回类型
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
	* @Description: TODO 查询数据库返回查询结果
	* @param     colName列名  data 查询到的数据
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

			//获取表头
			for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
			{
				colName.addElement(rsMetaData.getColumnName(i));
			}

			
			/*获取记录，每条记录是一个Vector<String>
			全部的记录用Vector<Vector<String>>来保存*/
			
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
