package engineextend.actionperformer;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import engineextend.actionperformer.actions_t.TAction;
import geivcore.R;
import geivcore.SerialTask;

public class T_Arithmetor implements SerialTask
{
	R R;

	List<TAction> acts = new ArrayList<TAction>(128);
	Semaphore actsLock=new Semaphore(1);
	
	boolean showInfo=false;
	
	public T_Arithmetor(R R)
	{
		this.R=R;
		//R.T_AL=this;
		R.SC.addSerialTask(this);
	}
	public void addActions(TAction Ga)
	{
		actsLock.acquireUninterruptibly();
		acts.add(Ga);
		Ga.Dms=R.Dms;
		actsLock.release();
	}
	public boolean hasActs()
	{
		return !acts.isEmpty();
	}
	@Override
	public void Serial(int clock) 
	{
		if(!acts.isEmpty())
		{
			actsLock.acquireUninterruptibly();
			for(TAction ac : acts)
			{
				if(ac.delayTime>0)
				{
					if(ac.delayTime>R.Dms)
					{
						ac.delayTime-=R.Dms;
					}
					else
					{
						ac.delayTime=0;
					}
				}
				else
				{
					if(ac.leftTime>0)
					{
					ac.runAct();
					ac.leftTime-=R.Dms;
					}
				}
				ac.cishu+=1;
			}
			for(int i=0;i<acts.size();i++)
			{  
	            if(R.Dms*acts.get(i).cishu>=acts.get(i).allmsR)
	            {  
	            	acts.get(i).leftTime=acts.get(i).allmsR-acts.get(i).STdelayTime;
	            	acts.get(i).delayTime=acts.get(i).STdelayTime;
	            	acts.get(i).runFinal();
	            	acts.get(i).cishu=0;
	                acts.remove(i);
	                i--;
	            }  
	        }
			actsLock.release();
		}//WHILE
	}
}
