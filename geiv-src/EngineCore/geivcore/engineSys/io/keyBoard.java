package geivcore.engineSys.io;


import geivcore.KeyFactor;
import geivcore.R;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class keyBoard extends KeyAdapter
{
	boolean showKey = false;
	public int topKeyObjIndex = 0;
	
	//public List<Boolean> KeyInfo = new ArrayList<Boolean>(256);
	Hashtable<Integer,Boolean> KeyInfo = new Hashtable<Integer,Boolean>(); 
	public List<KeyFactor> KeyRefObj = new ArrayList<KeyFactor>(16);//���̶�ջ ���̷��͵���Ϣʼ�ո����ջ���˶���
	
	public keyBoard(R R)
	{
//		for(int i = 0;i < 524;i++)
//		{
//			KeyInfo.add(false);
//		}
		R.keyBoard = this;
	}
	public boolean getKeyStatus(int keyCode)
	{
		if(KeyInfo.containsKey(keyCode))
		{
			return KeyInfo.get(keyCode);
		}
		else
		{
			return false;
		}
	}
	public void pressKey(int keyCode)
	{	
		KeyInfo.put(keyCode,true);
		//KeyInfo.set(keyCode, true);
		if(topKeyObjIndex>0)
		{
			KeyRefObj.get(topKeyObjIndex-1).keyFactor(keyCode,true);
		}
	}
	public void relasKey(int keyCode)
	{
		KeyInfo.put(keyCode,false);
		if(topKeyObjIndex>0)
		{
			KeyRefObj.get(topKeyObjIndex-1).keyFactor(keyCode,false);
		}
	}
	public void pushKeyIO(KeyFactor kF)
	{
		if(topKeyObjIndex!=15)
		{
			KeyRefObj.add(kF);
			this.topKeyObjIndex++;
		}
	}
	public void popKeyIO()
	{
		if(topKeyObjIndex!=0)
		{
			this.topKeyObjIndex--;
			KeyRefObj.remove(topKeyObjIndex);
		}
	}
	public void popAllKeyIO()
	{
		while(topKeyObjIndex!=0)
		{
			this.topKeyObjIndex--;
			KeyRefObj.remove(topKeyObjIndex);
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(showKey)
   	 	{
   		 	System.out.println(e.getKeyCode());
   	 	}
   	 	pressKey(e.getKeyCode());
	}
	@Override
	public void keyReleased(KeyEvent e) {
   	 	relasKey(e.getKeyCode());
	}
}
