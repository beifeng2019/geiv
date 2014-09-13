package geivcore.enginedata.canonical;

import geivcore.enginedata.obj.PObj;

public interface CANReferance {
	public float getRefDistance(float Dx,float Dy);
	public float getRefDistance(PObj PO);
	public float getRefTheta(float Dx,float Dy);
	public float getRefTheta(PObj PO);
}
