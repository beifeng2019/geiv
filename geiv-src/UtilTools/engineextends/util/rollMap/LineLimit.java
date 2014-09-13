package engineextends.util.rollMap;

public class LineLimit implements RollMapInterface{
	float Dx1,Dy1,Dx2,Dy2;
	boolean polarity;
	public LineLimit(float Dx1,float Dy1,float Dx2,float Dy2)
	{
		this(Dx1,Dy1,Dx2,Dy2,false);
	}
	public LineLimit(float Dx1,float Dy1,float Dx2,float Dy2,boolean polarity)
	{
		this.Dx1 = Dx1;
		this.Dx2 = Dx2;
		this.Dy1 = Dy1;
		this.Dy2 = Dy2;
		
		this.polarity = polarity;
	}
	public float getDx1()
	{
		return Dx1;
	}
	public float getDx2()
	{
		return Dx2;
	}
	public float getDy1()
	{
		return Dy1;
	}
	public float getDy2()
	{
		return Dy2;
	}
	public void setDx1(float Dx1)
	{
		this.Dx1 = Dx1;
	}
	public void setDx2(float Dx2)
	{
		this.Dx2 = Dx2;
	}
	public void setDy1(float Dy1)
	{
		this.Dy1 = Dy1;
	}
	public void setDy2(float Dy2)
	{
		this.Dy2 = Dy2;
	}
	public boolean isEffect(float Dx,float Dy)
	{
		if(!isInsideXArea(Dx)&&!isInsideYArea(Dy))
		{
			return false;
		}
		else
		{
			if(Dx1 == Dx2)
			{
				return polarity?Dx >= Dx1:!(Dx > Dx1);
			}
			else if(Dy1 == Dy2)
			{
				return polarity?Dy >= Dy1:!(Dy > Dy1);
			}
			else if(isInsideXArea(Dx))
			{
				float Y = getYByX(Dx);
				return polarity?Dy >= Y:!(Dy > Y);
			}
			else
			{
				float X = getXByY(Dy);
				return polarity?Dx >= X:!(Dx > X);
			}
		}
	}
	@Override
	public float getMinAbleX(float Dx, float Dy) {
		if(isEffect(Dx,Dy)&&isInsideYArea(Dy))
		{
			float X = this.getXByY(Dy);
			if(X <= Dx)
			{
				return X;
			}
		}
		return Float.MIN_VALUE;
	}
	@Override
	public float getMaxAbleX(float Dx, float Dy) {
		if(isEffect(Dx,Dy)&&isInsideYArea(Dy))
		{
			float X = this.getXByY(Dy);
			if(X >= Dx)
			{
				return X;
			}
		}
		return Float.MAX_VALUE;
	}
	@Override
	public float getMinAbleY(float Dx, float Dy) {
		if(isEffect(Dx,Dy)&&isInsideXArea(Dx))
		{
			float Y = this.getYByX(Dx);
			if(Y <= Dy)
			{
				return Y;
			}
		}
		return Float.MIN_VALUE;
	}
	@Override
	public float getMaxAbleY(float Dx, float Dy) {
		if(isEffect(Dx,Dy)&&isInsideXArea(Dx))
		{
			float Y = this.getYByX(Dx);
			if(Y >= Dy)
			{
				return Y;
			}
		}
		return Float.MAX_VALUE;
	}
	private boolean isInsideXArea(float Dx)
	{
		return (Dx <= Math.max(Dx1, Dx2)&&Dx >= Math.min(Dx1, Dx2));
	}
	private boolean isInsideYArea(float Dy)
	{
		return (Dy <= Math.max(Dy1, Dy2)&&Dy >= Math.min(Dy1, Dy2));
	}
	private float getXByY(float Dy)
	{
		if(isInsideYArea(Dy))
		{
			return ((Dy - Dy2)*(Dx2 - Dx1) + (Dy2 - Dy1)*Dx2)/(Dy2 - Dy1);
		}
		else
		{
			return Float.NaN;
		}
	}
	private float getYByX(float Dx)
	{
		if(isInsideXArea(Dx))
		{
			return ((Dy2 - Dy1)*(Dx - Dx2)+Dy2*(Dx2 - Dx1))/(Dx2 - Dx1);
		}
		else
		{
			return Float.NaN;
		}
	}
}
