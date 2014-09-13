package engineextends.util.rollMap;

import java.util.LinkedList;
import java.util.List;

public class RollMap implements RollMapInterface{
	List<LineLimit> limits;
	public RollMap()
	{
		limits = new LinkedList<LineLimit>();
	}
	public void addLimit(LineLimit lineLimit)
	{
		synchronized(limits)
		{
			limits.add(lineLimit);
		}
	}
	public void clearAllLimit()
	{
		synchronized(limits)
		{
			limits.removeAll(limits);
		}
	}
	@Override
	public float getMinAbleX(float Dx, float Dy) {
		float result = Float.MIN_VALUE;
		float tempRes;
		synchronized(limits)
		{
			for(LineLimit lmt:limits)
			{
				tempRes = lmt.getMinAbleX(Dx, Dy);
				result = tempRes>result?tempRes:result;
			}
		}
		return result;
	}
	@Override
	public float getMaxAbleX(float Dx, float Dy) {
		float result = Float.MAX_VALUE;
		float tempRes;
		synchronized(limits)
		{
			for(LineLimit lmt:limits)
			{
				tempRes = lmt.getMaxAbleX(Dx, Dy);
				result = tempRes<result?tempRes:result;
			}
		}
		return result;
	}
	@Override
	public float getMinAbleY(float Dx, float Dy) {
		float result = Float.MIN_VALUE;
		float tempRes;
		synchronized(limits)
		{
			for(LineLimit lmt:limits)
			{
				tempRes = lmt.getMinAbleY(Dx, Dy);
				result = tempRes>result?tempRes:result;
			}
		}
		return result;
	}
	@Override
	public float getMaxAbleY(float Dx, float Dy) {
		float result = Float.MAX_VALUE;
		float tempRes;
		synchronized(limits)
		{
			for(LineLimit lmt:limits)
			{
				tempRes = lmt.getMaxAbleY(Dx, Dy);
				result = tempRes<result?tempRes:result;
			}
		}
		return result;
	}
}
