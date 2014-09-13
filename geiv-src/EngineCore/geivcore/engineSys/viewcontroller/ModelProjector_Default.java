package geivcore.engineSys.viewcontroller;

import geivcore.R;

import javax.media.opengl.GL;


public class ModelProjector_Default extends ModelProjector
{
	public ModelProjector_Default(R R)
	{
		super(-1,R,200,150,-200,true);
	}
	@Override
	public void projectModel(GL gl)
	{
		//System.out.println("Default!");
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glTranslatef(-100,75,-200);
		gl.glRotatef(180,1,0,0);
	}
}
