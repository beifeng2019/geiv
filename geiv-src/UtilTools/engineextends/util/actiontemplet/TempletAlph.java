package engineextends.util.actiontemplet;

import geivcore.enginedata.canonical.CANAlph;
import geivcore.enginedata.obj.Obj;

public class TempletAlph {
	public static boolean tearInAll(Obj alph,float maxAlp,float alpSpd)
	{
		if(alph.getAlph() < maxAlp&&maxAlp <= 1.0f)
		{
			alph.setAllAlph(alph.getAlph() + alpSpd);
			return false;
		}
		else 
		{
			return true;
		}
	}
	public static boolean tearOutAll(Obj alph,float minAlp,float alpSpd)
	{
		if(alph.getAlph() > minAlp&&minAlp >= 0.0f)
		{
			alph.setAllAlph(alph.getAlph() - alpSpd);
			return false;
		}
		else 
		{
			return true;
		}
	}
	public static boolean tearIn(CANAlph alph,float maxAlp,float alpSpd)
	{
		if(alph.getAlph() < maxAlp&&maxAlp <= 1.0f)
		{
			alph.setAlph(alph.getAlph() + alpSpd);
			return false;
		}
		else 
		{
			return true;
		}
	}
	public static boolean tearOut(CANAlph alph,float minAlp,float alpSpd)
	{
		if(alph.getAlph() > minAlp&&minAlp >= 0.0f)
		{
			alph.setAlph(alph.getAlph() - alpSpd);
			return false;
		}
		else 
		{
			return true;
		}
	}
	public static boolean tearLinearRound(CANAlph alph,float minAlp,float maxAlp,float stp,boolean flag)
	{
		if(flag){
			if(alph.getAlph() < maxAlp)
			{
				alph.setAlph(alph.getAlph() + stp);
				return flag;
			}
			else
			{
				alph.setAlph(maxAlp);
				return !flag;
			}
		}
		else
		{
			if(alph.getAlph() > minAlp)
			{
				alph.setAlph(alph.getAlph() - stp);
				return flag;
			}
			else
			{
				alph.setAlph(minAlp);
				return !flag;
			}
		}
	}
}
