package engineextends.util.tileMap;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class tileMap {
	int[][] tileType;
	final int allWidth,allHeight;
	LinkedList<tileObj> tileMapObjList;
	Semaphore listLock;
	public tileMap(int allWidthCount,int allHeightCount)
	{
		tileType = new int[allWidthCount][allHeightCount];
		this.allWidth = allWidthCount;
		this.allHeight = allHeightCount;
		tileMapObjList = new LinkedList<tileObj>();
		listLock = new Semaphore(1);
	}
	public void setTileType(int TYPE,int i,int j)
	{
		tileType[i][j] = TYPE;
	}
	public int getTileType(int i,int j)
	{
		return tileType[i][j];
	}
	public int getAllWidth()
	{
		return allWidth;
	}
	public int getAllHeight()
	{
		return allHeight;
	}
	public boolean isInsideMap(int i,int j)
	{
		return (i>=0&&i<allWidth)&&(j>=0&&j<allHeight);
	}
	public tileObj getMapObj(int i,int j)
	{
		tileObj result = null;
		if(!isInsideMap(i,j))
		{
			System.out.println("out side range!");
			return null;
		}
		listLock.acquireUninterruptibly();
		for(tileObj tMObj:tileMapObjList)
		{
			if(tMObj.isOnMap(i, j))
			{
				result = tMObj;
				break;
			}
		}
		listLock.release();
		return result;
	}
	public boolean moveObj(tileObj tMObj,int i,int j)
	{
		if(!isInsideMap(i,j))
		{
			return false;
		}
		if(tileMapObjList.contains(tMObj))
		{
			if(tMObj.isPlaceAble(getTileType(i,j))||tMObj.isAllPlaceable())
			{
				tileObj anot = null;
				if((anot = getMapObj(i,j)) != null)
				{
					if(tMObj.isCompatible(anot))
					{
						tMObj.setCrtPos(i, j);
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					tMObj.setCrtPos(i, j);
					return true;
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			System.out.println(tMObj+" is not in map!");
			return false;
		}
	}
	public boolean removeMapObj(tileObj tMObj)
	{
		if(tMObj.isOnMap())
		{
			listLock.acquireUninterruptibly();
			tileMapObjList.remove(tMObj);
			tMObj.setrefMap(null);
			listLock.release();
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean removeMapObj(int i,int j)
	{
		tileObj anot = null;
		if((anot = getMapObj(i,j)) != null)
		{
			listLock.acquireUninterruptibly();
			tileMapObjList.remove(anot);
			anot.setrefMap(null);
			listLock.release();
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean addMapObj(tileObj tMObj,int i,int j)
	{
		boolean result = false;
		if(!isInsideMap(i,j))
		{
			System.out.println("out side range!");
			return result;
		}
		tileObj anot = null;
		if(tMObj.isPlaceAble(getTileType(i,j))||tMObj.isAllPlaceable())
		{
			if((anot = getMapObj(i,j)) != null)
			{
				if(tMObj.isCompatible(anot))
				{
					listLock.acquireUninterruptibly();
					tMObj.setCrtPos(i, j);
					tMObj.initPos(i, j);
					tileMapObjList.add(tMObj);
					listLock.release();
					result = true;
				}
				else
				{
					result = false;
				}
			}
			else
			{
				listLock.acquireUninterruptibly();
				tMObj.setCrtPos(i, j);
				tMObj.initPos(i, j);
				tileMapObjList.add(tMObj);
				listLock.release();
				result = true;
			}
		}
		else
		{
			result = false;
		}
		if(result)
		{
			tMObj.setrefMap(this);
		}
		return result;
	}
}
