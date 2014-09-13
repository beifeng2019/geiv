package geivcore.enginedata.obj;

import geivcore.MouseListener;
import geivcore.R;
import geivcore.engineSys.viewcontroller.ModelProjector;
import geivcore.enginedata.canonical.CANCollide;
import geivcore.enginedata.canonical.CANFill;
import geivcore.enginedata.canonical.CANPositionMPoints;
import geivcore.enginedata.canonical.CANSquare;

import javax.media.opengl.GL;


public abstract class SquareablePObj extends MutiPointsPObj implements CANSquare,CANFill,CANCollide
{
	MouseListener mouseListener = null;
	boolean useFill = false;
	public SquareablePObj(R R, String HEXC, float Dx,
			float Dy, float[] Dxs, float[] Dys) {
		super(R, HEXC, Dx, Dy, Dxs, Dys);
	}
	public void setGLFill(boolean useFill)
	{
		this.useFill = true;
	}
	public float getSquare()
	{
		float res = 0;
		float Px = Dxs[0];
		float Py = Dys[0];
		for(int i = 1;i < Dxs.length - 1;i++)
		{
			res+=(Math.abs((Dxs[i]-Px)*(Dys[i+1]-Py))+Math.abs((Dxs[i+1]-Px)*(Dys[i]-Py)))/2;
		}
		return res;
	}
	@Override
	public void drawShape(GL gl, ModelProjector refModel)
	{
		//sdone!
		float cW = refModel.cW;
		float cH = refModel.cH;
		float sW = cW/R.ScreenW;
		float sH = cH/R.ScreenH;
		//sdone!
		gl.glColor4f((float)C.getRed()/255, (float)C.getGreen()/255,(float)C.getBlue()/255,alpha);
		int ploc = -1;
		if(this.shaderName != null)
    	{
	    	ploc = gl.glGetAttribLocation(R.getShaderProgram(shaderName),"sys_pIndex");
    	}
		if(useFill)
		{
			gl.glBegin(GL.GL_POLYGON);
		}
		else
		{
			gl.glBegin(GL.GL_LINE_LOOP);
		}
		for(int i = 0;i < Dxs.length;i++)
		{
			gl.glVertexAttrib1f(ploc, i);
			gl.glVertex3f(Dxs[i]*sW,Dys[i]*sH,0.0f);
		}
		gl.glEnd();
	}
	public boolean PolygonCollide(CANPositionMPoints CANMP)
	{
		for(int i = 0;i < CANMP.getPointNumber();i++)
		{
			if(PtInPolygon(CANMP.getDxs(i),CANMP.getDys(i)))
			{
				return true;
			}
		}
		return false;
	}
	public boolean PtInPolygon (float Px,float Py) 
	{ 
		int nCount = Dxs.length;
		int nCross = 0;
		for (int i = 0; i < nCount; i++) 
		{ 
			float px1,py1,px2,py2;
			px1 = Dxs[i];
			py1 = Dys[i];
			
			px2 = Dxs[(i + 1) % nCount];
			py2 = Dys[(i + 1) % nCount];

			// ��� y=p.y �� p1p2 �Ľ���
			if ( py1 == py2 ) // p1p2 �� y=p0.yƽ�� 
				continue;
			if ( Py < min(py1, py2) ) // ������p1p2�ӳ����� 
				continue; 
			if ( Py >= max(py1, py2) ) // ������p1p2�ӳ����� 
				continue;
			// �󽻵�� X ��� -------------------------------------------------------------- 
			float x = (Py - py1) * (px2 - px1) / (py2 - py1) + px1;
			if ( x > Px ) 
			nCross++; // ֻͳ�Ƶ��߽��� 
		}
		return (nCross % 2 == 1); 
	}
	private float min(float x,float y)
	{
		return x>y?y:x;
	}
	private float max(float x,float y)
	{
		return x>y?x:y;
	}
}
