package geivcore.engineSys.io;


import geivcore.MouseFactor;
import geivcore.R;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


public class mouseButton extends MouseAdapter
{
	R R;
	boolean showBotton = true;
	public ReentrantLock selectListLock = new ReentrantLock();
	public List<Boolean> MouseInfo = new ArrayList<Boolean>(8);
	public List<MouseFactor> listenerList = new ArrayList<MouseFactor>();
	//public List<mouseButton_IO> mouseRefObj = new ArrayList<mouseButton_IO>();
	public int MouseX,MouseY;
	public mouseButton(R R)
	{
		this.R = R;
		for(int i = 0;i < 8;i++)
		{
			MouseInfo.add(false);
		}
	}
	public void addMouseRefObj(MouseFactor mF)
	{
		selectListLock.lock();
		listenerList.add(mF);
		selectListLock.unlock();
	}
	public void removeMouseRefObj(MouseFactor mF)
	{
		selectListLock.lock();
		listenerList.remove(mF);
		selectListLock.unlock();
	}
	@Override
	public void mousePressed(MouseEvent e) 
	{
//		if(showBotton)
//   	 	{
//   		 	System.out.println(e.getButton());
//   		 	System.out.println("CRT SC: " + MouseX +","+ MouseY);
//   	 	}
		selectListLock.lock();
		for(MouseFactor mF:listenerList)
		{
			if(mF.isMouseInside())
			{
				mF.mouseFactor(e);
			}
		}
		selectListLock.unlock();
		MouseInfo.set(e.getButton(), true);
	}
	@Override
	public void mouseReleased(MouseEvent e) 
	{
//		if(showBotton)
//   	 	{
//   		 	System.out.println(e.getButton());
//   		 	System.out.println("CRT SC: " + MouseX +","+ MouseY);
//   	 	}
		MouseInfo.set(e.getButton(), false);
	}
	@Override
	public void mouseMoved(MouseEvent e)   
    {  
		MouseX = e.getX();
		MouseY = e.getY();
	}
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		MouseX = e.getX();
		MouseY = e.getY();
	}
}