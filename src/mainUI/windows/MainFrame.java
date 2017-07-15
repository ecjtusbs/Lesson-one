package mainUI.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mainUI.dao.RecordDAO;
import mainUI.entity.Record;
import mainUI.uicomponent.TableModel;
//import mainUI.uicomponent.TableModel;
import mainUI.utils.UIUtils;

public class MainFrame extends JFrame implements ActionListener
{

	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 400;

	/**
	 * @serialVersionUID:TODO
	 */
	private static final long serialVersionUID = 3L;

	// 查询和添加记录按钮
	private JButton jbt_del;
	private JButton jbt_addNew;

	// 展示记录的表格
	public JTable table = null;
	private TableModel tmModel = null;

	// 新增记录界面
	private AddUserFrame addUserFrame;

	public MainFrame(String title)
	{
		super(title);
	}

	public static void main(String[] args)
	{

		MainFrame mainframe = new MainFrame("密码本");
		mainframe.init();

		// 设置addUserFrame对象中记录此窗体的引用
		mainframe.addUserFrame.setParent(mainframe);

		// 查询记录
		mainframe.queryAll();

		mainframe.setVisible(true);
	}

	/**
	* @Title: init
	* @Description: 初始化GUI界面，设置监听器
	* @param    
	* @return void   
	* 
	*/
	public void init()
	{
		// 主窗体居中
		int screenWidth = UIUtils.getScreenWidth();
		int screenHeight = UIUtils.getScreenHeight();

		setBounds((screenWidth - FRAME_WIDTH) / 2, (screenHeight - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 设置绝对布局
		getContentPane().setLayout(null);

		jbt_del = new JButton("Delete");
		jbt_del.setBounds((FRAME_WIDTH) / 2 - 110, FRAME_HEIGHT - 70, 100, 25);
		jbt_del.addActionListener(this);
		getContentPane().add(jbt_del);

		jbt_addNew = new JButton("Add New");
		jbt_addNew.setBounds(FRAME_WIDTH / 2 + 10, FRAME_HEIGHT - 70, 100, 25);
		jbt_addNew.addActionListener(this);
		getContentPane().add(jbt_addNew);

		// 新增记录 面板
		addUserFrame = new AddUserFrame("Add user");
		addUserFrame.setVisible(false);
	}

	/**
	* @Title: queryAll
	* @Description: 查询记录
	* @param    
	* @return void   
	* 
	*/
	public void queryAll()
	{

		Vector<String> colname = new Vector<String>();
		Vector<Vector<String>> data = new Vector<Vector<String>>();

		// 执行查询操作
		RecordDAO.queryAll(colname, data);

		tmModel = new TableModel();
		tmModel.setDataVector(data, colname);

		table = new JTable(tmModel);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT - 100);
		getContentPane().add(scrollPane);

	}

	/**
	* @Title: updateAfterAdd
	* @Description: 添加记录后更新表格视图
	* @param    Record rec 添加的记录
	* @return void  
	* 
	*/
	public void updateAfterAdd(Record rec)
	{
		Vector<String> data = new Vector<String>();

		data.add(rec.getWebsite());
		data.add(rec.getUsername());
		data.add(rec.getPassword());

		int oldCount = tmModel.getRowCount();

		tmModel.insertRow(tmModel.getRowCount(), data);
		tmModel.setRowCount(oldCount + 1);
	}

	/**
	* @Title: delTableUpdate
	* @Description: TODO 删除记录后更新表格视图
	* @param    indexs 待删除记录的索引数组
	* @return void   
	* 
	*/
	public void updateAfterDel(int[] indexs)
	{

		// indexs 从小到大排列，因每次删除重新调整表格大小 ，为避免索引值越界，故从后往前删除

		for (int i = indexs.length; i > 0; i--)
		{
			// 原始大小
			int origin = tmModel.getRowCount();

			// 删除一行
			tmModel.removeRow(indexs[i - 1]);

			// 调整大小
			tmModel.setRowCount(origin - 1);
		}
	}

	/**
	 * 响应查询和新增按钮点击 实现查询和新增记录功能
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{

		if (((JButton) e.getSource()) == jbt_addNew)
		{
			// 显示新增记录对话框
			addUserFrame.setVisible(true);

		} else if (((JButton) e.getSource()) == jbt_del)
		{
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Sure to delete this record?", "Confirm",
					JOptionPane.YES_NO_OPTION))
			{

				//选中记录的索引值
				int[] rowsDel = table.getSelectedRows();

				if (rowsDel.length != 0)
				{
					Record[] records = new Record[rowsDel.length];

					for (int i = 0; i < rowsDel.length; i++)
					{
						// 获取选中记录的website值构建Record对象
						records[i] = new Record((String) table.getValueAt(rowsDel[i], 0));
					}

//					删除选中的记录
					RecordDAO.delRec(records);

//					更新表格视图
					updateAfterDel(rowsDel);

				}
			}
		}
	}
}
