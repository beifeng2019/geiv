package geivcore.enginedata.obj;


import geivcore.R;
import geivcore.enginedata.canonical.CANAngle;
import geivcore.enginedata.canonical.CANCentral;
import geivcore.enginedata.canonical.CANLineWidth;
import geivcore.enginedata.canonical.CANPositionMPoints;
import geivcore.enginedata.canonical.CANTheta;

import java.util.concurrent.locks.ReentrantLock;


public abstract class MutiPointsPObj extends PObj implements CANPositionMPoints,CANTheta,CANAngle,CANCentral,CANLineWidth
{
	float[] Dxs,Dys;
	float glLineWidth = 1.0f;
	public ReentrantLock DxsL,DysL;
	float Theta = 0;
	public MutiPointsPObj(R R, String HEXC, float Dx,float Dy,float[] Dxs,float[] Dys) 
	{
		super(R, HEXC, Dx, Dy);
		this.Dxs = Dxs;
		this.Dys = Dys;
		
		DxsL = new ReentrantLock();
		DysL = new ReentrantLock();
		
		if(Dxs.length!=Dys.length)
		{System.out.println("Warning!MPDisMatch!");}
	}
	//////////getϵ��CX,CY,XN,YN,Theta
	@Override
	public float getCentralX()
	{
		float res = 0;
		DxsL.lock();
		for(int i = 0;i < Dxs.length;i++)
		{
			res+=Dxs[i];
		}
		DxsL.unlock();
		res = res/Dxs.length;
		return res;
	}
	@Override
	public float getCentralY()
	{
		float res = 0;
		DysL.lock();
		for(int i = 0;i < Dys.length;i++)
		{
			res+=Dys[i];
		}
		DysL.unlock();
		res = res/Dxs.length;
		return res;
	}
	public float getDxs(int index)
	{
		if(index>=0&&index<Dxs.length)
		{
			return Dxs[index];
		}
		else
		{
			System.out.println("GetDxsError");
			return 0;
		}
	}
	public float getDys(int index)
	{
		if(index>=0&&index<Dys.length)
		{
			return Dys[index];
		}
		else
		{
			System.out.println("GetDysError");
			return 0;
		}
	}
	public float getTheta()
	{
		return this.Theta;
	}
	public float getAngle()
	{
		return getTheta()*180/(float)Math.PI;
	}
	public int getPointNumber()
	{
		return Dxs.length;
	}
	//////////setϵ��DX,DY,DXN,DYN,Theta,lineW
	@Override
	public void setDx(float Dx)
	{
		float Offset = Dx - this.Dx;
		super.setDx(Dx);
		DxsL.lock();
		for(int i = 0;i<Dxs.length;i++)
		{
			Dxs[i]+=Offset;
		}
		DxsL.unlock();
	}
	@Override
	public void setDy(float Dy)
	{
		float Offset = Dy - this.Dy;
		super.setDy(Dy);
		DysL.lock();
		for(int i = 0;i<Dys.length;i++)
		{
			Dys[i]+=Offset;
		}
		DysL.unlock();
	}
	public void setDxs(float Dx,int index)
	{
		DxsL.lock();
		if(index>=0&&index<Dxs.length)
		{Dxs[index] = Dx;}
		DxsL.unlock();
	}
	public void setDys(float Dy,int index)
	{
		DysL.lock();
		if(index>=0&&index<Dys.length)
		{Dys[index] = Dy;}
		DysL.unlock();
	}
	public void setAngle(float Angle)
	{
		setTheta(Angle*(float)Math.PI/180);
	}
	public void setTheta(float Theta)
	{
		float Offset = Theta - this.Theta;
		DxsL.lock();
		DysL.lock();
		float PDx = getCentralX();
		float PDy = getCentralY();
		float[] res;
		for(int i = 0;i < Dxs.length;i++)
		{
			res = Revolve(Dxs[i],Dys[i],PDx,PDy,Offset);
			Dxs[i] = res[0];
			Dys[i] = res[1];
		}
		DxsL.unlock();
		DysL.unlock();
		this.Theta = Theta;
	}
	public void setLineWidth(float lineWidth)
	{
		this.glLineWidth = lineWidth;
	}
	public float getLineWidth()
	{
		return this.glLineWidth;
	}
	protected float[] Revolve(float Dx,float Dy,float PDx,float PDy,float Theta)
	{
		float[] result = new float[2];
		double theta = Theta;
		float Cdx,Cdy;
		
		Cdx = Dx - PDx;
		Cdy = Dy - PDy;
		
		result[0] = (float)(Cdx*Math.cos(theta) - Cdy*Math.sin(theta)) + PDx;
		result[1] = (float)(Cdx*Math.sin(theta) + Cdy*Math.cos(theta)) + PDy;
		return result;
	}
	//ACTIONS
	/////////////////�ϼ�����
	//refDistance,refAngle,ACTIONS
	@Override
	public float getRefDistance(PObj PO)
	{
		return getPPDistance(getCentralX(),getCentralY(),PO.getCentralX(),PO.getCentralY());
	}
	@Override
	public float getRefDistance(float Dx,float Dy)
	{
		return getPPDistance(getCentralX(),getCentralY(),Dx,Dy);
	}
}
