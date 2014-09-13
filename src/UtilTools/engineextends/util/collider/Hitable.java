package engineextends.util.collider;

import geivcore.enginedata.canonical.CANCollide;

public interface Hitable extends CANCollide{
	
	public float getDx();
	public float getDy();
	
	public float getWidth();
	public float getHeight();
	
	public float getCentralX();
	public float getCentralY();
	
	public void setHitable(boolean isHit, Abshell shell);
	public boolean isEnable();
	
}
