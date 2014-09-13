package geivcore.engineSys.serialTaskcontroller;


import geivcore.R;
import geivcore.SerialTask;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;


public class SerialTaskController 
{
	R R;
	private int clock = Integer.MIN_VALUE;
	private boolean showMTime = false;
	private long bTime;
	private Set<SerialTask> STList;
	private Set<SerialTask> removeCache;
	private Set<SerialTask> addCache;
	
	Semaphore STLock=new Semaphore(1);
	
	public SerialTaskController(R R)
	{
		STList = new HashSet<SerialTask>();
		removeCache = new HashSet<SerialTask>();
		addCache = new HashSet<SerialTask>();
		this.R = R;
	}
	public void runAllSerialTask()
	{
		if(clock < Integer.MAX_VALUE)
		{
			clock++;
		}
		else
		{
			clock = Integer.MIN_VALUE;
		}
		if(showMTime)
		{
			bTime = System.nanoTime();
		}
		for(SerialTask ST:STList)
		{
			ST.Serial(clock);
		}
		STLock.acquireUninterruptibly();
		STList.removeAll(removeCache);
		removeCache.removeAll(removeCache);
		STList.addAll(addCache);
		addCache.removeAll(addCache);
		STLock.release();
		if(showMTime)
		{
			System.out.println("ALL SerialTask Use Time:"+(System.nanoTime() - bTime)/1000000.0+" ms");
		}
	}
	public void addSerialTask(SerialTask ST)
	{
		STLock.acquireUninterruptibly();
		addCache.add(ST);
		STLock.release();
	}
	public void removeSerialTask(SerialTask ST)
	{
		STLock.acquireUninterruptibly();
		removeCache.add(ST);
		STLock.release();
	}
}
