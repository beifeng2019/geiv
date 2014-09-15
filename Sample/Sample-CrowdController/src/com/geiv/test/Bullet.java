package com.geiv.test;

import engineextend.crowdcontroller.Individual;
import geivcore.UESI;
import geivcore.enginedata.obj.Obj;

public class Bullet implements Individual{
	Obj bullet;
	public Bullet(UESI UES){
		bullet = UES.creatObj(UESI.BGIndex);
		bullet.addGLRect("FFFFFF",0,0,5,12);
		bullet.setGLFill(true);
	}
	@Override
	public boolean isAvalible() {
		return !bullet.isPrintable();
	}

	@Override
	public void getUse(Object[] ARGS, float... FARGS) {
		bullet.setCentralX(FARGS[0]);
		bullet.setCentralY(FARGS[1]);
		
		bullet.show();
	}

	@Override
	public void doStp(int clock) {
		if(bullet.getDy() > 0){
			bullet.setDy(bullet.getDy() - 5.5f);
		}
		else{
			finish(0);
		}
	}

	@Override
	public void finish(int src) {
		bullet.hide();
	}

	@Override
	public void destroy() {
		bullet.destroy();		
	}

	

}
