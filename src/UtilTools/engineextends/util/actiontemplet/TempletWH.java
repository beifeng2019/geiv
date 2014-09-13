package engineextends.util.actiontemplet;

import geivcore.enginedata.canonical.CANWH;

public class TempletWH {
	public static boolean stretchWTo(CANWH wh,float dst,float spd)
	{
		float crtw = wh.getWidth();
		float dec = Math.abs(dst - crtw);
		if(crtw < dst)
		{
			if(dec > spd)
			{
				wh.setWidth(crtw + spd);
			}
			else
			{
				wh.setWidth(dst);
			}
			return false;
		}
		else if(crtw > dst)
		{
			if(dec > spd)
			{
				wh.setWidth(crtw - spd);
			}
			else
			{
				wh.setWidth(dst);
			}
			return false;
		}
		else
		{
			return true;
		}
	}
	public static boolean stretchHTo(CANWH wh,float dst,float spd)
	{
		float crth = wh.getHeight();
		float dec = Math.abs(dst - crth);
		if(crth < dst)
		{
			if(dec > spd)
			{
				wh.setHeight(crth + spd);
			}
			else
			{
				wh.setHeight(dst);
			}
			return false;
		}
		else if(crth > dst)
		{
			if(dec > spd)
			{
				wh.setHeight(crth - spd);
			}
			else
			{
				wh.setHeight(dst);
			}
			return false;
		}
		else
		{
			return true;
		}
	}
}
