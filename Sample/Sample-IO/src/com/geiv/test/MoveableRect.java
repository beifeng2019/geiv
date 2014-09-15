package com.geiv.test;

import java.awt.event.KeyEvent;

import geivcore.SerialTask;
import geivcore.UESI;
import geivcore.enginedata.canonical.CANExPos;
import geivcore.enginedata.obj.Obj;

public class MoveableRect implements SerialTask{
	Obj rect;
	UESI UES;
	public MoveableRect(UESI UES){
		this.UES = UES;
		rect = UES.creatObj(UESI.BGIndex);
		rect.addGLRect("00FF00",0,0,200f,200f);
		rect.setGLFill(true);
		rect.setPosition(CANExPos.POS_CENTER);
		rect.show();
		
		UES.addSerialTask(this);
	}
	@Override
	public void Serial(int arg0) {
		if(UES.getKeyStatus(KeyEvent.VK_LEFT)){
			rect.setDx(rect.getDx() - 3);
		}
		if(UES.getKeyStatus(KeyEvent.VK_RIGHT)){
			rect.setDx(rect.getDx() + 3);
		}
		if(UES.getKeyStatus(KeyEvent.VK_UP)){
			rect.setDy(rect.getDy() - 3);
		}
		if(UES.getKeyStatus(KeyEvent.VK_DOWN)){
			rect.setDy(rect.getDy() + 3);
		}
	}
}
