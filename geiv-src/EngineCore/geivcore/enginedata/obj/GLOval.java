package geivcore.enginedata.obj;

import geivcore.enginedata.canonical.CANWH;


public class GLOval extends SquareablePObj implements CANWH
{
	float laxis,saxis;
	int accuracy;
	public GLOval(geivcore.R R, String HEXC, float Dx,
			float Dy,float laxis,float saxis,int accuracy) {
		super(R, HEXC, Dx, Dy, new float[accuracy],new float[accuracy]);
		this.laxis = laxis;
		this.saxis = saxis;
		this.accuracy = accuracy;
		double thetaAcc = 2*Math.PI/accuracy;
		for(int i = 0;i < accuracy;i++)
		{
			Dxs[i] = (laxis/2)*(float)Math.cos(thetaAcc*i)+Dx+laxis/2;
			Dys[i] = (saxis/2)*(float)Math.sin(thetaAcc*i)+Dy+saxis/2;
		}
	}
	@Override
	public float getWidth() {
		return laxis;
	}
	@Override
	public float getHeight() {
		return saxis;
	}
	@Override
	public void setWidth(float Width) {
		this.laxis = Width;
		double thetaAcc = 2*Math.PI/accuracy;
		for(int i = 0;i < accuracy;i++)
		{
			Dxs[i] = (laxis/2)*(float)Math.cos(thetaAcc*i)+Dx+laxis/2;
			Dys[i] = (saxis/2)*(float)Math.sin(thetaAcc*i)+Dy+saxis/2;
		}
		setAngle(getAngle());
	}
	@Override
	public void setHeight(float Height) {
		this.saxis = Height;
		double thetaAcc = 2*Math.PI/accuracy;
		for(int i = 0;i < accuracy;i++)
		{
			Dxs[i] = (laxis/2)*(float)Math.cos(thetaAcc*i)+Dx+laxis/2;
			Dys[i] = (saxis/2)*(float)Math.sin(thetaAcc*i)+Dy+saxis/2;
		}
		setAngle(getAngle());
	}
}
