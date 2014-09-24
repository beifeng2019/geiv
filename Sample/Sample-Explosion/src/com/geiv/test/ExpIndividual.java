package com.geiv.test;

import engineextend.crowdcontroller.Individual;
import geivcore.UESI;
import geivcore.enginedata.obj.Obj;
import java.awt.Color;
import com.thrblock.util.REPR;
import com.thrblock.util.RandomSet;

public class ExpIndividual implements Individual {
	UESI UES;
	Obj Disp;

	float STallms = 500;
	float allms = STallms;
	float Dms = 17;

	float V = 4.5f;
	float ax, ay;
	float vx, vy;
	float Theta;

	public ExpIndividual(UESI UES) {
		this.UES = UES;
		Disp = UES.creatObj(UESI.XRIndex);
//		Disp.addGLOval("FFFFFF",0,0,12,12,12);
//		Disp.setGLFill(true);
		Disp.addGLImage(0, 0, 12, 12, ".\\Effect\\PT_CLOUD1_POINT.png");
		Disp.setColor("FFFFFF");
		Disp.setAlph(Disp.getTopDivIndex(), 1.0f);

		allms = STallms;
	}

	@Override
	public boolean isAvalible() {
		return !Disp.isPrintable();
	}

	@Override
	public void getUse(Object[] ARGS, float... FARGS) {
		int Rad;
		if (RandomSet.getRate(50)) {
			Rad = RandomSet.getRandomNum(6, 35);
		} else {
			Rad = RandomSet.getRandomNum(6, 24);
		}
		Disp.setWidth(Rad);
		Disp.setHeight(Rad);

		Disp.setCentralX(FARGS[0] + RandomSet.getRandomNum(-5, 5));
		Disp.setCentralY(FARGS[1] + RandomSet.getRandomNum(-5, 5));

		Disp.setAngle(RandomSet.getRandomFloatIn_1() * 360);

		Theta = (float) Math.PI * 2 * RandomSet.getRandomFloatIn_1();
		// V = 2.5f*24/Disp.getWidth() + 2.0f;
		vx = V * (float) Math.sin(Theta);
		vy = -V * (float) Math.cos(Theta);

		ax = -0.0003f * (Disp.getWidth() * Disp.getWidth()) * vx;
		ay = -0.0003f * (Disp.getHeight() * Disp.getHeight()) * vy;

		Disp.show();
	}

	@Override
	public void doStp(int clock) {
		if (this.allms > Dms) {
			allms -= Dms;

			Disp.setColor(new Color(1.0f, REPR.Rep_POW_1_F(allms / STallms,
					Disp.getWidth() / 24), REPR.Rep_POW_F(allms / STallms, 16)));
			Disp.setAlph(REPR.Rep_POW_1_F(allms / STallms, Disp.getWidth() / 12));

			ax = -0.0003f * (Disp.getWidth() * Disp.getWidth()) * vx;
			ay = -0.0003f * (Disp.getHeight() * Disp.getHeight()) * vy;

			vx += ax;
			vy += ay;

			Disp.setDx(Disp.getDx() + vx);
			Disp.setDy(Disp.getDy() + vy);
		} else {
			finish(Individual.SRC_INNER);
		}
	}

	@Override
	public void finish(int src) {
		Disp.hide();

		Disp.setColor("FFFFFF");
		Disp.setAlph(Disp.getTopDivIndex(), 1.0f);

		Disp.setWidth(12);
		Disp.setHeight(12);

		allms = STallms;
	}

	@Override
	public void destroy() {
		Disp.destroy();
	}
}
