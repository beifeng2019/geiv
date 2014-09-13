package engineextend.components;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import engineextend.components.views.GL_ComponentView;
import geivcore.KeyFactor;
import geivcore.KeyListener;
import geivcore.MouseFactor;
import geivcore.MouseListener;
import geivcore.UESI;
import geivcore.enginedata.canonical.CANExPos;
import geivcore.enginedata.canonical.CANPosition;
import geivcore.enginedata.canonical.CANRefPos;
import geivcore.enginedata.canonical.CANWH;


public abstract class GL_Component implements KeyFactor,MouseFactor,CANPosition,CANExPos,CANRefPos,CANWH
{
	public UESI UES;
	GL_ComponentView Displayer;
	List<GL_Component> subComp = new ArrayList<GL_Component>();
	
	KeyListener keyListener;
	MouseListener mouseListener;
	
	public ReentrantLock compListLock = new ReentrantLock();
	public GL_Component(UESI UES,GL_ComponentView View) 
	{
		this.UES = UES;
		this.Displayer = View;
	}
	public void addComponent(GL_Component C)
	{
		compListLock.lock();
		subComp.add(C);
		compListLock.unlock();
	}
	public void removeComponent(GL_Component C)
	{
		compListLock.lock();
		subComp.remove(C);
		compListLock.unlock();
	}
	public void removeAllComponent()
	{
		compListLock.lock();
		subComp.removeAll(subComp);
		compListLock.unlock();
	}
	public void show()
	{
		Displayer.show();
		UES.addMouseButtonIO(this);
		compListLock.lock();
		for(GL_Component C:subComp)
		{
			C.show();
		}
		compListLock.unlock();
	}
	public boolean isPrintable()
	{
		return Displayer.isPrintable();
	}
	public void hide()
	{
		Displayer.hide();
		UES.removeMouseButtonIO(this);
		compListLock.lock();
		for(GL_Component C:subComp)
		{
			C.hide();
		}
		compListLock.unlock();
	}
	@Override
	public void setDx(float Dx)
	{
		float DxOffset = Dx - Displayer.getDx();
		Displayer.setDx(Dx);
		compListLock.lock();
		for(GL_Component subCom:subComp)
		{
			subCom.setDx(subCom.Displayer.getDx()+DxOffset);
		}
		compListLock.unlock();
	}
	@Override
	public void setDy(float Dy)
	{
		float DyOffset = Dy - Displayer.getDy();
		Displayer.setDy(Dy);
		compListLock.lock();
		for(GL_Component subCom:subComp)
		{
			subCom.setDy(subCom.Displayer.getDy()+DyOffset);
		}
		compListLock.unlock();
	}
	@Override
	public float getDx() {
		return Displayer.getDx();
	}
	@Override
	public float getDy() {
		return Displayer.getDy();
	}
	@Override
	public float getWidth()
	{
		if(Displayer instanceof CANWH)
		{
			return ((CANWH) Displayer).getWidth();
		}
		else
		{
			return 0;
		}
	}
	@Override
	public float getHeight()
	{
		if(Displayer instanceof CANWH)
		{
			return ((CANWH) Displayer).getHeight();
		}
		else
		{
			return 0;
		}
	}
	@Override
	public void setWidth(float Width)
	{
		if(Displayer instanceof CANWH)
		{
			((CANWH) Displayer).setWidth(Width);
		}
		else
		{
			System.out.println("Error in canwh,component");
		}
	}
	@Override
	public void setHeight(float Height)
	{
		if(Displayer instanceof CANWH)
		{
			((CANWH) Displayer).setHeight(Height);
		}
		else
		{
			System.out.println("Error in canwh,component");
		}
	}
	public void setPosition(int FLAG,CANPosition rO,float margin)
	{
		float ODx = Displayer.getDx();
		float ODy = Displayer.getDy();
		Displayer.setPosition(FLAG, rO, margin);
		float DxOffset = Displayer.getDx() - ODx;
		float DyOffset = Displayer.getDy() - ODy;
		compListLock.lock();
		for(GL_Component subCom:subComp)
		{
			subCom.setDx(subCom.Displayer.getDx()+DxOffset);
			subCom.setDy(subCom.Displayer.getDy()+DyOffset);
		}
		compListLock.unlock();
	}
	public void setPosition(int FLAG,CANPosition rO)
	{
		float ODx = Displayer.getDx();
		float ODy = Displayer.getDy();
		Displayer.setPosition(FLAG, rO);
		float DxOffset = Displayer.getDx() - ODx;
		float DyOffset = Displayer.getDy() - ODy;
		compListLock.lock();
		for(GL_Component subCom:subComp)
		{
			subCom.setDx(subCom.Displayer.getDx()+DxOffset);
			subCom.setDy(subCom.Displayer.getDy()+DyOffset);
		}
		compListLock.unlock();
	}
	@Override
	public void setPosition(int FLAG,float margin)
	{
		float ODx = Displayer.getDx();
		float ODy = Displayer.getDy();
		Displayer.setPosition(FLAG,margin);
		float DxOffset = Displayer.getDx() - ODx;
		float DyOffset = Displayer.getDy() - ODy;
		compListLock.lock();
		for(GL_Component subCom:subComp)
		{
			subCom.setDx(subCom.Displayer.getDx()+DxOffset);
			subCom.setDy(subCom.Displayer.getDy()+DyOffset);
		}
		compListLock.unlock();
	}
	@Override
	public void setPosition(int FLAG)
	{
		float ODx = Displayer.getDx();
		float ODy = Displayer.getDy();
		Displayer.setPosition(FLAG);
		float DxOffset = Displayer.getDx() - ODx;
		float DyOffset = Displayer.getDy() - ODy;
		compListLock.lock();
		for(GL_Component subCom:subComp)
		{
			subCom.setDx(subCom.Displayer.getDx()+DxOffset);
			subCom.setDy(subCom.Displayer.getDy()+DyOffset);
		}
		compListLock.unlock();
	}
	public void destroy()
	{
		Displayer.destroy();
		compListLock.lock();
		for(GL_Component subCom:subComp)
		{
			subCom.destroy();
		}
		compListLock.unlock();
	}
	public void setKeyListener(KeyListener keyListener)
	{
		this.keyListener = keyListener;
	}
	public void removeKeyListener()
	{
		this.keyListener = null;
	}
	public void keyFactor(int keyCode,boolean ispressed)
	{
		if(keyListener != null)
		{
			keyListener.doKeyBord(this, keyCode, ispressed);
		}
	}
	public String getInfo()
	{
		return this.toString();
	}
	public void setMouseListener(MouseListener mouseListener)
	{
		this.mouseListener = mouseListener;
		UES.addMouseButtonIO(this);
	}
	public void removeMouseListener()
	{
		UES.removeMouseButtonIO(this);
		this.mouseListener = null;
	}
	public void mouseFactor(MouseEvent e)
	{
		if(mouseListener!=null)
		{
			mouseListener.onClick(this, e);
		}
	}
	public boolean isMouseInside()
	{
		return Displayer.isMouseInside();
	}
}
