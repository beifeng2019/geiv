package geivcore.engineSys.viewcontroller;

import geivcore.R;

import javax.media.opengl.GL;


public class ModelProjector_UI extends ModelProjector
{
	public ModelProjector_UI(R R)
	{
		super(R.OC.UIIndex,R,200,150,-200,false);
	}
	@Override
	public void projectModel(GL gl)
	{
		//System.out.println("BG");
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glTranslatef(-100,75,-200);
		
		gl.glRotatef(180,1,0,0);
		//System.out.println(R.TransX+","+R.TransY+","+R.Depth+","+R.RotateTheta+","+R.RotateX+","+R.RotateY+","+R.RotateZ);
	}
}
