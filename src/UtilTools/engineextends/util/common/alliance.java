package engineextends.util.common;

import java.util.ArrayList;
import java.util.List;

public class alliance 
{
	List<camps> allianceColor = new ArrayList<camps>();
	List<List<Integer>> alliance=new ArrayList<List<Integer>>();//�����б�
	
	public int playerControlFlag = 1;
	public int MaxAlliance;
	public alliance()
	{
		addCamps(0,"DDDDDD");
		addCamps(1,"2C89C0");
		addCamps(2,"00DD00");
		addCamps(3,"FF0000");
		addCamps(4,"DDDD00");
		addCamps(5,"00DDDD");
		MaxAlliance = 5;
	}
	public boolean hasCamps(int allianceFlag)
	{
		for(camps c:allianceColor)
		{
			if(c.allianceFlag == allianceFlag)
			{
				return true;
			}
		}
		return false;
	}
	public void addCamps(int allianceFlag,String HEXColor)
	{
		if(!hasCamps(allianceFlag))
		{
			allianceColor.add(new camps(allianceFlag,HEXColor));
			alliance.add(allianceFlag, new ArrayList<Integer>());
		}
	}
	public String getCampsColor(int allianceFlag)
	{
		for(camps c:allianceColor)
		{
			if(c.allianceFlag == allianceFlag)
			{
				return c.HEXColor;
			}
		}
		return null;
	}
	public boolean isAlliance(int currentBelongFlag,int allianceBelongFlag)
	{
		if(currentBelongFlag == allianceBelongFlag)
		{
			return true;
		}
		for(Integer Inte:alliance.get(currentBelongFlag))
		{
			if(Inte==allianceBelongFlag)
			{
				return true;
			}
		}
		return false;
	}
	public void addAlliance(Integer currentBelongFlag,Integer allianceBelongFlag)
	{
		if(isAlliance(currentBelongFlag,allianceBelongFlag))
		{
			return;
		}
		alliance.get(currentBelongFlag).add(allianceBelongFlag);
		alliance.get(allianceBelongFlag).add(currentBelongFlag);
	}
	public void removeAlliance(Integer currentBelongFlag,Integer allianceBelongFlag)
	{
		if(!isAlliance(currentBelongFlag,allianceBelongFlag))
		{
			return;
		}
		alliance.get(currentBelongFlag).remove(allianceBelongFlag);
		alliance.get(allianceBelongFlag).remove(currentBelongFlag);
	}
	public void reinitAlliance()
	{
		for(List<Integer> Li:alliance)
		{
			Li.remove(Li);
		}
	}
	public boolean hasAlliance(int allianceFlag)
	{
		if(alliance.get(allianceFlag).isEmpty())
		{
			return false;
		}
		return true;
	}
}
class camps
{
	int allianceFlag;
	String HEXColor;
	public camps(int allianceFlag,String HEXColor)
	{
		this.allianceFlag = allianceFlag;
		this.HEXColor = HEXColor;
	}
}
