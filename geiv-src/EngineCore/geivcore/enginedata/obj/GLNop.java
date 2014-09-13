package geivcore.enginedata.obj;

import geivcore.R;
import geivcore.engineSys.viewcontroller.ModelProjector;

import javax.media.opengl.GL;


public class GLNop extends RectPObj
{
	public GLNop(R r,float Dx, float Dy) 
	{
		super(r,"FFFFFF",Dx,Dy,1,1);
	}
	@Override
	public void drawShape(GL gl, ModelProjector refModel) 
	{
		return;
	}
}
