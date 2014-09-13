package geivcore.enginedata.canonical;

public interface CANPositionMPoints {
	public void setDxs(float Dx,int index);
	public void setDys(float Dy,int index);
	public float getDxs(int index);
	public float getDys(int index);
	public int getPointNumber();
}
