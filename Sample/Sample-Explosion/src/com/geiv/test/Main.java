package com.geiv.test;

import geivcore.R;
import geivcore.UESI;

public class Main{
	public static void main(String[] args) {
		UESI UES = new R();
		Explosion exp = new Explosion(UES);
		for(;;){
			exp.doEffect(400,300);
			UES.wait(3,1000);
		}
	}
}
