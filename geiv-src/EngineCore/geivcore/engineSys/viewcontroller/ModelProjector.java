package geivcore.engineSys.viewcontroller;

import geivcore.R;

import javax.media.opengl.GL;


public abstract class ModelProjector 
{
	R R;
	public int DivIndex;
	public float cW,cH,Depth;
	public boolean useViewPort;
	public ModelProjector(int DivIndex,R R,float cW,float cH,float Depth,boolean useViewPort)
	{
		this.DivIndex = DivIndex;
		this.cW = cW;
		this.cH = cH;
		this.Depth = Depth;
		this.R = R;
		this.useViewPort = useViewPort;
	}
	public abstract void projectModel(GL gl);
}
