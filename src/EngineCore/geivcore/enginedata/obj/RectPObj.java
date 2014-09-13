package geivcore.enginedata.obj;

import geivcore.R;
import geivcore.enginedata.canonical.CANWH;

public abstract class RectPObj extends SquareablePObj implements CANWH
{
	public RectPObj(R R, String HEXC, float Dx, float Dy,float Width, float Height) 
	{
		super(R, HEXC, Dx, Dy, new float[4],new float[4]);
		Dxs[0] = Dx;
		Dxs[1] = Dx + Width;
		Dxs[2] = Dxs[1];
		Dxs[3] = Dxs[0];
		
		Dys[0] = Dy;
		Dys[1] = Dys[0];
		Dys[2] = Dy + Height;
		Dys[3] = Dys[2];
	}
	public float getWidth()
	{
		return getPPDistance(Dxs[0],Dys[0],Dxs[1],Dys[1]);
	}
	public float getHeight()
	{
		return getPPDistance(Dxs[1],Dys[1],Dxs[2],Dys[2]);
	}
	public void setWidth(float W)
	{
		if(W<=0)
		{
			System.out.println("RectLineValueERROR!");
		}
		else
		{
			DxsL.lock();
			DysL.lock();
			float m = getWidth();
			float k = W/m;
			
			Dxs[1] = k*(Dxs[1] - Dxs[0])+Dxs[0];
			Dxs[2] = k*(Dxs[2] - Dxs[3])+Dxs[3];
			Dys[1] = k*(Dys[1] - Dys[0])+Dys[0];
			Dys[2] = k*(Dys[2] - Dys[3])+Dys[3];
			
			DxsL.unlock();
			DysL.unlock();
		}
	}
	public void setHeight(float H)
	{
		if(H<=0)
		{
			System.out.println("RectLineValueERROR!");
		}
		else
		{
			DxsL.lock();
			DysL.lock();
			float m = getHeight();
			float k = H/m;
			
			Dxs[2] = k*(Dxs[2] - Dxs[1])+Dxs[1];
			Dxs[3] = k*(Dxs[3] - Dxs[0])+Dxs[0];
			Dys[2] = k*(Dys[2] - Dys[1])+Dys[1];
			Dys[3] = k*(Dys[3] - Dys[0])+Dys[0];
			
			DxsL.unlock();
			DysL.unlock();
		}
	}
}
