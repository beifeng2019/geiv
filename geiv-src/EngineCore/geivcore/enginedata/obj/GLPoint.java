package geivcore.enginedata.obj;

import geivcore.engineSys.viewcontroller.ModelProjector;

import javax.media.opengl.GL;


public class GLPoint extends PObj
{
	public GLPoint(geivcore.R R,String HEXC,float Dx, float Dy) 
	{
		super(R, HEXC, Dx, Dy);
	}
	@Override
	public void drawShape(GL gl, ModelProjector refModel) 
	{
		float cW = refModel.cW;
		float cH = refModel.cH;
		
		float sW = cW/R.ScreenW;
		float sH = cH/(R.ScreenH);
		
		gl.glColor4f((float)C.getRed()/255, (float)C.getGreen()/255,(float)C.getBlue()/255,alpha);
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex3f(Dx*sW,Dy*sH,0.0f);
		gl.glEnd();
	}
}
