package geivcore.engineSys.io;


import geivcore.R;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class mouseWheel implements MouseWheelListener
{
	R R;
	boolean response = true;
	public int roolFlag = 0;
	public mouseWheel(R R)
	{
		this.R = R;
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		int count = e.getWheelRotation();
		if(response)
		{
			response = false;
			if(count == -1)
			{
				//�Ϲ�
				roolFlag = -1;
			}
			else
			{
				//�¹�
				roolFlag = 1;
			}
			
			roolFlag = 0;
			
			response = true;
		}
	}
}
