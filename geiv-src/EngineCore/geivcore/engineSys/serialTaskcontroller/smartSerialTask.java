package geivcore.engineSys.serialTaskcontroller;

import geivcore.SerialTask;
import geivcore.UESI;

public abstract class smartSerialTask implements SerialTask
{
	UESI UES;
	int allTime;
	public smartSerialTask(UESI UES,boolean startRun)
	{
		this.UES = UES;
		if(startRun)
		{
			UES.addSerialTask(this);	
		}
	}
	public void startSerial()
	{
		UES.addSerialTask(this);
	}
	public void stopSerial()
	{
		UES.removeSerialTask(this);
	}
	public void delaySerial(int allms)
	{
		allTime = allms;
	}
	public void Serial(int clock)
	{
		if(this.allTime > 0)
		{
			allTime -= 17;
			return;
		}
	}
}
