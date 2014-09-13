package geivcore.enginedata.obj;

import geivcore.engineSys.viewcontroller.ModelProjector;

import javax.media.opengl.GL;


public class GLLine extends MutiPointsPObj
{
	public GLLine(geivcore.R R, String HEXC, float Dx1,float Dy1,float Dx2,float Dy2) 
	{
		super(R, HEXC, Dx1,Dy1,new float[]{Dx1,Dx2},new float[]{Dy1,Dy2});
	}
	public GLLine(geivcore.R R, String HEXC, float Dx1,float Dy1,float Dx2,float Dy2,float lineWidth)
	{
		this(R, HEXC, Dx1, Dy1, Dx2, Dy2);
		this.glLineWidth = lineWidth;
	}
	@Override
	public void drawShape(GL gl, ModelProjector refModel) 
	{
		float cW = refModel.cW;
		float cH = refModel.cH;
		float sW = cW/R.ScreenW;
		float sH = cH/(R.ScreenH);
		
		gl.glLineWidth(glLineWidth);
		
		gl.glColor4f((float)C.getRed()/255, (float)C.getGreen()/255,(float)C.getBlue()/255,alpha);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(Dxs[0]*sW,Dys[0]*sH, 0.0f);
		gl.glVertex3f(Dxs[1]*sW,Dys[1]*sH, 0.0f);
		gl.glEnd();
		if(glLineWidth != 1.0f)
		{
			gl.glLineWidth(1.0f);
		}
	}

}
