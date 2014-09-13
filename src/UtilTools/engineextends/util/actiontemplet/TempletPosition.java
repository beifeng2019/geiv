package engineextends.util.actiontemplet;

import geivcore.enginedata.canonical.CANCentral;
import geivcore.enginedata.canonical.CANPosition;

public class TempletPosition {
	public static boolean moveCyTo(CANCentral po,float dst,float spd)
	{
		float crty = po.getCentralY();
		float dec = Math.abs(dst - crty);
		if(crty < dst)
		{
			if(dec > spd)
			{
				po.setCentralY(crty + spd);
			}
			else
			{
				po.setCentralY(dst);
			}
			return false;
		}
		else if(crty > dst)
		{
			if(dec > spd)
			{
				po.setCentralY(crty - spd);
			}
			else
			{
				po.setCentralY(dst);
			}
			return false;
		}
		else
		{
			return true;
		}
	}
	public static boolean moveCxTo(CANCentral po,float dst,float spd)
	{
		float crtx = po.getCentralX();
		float dec = Math.abs(dst - crtx);
		if(crtx < dst)
		{
			if(dec > spd)
			{
				po.setCentralX(crtx + spd);
			}
			else
			{
				po.setCentralX(dst);
			}
			return false;
		}
		else if(crtx > dst)
		{
			if(dec > spd)
			{
				po.setCentralX(crtx - spd);
			}
			else
			{
				po.setCentralX(dst);
			}
			return false;
		}
		else
		{
			return true;
		}
	}
	public static boolean moveDxTo(CANPosition po,float dst,float spd)
	{
		float crtx = po.getDx();
		float dec = Math.abs(dst - crtx);
		if(crtx < dst)
		{
			if(dec > spd)
			{
				po.setDx(crtx + spd);
			}
			else
			{
				po.setDx(dst);
			}
			return false;
		}
		else if(crtx > dst)
		{
			if(dec > spd)
			{
				po.setDx(crtx - spd);
			}
			else
			{
				po.setDx(dst);
			}
			return false;
		}
		else
		{
			return true;
		}
	}
	public static boolean moveDyTo(CANPosition po,float dst,float spd)
	{
		float crty = po.getDy();
		float dec = Math.abs(dst - crty);
		if(crty < dst)
		{
			if(dec > spd)
			{
				po.setDy(crty + spd);
			}
			else
			{
				po.setDy(dst);
			}
			return false;
		}
		else if(crty > dst)
		{
			if(dec > spd)
			{
				po.setDy(crty - spd);
			}
			else
			{
				po.setDy(dst);
			}
			return false;
		}
		else
		{
			return true;
		}
	}
}
