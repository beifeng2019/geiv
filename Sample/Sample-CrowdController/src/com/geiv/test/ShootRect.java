package com.geiv.test;

import java.awt.event.KeyEvent;

import engineextend.crowdcontroller.CrowdController;
import geivcore.SerialTask;
import geivcore.UESI;
import geivcore.enginedata.canonical.CANExPos;
import geivcore.enginedata.obj.Obj;

public class ShootRect implements SerialTask{
	UESI UES;
	Obj rect;
	CrowdController cc;
	public ShootRect(UESI UES){
		this.UES = UES;
		rect = UES.creatObj(UESI.BGIndex);
		rect.addGLRect("FFFFFF",0,0,40,40);
		rect.setPosition(CANExPos.POS_CENTER);
		cc = new CrowdController(UES,false);
		
		for(int i = 0;i < 32;i++){
			cc.addIndividual(new Bullet(UES));
		}
		UES.addSerialTask(cc);
		UES.addSerialTask(this);
		
		rect.show();
	}
	@Override
	public void Serial(int clock) {
		if(UES.getKeyStatus(KeyEvent.VK_LEFT)){
			rect.setDx(rect.getDx() - 3.0f);
		}
		if(UES.getKeyStatus(KeyEvent.VK_RIGHT)){
			rect.setDx(rect.getDx() + 3.0f);
		}
		if(UES.getKeyStatus(KeyEvent.VK_UP)){
			rect.setDy(rect.getDy() - 3.0f);
		}
		if(UES.getKeyStatus(KeyEvent.VK_DOWN)){
			rect.setDy(rect.getDy() + 3.0f);
		}
		if(UES.getKeyStatus(KeyEvent.VK_A)){
			if(clock%10 == 0){
				cc.getAvailible().getUse(null, rect.getCentralX(),rect.getCentralY());
			}
		}
	}
}
