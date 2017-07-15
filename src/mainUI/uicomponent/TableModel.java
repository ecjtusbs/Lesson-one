package mainUI.uicomponent;

import javax.swing.table.DefaultTableModel;

/**
*@author ckw
*@version time：2017年7月14日 下午6:33:27
*/

public class TableModel extends DefaultTableModel
{

	// 禁止编辑表格
	@Override
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}

}
