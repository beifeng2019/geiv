package com.thrblock.util;

public class UMath {
	public static float getPLDistance(float Px,float Py,float Lx1,float Ly1,float Lx2,float Ly2)
	{
		if(Lx1==Lx2)
		{
			return Math.abs(Py-Ly1);
		}
		if(Ly1==Ly2)
		{
			return Math.abs(Px-Lx1);
		}
		else
		{
			float a = (Ly2-Ly1),b=(Lx1-Lx2),c=Lx2*Ly1-Lx1*Ly2;
			return (float) (Math.abs(a*Px+b*Py+c)/Math.sqrt(a*a+b*b));
		}
	}
	public static boolean checkPPDistance(float Dx1,float Dy1,float Dx2,float Dy2,float DstLimit){
		if(Math.abs(Dx1 - Dx2) > DstLimit||Math.abs(Dy1 - Dy2) > DstLimit){
			return false;
		}
		else {
			return getPPDistance(Dx1, Dy1, Dx2, Dy2) < DstLimit;
		}
	}
	public static float getPPDistance(float Dx1,float Dy1,float Dx2,float Dy2)
	{
		return (float)Math.sqrt((Dx2-Dx1)*(Dx2-Dx1)+(Dy2-Dy1)*(Dy2-Dy1));
	}
	public static float getPPTheta(float Dx1,float Dy1,float Dx2,float Dy2)
	{
		float dcx,dcy;

		dcx =Dx2 - Dx1;
		dcy =Dy2 - Dy1;
		double t1 = -dcy/(Math.sqrt(dcx*dcx+dcy*dcy));
		if(t1 > 1)
		{
			t1 = 1;
		}
		else if(t1 < -1)
		{
			t1 = -1;
		}
		float result = (float)Math.acos(t1);
		if(dcx > 0)
		{
			return result;
		}
		else
		{
			return (float)Math.PI*2 - result;
		}
	}
	public static float getPPAngle(float Dx1,float Dy1,float Dx2,float Dy2)
	{
		return getPPTheta(Dx1,Dy1,Dx2,Dy2)*180/(float)Math.PI;
	}
}
