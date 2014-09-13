package geivcore.engineSys.io;


import geivcore.R;
//import EngineExtends.ParticleSystem.ParticleProjector.particleProjector;
//import EngineExtends.ParticleSystem.ParticleProjector.smallPointProjector;
public class mouse
{
	R R;
	boolean showMouseInfo = true;

	public mouseButton mouseBotton;
	public mouseWheel mouseWheel;

	public mouse(R R)
	{
		this.R = R;
		R.mouse = this;
		mouseBotton = new mouseButton(R);
		mouseWheel = new mouseWheel(R);
	}
}
