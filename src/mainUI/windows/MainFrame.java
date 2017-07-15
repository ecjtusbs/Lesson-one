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

	// ��ѯ����Ӽ�¼��ť
	private JButton jbt_del;
	private JButton jbt_addNew;

	// չʾ��¼�ı��
	public JTable table = null;
	private TableModel tmModel = null;

	// ������¼����
	private AddUserFrame addUserFrame;

	public MainFrame(String title)
	{
		super(title);
	}

	public static void main(String[] args)
	{

		MainFrame mainframe = new MainFrame("���뱾");
		mainframe.init();

		// ����addUserFrame�����м�¼�˴��������
		mainframe.addUserFrame.setParent(mainframe);

		// ��ѯ��¼
		mainframe.queryAll();

		mainframe.setVisible(true);
	}

	/**
	* @Title: init
	* @Description: ��ʼ��GUI���棬���ü�����
	* @param    
	* @return void   
	* 
	*/
	public void init()
	{
		// ���������
		int screenWidth = UIUtils.getScreenWidth();
		int screenHeight = UIUtils.getScreenHeight();

		setBounds((screenWidth - FRAME_WIDTH) / 2, (screenHeight - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ���þ��Բ���
		getContentPane().setLayout(null);

		jbt_del = new JButton("Delete");
		jbt_del.setBounds((FRAME_WIDTH) / 2 - 110, FRAME_HEIGHT - 70, 100, 25);
		jbt_del.addActionListener(this);
		getContentPane().add(jbt_del);

		jbt_addNew = new JButton("Add New");
		jbt_addNew.setBounds(FRAME_WIDTH / 2 + 10, FRAME_HEIGHT - 70, 100, 25);
		jbt_addNew.addActionListener(this);
		getContentPane().add(jbt_addNew);

		// ������¼ ���
		addUserFrame = new AddUserFrame("Add user");
		addUserFrame.setVisible(false);
	}

	/**
	* @Title: queryAll
	* @Description: ��ѯ��¼
	* @param    
	* @return void   
	* 
	*/
	public void queryAll()
	{

		Vector<String> colname = new Vector<String>();
		Vector<Vector<String>> data = new Vector<Vector<String>>();

		// ִ�в�ѯ����
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
	* @Description: ��Ӽ�¼����±����ͼ
	* @param    Record rec ��ӵļ�¼
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
	* @Description: TODO ɾ����¼����±����ͼ
	* @param    indexs ��ɾ����¼����������
	* @return void   
	* 
	*/
	public void updateAfterDel(int[] indexs)
	{

		// indexs ��С�������У���ÿ��ɾ�����µ�������С ��Ϊ��������ֵԽ�磬�ʴӺ���ǰɾ��

		for (int i = indexs.length; i > 0; i--)
		{
			// ԭʼ��С
			int origin = tmModel.getRowCount();

			// ɾ��һ��
			tmModel.removeRow(indexs[i - 1]);

			// ������С
			tmModel.setRowCount(origin - 1);
		}
	}

	/**
	 * ��Ӧ��ѯ��������ť��� ʵ�ֲ�ѯ��������¼����
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{

		if (((JButton) e.getSource()) == jbt_addNew)
		{
			// ��ʾ������¼�Ի���
			addUserFrame.setVisible(true);

		} else if (((JButton) e.getSource()) == jbt_del)
		{
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Sure to delete this record?", "Confirm",
					JOptionPane.YES_NO_OPTION))
			{

				//ѡ�м�¼������ֵ
				int[] rowsDel = table.getSelectedRows();

				if (rowsDel.length != 0)
				{
					Record[] records = new Record[rowsDel.length];

					for (int i = 0; i < rowsDel.length; i++)
					{
						// ��ȡѡ�м�¼��websiteֵ����Record����
						records[i] = new Record((String) table.getValueAt(rowsDel[i], 0));
					}

//					ɾ��ѡ�еļ�¼
					RecordDAO.delRec(records);

//					���±����ͼ
					updateAfterDel(rowsDel);

				}
			}
		}
	}
}
