package engineextends.util.actiontemplet;

public class TempValue {
	public static float getStpValue(float value,float dst,float stp)
	{
		if(Math.abs(value - dst) < stp)
		{
			return dst;
		}
		else if(value > dst)
		{
			return value - stp;
		}
		else
		{
			return value + stp;
		}
	}
}
