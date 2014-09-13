package engineextend.crowdcontroller;

import geivcore.SerialTask;
import geivcore.UESI;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;



public class CrowdController implements SerialTask
{
	UESI UES;
	Semaphore ListSph = new Semaphore(1);
	Semaphore runSph = new Semaphore(1);
	//List<Individual> indList = new LinkedList<Individual>();
	Set<Individual> indList = new HashSet<Individual>();
	Set<Individual> runSet = new HashSet<Individual>();
	public CrowdController(UESI UES,boolean defaultSerial)
	{
		this.UES = UES;
		if(defaultSerial)
		{
			UES.addSerialTask(this);
		}
	}
	public int countAvailible()
	{
		int res = 0;
		ListSph.acquireUninterruptibly();
		for(Individual ind:indList)
		{
			if(ind.isAvalible())
			{
				res++;
			}
		}
		ListSph.release();
		return res;
	}
	public int countUnAvailible()
	{
		int res = 0;
		ListSph.acquireUninterruptibly();
		for(Individual ind:indList)
		{
			if(!ind.isAvalible())
			{
				res++;
			}
		}
		ListSph.release();
		return res;
	}
	public Individual getUnAvailible()
	{
		Individual result = null;
		ListSph.acquireUninterruptibly();
		for(Individual ind:indList)
		{
			if(!ind.isAvalible())
			{
				result = ind;
				break;
			}
		}
		ListSph.release();
		if(result == null)
		{
			//System.out.println(this.toString()+" Report: not enough individuals!"+" TYPES:"+indList.iterator().next().getClass().toString());
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public <T> T getAvailible(T type)
	{
		Individual ind = getAvailible();
		if(type.getClass().equals(ind.getClass()))
		{
			return (T)ind;
		}
		else
		{
			return null;
		}
	}
	public Individual getAvailible()
	{
		Individual result = null;
		ListSph.acquireUninterruptibly();
		for(Individual ind:indList)
		{
			if(ind.isAvalible())
			{
				result = ind;
				break;
			}
		}
		ListSph.release();
		if(result == null)
		{
			//System.out.println(this.toString()+" Report: not enough individuals!");
		}
		return result;
	}
	public void addIndividual(Individual ind)
	{
		ListSph.acquireUninterruptibly();
		indList.add(ind);
		ListSph.release();
	}
	public void destroyAllInd()
	{
		ListSph.acquireUninterruptibly();
		for(Individual ind:indList)
		{
			ind.destroy();
		}
		ListSph.release();
	}
	public void finishAllInd(int src)
	{
		ListSph.acquireUninterruptibly();
		for(Individual ind:indList)
		{
			ind.finish(src);
		}
		ListSph.release();
	}
	public void finishAllInd()
	{
		ListSph.acquireUninterruptibly();
		for(Individual ind:indList)
		{
			ind.finish(Individual.SRC_OUTER);
		}
		ListSph.release();
	}
	@Override
	public void Serial(int clock) 
	{
		runSph.acquireUninterruptibly();
		runSet.removeAll(runSet);
		ListSph.acquireUninterruptibly();
		runSet.addAll(indList);
		ListSph.release();
		for(Individual ind:runSet)
		{
			if(!ind.isAvalible())
			{
				ind.doStp(clock);
			}
		}
		runSph.release();
	}
}

