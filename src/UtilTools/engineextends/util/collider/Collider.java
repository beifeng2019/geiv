package engineextends.util.collider;

import java.util.LinkedList;
import java.util.List;

public class Collider{
	float frameDx,frameDy,frameWidth,frameHeight;
	
	List<Abshell> shellList;
	List<Hitable> hitList;
	
	public Collider()
	{
		shellList = new LinkedList<Abshell>();
		hitList = new LinkedList<Hitable>();
	}
	public void addAbshell(Abshell shell)
	{
		shellList.add(shell);
	}
	public void addHitable(Hitable hitable)
	{
		hitList.add(hitable);
	}
	public void collideFragment()
	{
		for(Hitable hit:hitList)
		{
			if(hit.isEnable())
			{
				for(Abshell shell:shellList)
				{
					if(shell.isEnable()&&shell.isHit(hit))
					{
						hit.setHitable(true, shell);
						shell.disable();
					}
				}
			}
		}
	}
}
