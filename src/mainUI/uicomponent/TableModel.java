package mainUI.uicomponent;

import javax.swing.table.DefaultTableModel;

/**
*@author ckw
*@version time��2017��7��14�� ����6:33:27
*/

public class TableModel extends DefaultTableModel
{

	// ��ֹ��Ǳ��
	@Override
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}

}