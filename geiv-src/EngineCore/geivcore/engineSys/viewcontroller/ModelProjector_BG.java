package geivcore.engineSys.viewcontroller;

import geivcore.R;

import javax.media.opengl.GL;


public class ModelProjector_BG extends ModelProjector
{
	public ModelProjector_BG(R R)
	{
		super(R.OC.BGIndex,R,200,150,-200,true);
	}
	@Override
	public void projectModel(GL gl)
	{
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glTranslatef(-100,75,-200);
		
		gl.glRotatef(180,1,0,0);
	}
}
