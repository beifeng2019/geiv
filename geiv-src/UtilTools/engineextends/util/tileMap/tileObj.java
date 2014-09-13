package engineextends.util.tileMap;

import java.util.HashSet;
import java.util.Set;

public abstract class tileObj{
	int i = 0,j = 0;
	int TYPE;
	Set<Integer> Compatible;
	Set<Integer> Placeable;
	boolean isAllNeg = false;
	boolean isAllCom = false;
	boolean isAllPlace = false;
	tileMap refMap = null;
	public tileObj(int TYPE)
	{
		this.TYPE = TYPE;
		Compatible = new HashSet<Integer>();
		Placeable = new HashSet<Integer>();
	}
	public tileObj(int TYPE,boolean isNeg,boolean isCom,boolean isPla)
	{
		this(TYPE);
		this.isAllNeg = isNeg;
		this.isAllCom = isCom;
		this.isAllPlace = isPla;
	}
	public void setrefMap(tileMap map)
	{
		this.refMap = map;
	}
	public tileMap getrefMap()
	{
		return refMap;
	}
	public boolean isOnMap()
	{
		return refMap != null;
	}
	public void addCompatible(int TYPE)
	{
		Compatible.add(TYPE);
	}
	public void addPlaceable(int TYPE)
	{
		Placeable.add(TYPE);
	}
	public boolean isOnMap(int i,int j)
	{
		return (this.i == i&&this.j == j);
	}
	public void setCrtPos(int i,int j)
	{
		this.i = i;
		this.j = j;
	}
	public abstract void initPos(int i,int j);
	public int getCrtX()
	{
		return i;
	}
	public int getCrtY()
	{
		return j;
	}
	public boolean isAllCompatible()
	{
		return this.isAllCom;
	}
	public boolean isAllNegCompatible()
	{
		return this.isAllNeg;
	}
	public boolean isAllPlaceable()
	{
		return this.isAllPlace;
	}
	public boolean isCompatible(tileObj another)
	{
		if(another.isAllNegCompatible()||this.isAllNegCompatible())
		{
			return false;
		}
		else if(another.isAllCompatible()||this.isAllCompatible())
		{
			return true;
		}
		else
		{
			return Compatible.contains(another.getType());
		}
	}
	public boolean isPlaceAble(int TILETYPE)
	{
		return (this.isAllPlaceable()||Placeable.contains(TILETYPE));
	}
	public int getType()
	{
		return TYPE;
	}
	public boolean tryMoveto(int i,int j)
	{
		if(this.isOnMap())
		{
			return refMap.moveObj(this, i, j);
		}
		else
		{
			System.out.println(this+"not on map!");
			return false;
		}
	}
}
