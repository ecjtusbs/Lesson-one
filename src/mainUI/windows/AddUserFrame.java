package mainUI.windows;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import mainUI.dao.RecordDAO;
import mainUI.entity.Record;
import mainUI.utils.UIUtils;

public class AddUserFrame extends JFrame implements ActionListener
{

	/**
	 * @serialVersionUID:TODO
	 */
	private static final long serialVersionUID = 2L;
	
	private JTextField website;
	private JTextField username;
	private JTextField password;

	private Container container = null;

	private static final int FRAME_WIDTH = 320;
	private static final int FRAME_HEIGHT = 240;

	private MainFrame mf = null;

	public AddUserFrame(String title)
	{
		super(title);

		initGUI();
	}

	
//	此Frame要通知主Frame更新视图，因此需要保留主Frame
//	的引用
	
	public void setParent(MainFrame mf)
	{
		this.mf = mf;
	}

	/**
	* @Title: initGUI
	* @Description: 初始化界面
	* @param    
	* @return void   
	* 
	*/
	private void initGUI()
	{
		int screenWidth = UIUtils.getScreenWidth();
		int screenHeight = UIUtils.getScreenHeight();

		setBounds((screenWidth - FRAME_WIDTH) / 2, (screenHeight - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);

		//单击退出时隐藏此窗体
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		container = getContentPane();
		container.setLayout(null);

		website = new JTextField();
		website.setBounds(FRAME_WIDTH / 2, 30, 90, 20);
		website.setColumns(10);
		container.add(website);

		username = new JTextField();
		username.setBounds(FRAME_WIDTH / 2, 70, 90, 20);
		username.setColumns(10);
		container.add(username);

		password = new JTextField();
		password.setBounds(FRAME_WIDTH / 2, 110, 90, 20);
		password.setColumns(10);
		container.add(password);

		JLabel lbl_website = new JLabel("Website");
		lbl_website.setBounds(FRAME_WIDTH / 2 - 70, 30, 70, 15);
		container.add(lbl_website);

		JLabel lbl_username = new JLabel("Username");
		lbl_username.setBounds(FRAME_WIDTH / 2 - 70, 70, 70, 15);
		container.add(lbl_username);

		JLabel lbl_password = new JLabel("Password");
		lbl_password.setBounds(FRAME_WIDTH / 2 - 70, 110, 70, 15);
		container.add(lbl_password);

		JButton btn_add = new JButton("Add");
		btn_add.setBounds(FRAME_WIDTH / 2 - 35, FRAME_HEIGHT - 70, 70, 25);
		container.add(btn_add);

		btn_add.addActionListener(this);

	}

	/**
	* @Title: actionPerformed
	* @Description:  获取输入数据，封装，插入到数据库中，更新表格视图
	* @param    
	* @return void   
	* 
	*/
	@Override
	public void actionPerformed(ActionEvent e)
	{

		String webName = website.getText();
		String name = username.getText();
		String pwd = password.getText();

		if (webName.equals("") || name.equals("") || pwd.equals(""))
		{
			JOptionPane.showMessageDialog(this, "网站名、用户名和密码均不能为空");

		} else
		{
			
			Record rec = new Record(webName, name, pwd);
			if (RecordDAO.addUser(rec))
			{
				//通知更新表格视图
				mf.updateAfterAdd(rec);
				
				website.setText("");
				username.setText("");
				password.setText("");
				
				JOptionPane.showMessageDialog(this, "成功插入记录");

			} else
			{
				JOptionPane.showMessageDialog(this, "插入记录失败");
			}
		}
	}
}
