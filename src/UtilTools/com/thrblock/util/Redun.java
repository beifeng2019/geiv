package com.thrblock.util;

import geivcore.enginedata.canonical.CANPosition;

public class Redun {
	public static boolean isXSmall(CANPosition CP,float DST,float SPD)
	{
		float bt = DST - CP.getDx();
		if(bt > SPD)
		{
			return true;
		}
		else if(bt >= 0)
		{
			CP.setDx(DST);
			return false;
		}
		else
		{
			return false;
		}
	}
	public static boolean isYSmall(CANPosition CP,float DST,float SPD)
	{
		//System.out.println(CP.getDy()+","+DST+","+SPD);
		float bt = DST - CP.getDy();
		if(bt > SPD)
		{
			return true;
		}
		else if(bt >= 0)
		{
			CP.setDy(DST);
			return false;
		}
		else
		{
			return false;
		}
	}
	public static boolean isXBigger(CANPosition CP,float DST,float SPD)
	{
		float bt = CP.getDx() - DST;
		if(bt > SPD)
		{
			return true;
		}
		else if(bt >= 0)
		{
			CP.setDx(DST);
			return false;
		}
		else
		{
			return false;
		}
	}
	public static boolean isYBigger(CANPosition CP,float DST,float SPD)
	{
		float bt = CP.getDy() - DST;
		if(bt > SPD)
		{
			return true;
		}
		else if(bt >= 0)
		{
			CP.setDy(DST);
			return false;
		}
		else
		{
			return false;
		}
	}
	public static boolean approximate(float value,float dst,float stp) {
		return Math.abs(value - dst) < stp;
	}
}
