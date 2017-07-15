package mainUI.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
*@author ckw
*@version time��2017��7��14�� ����8:17:26
*/

public class UIUtils
{

	public static int getScreenWidth()
	{
		Toolkit size = Toolkit.getDefaultToolkit();
		Dimension screenSize = size.getScreenSize();
		return screenSize.width;

	}

	public static int getScreenHeight()
	{
		Toolkit size = Toolkit.getDefaultToolkit();
		Dimension screenSize = size.getScreenSize();
		return screenSize.height;
	}
}
