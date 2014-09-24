package com.geiv.test;

import engineextend.crowdcontroller.CrowdController;
import geivcore.UESI;

public class Explosion{

	UESI UES;
	CrowdController cc;
	public Explosion(UESI UES)
	{
		this.UES = UES;
		cc = new CrowdController(UES, true);
		for(int i = 0;i < 512;i++)
		{
			cc.addIndividual(new ExpIndividual(UES));
		}
	}

	public void doEffect(float dx,float dy) {
		for(int i = 0;i < 256;i++)
		{
			cc.getAvailible().getUse(null,dx,dy);
		}
	}

	public void forceClose() {
		cc.finishAllInd();
	}
}