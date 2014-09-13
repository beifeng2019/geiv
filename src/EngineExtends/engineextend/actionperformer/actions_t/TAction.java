package engineextend.actionperformer.actions_t;

import geivcore.enginedata.canonical.CANPosition;

public abstract class TAction {
	public int allmsR=0;
	public int Dms=0;
	static int DefaultAllms=1200;
	CANPosition OR=null;
	public int leftTime;
	public int delayTime=0;
	public int STdelayTime=0;
	public int cishu=0;
	public TAction(CANPosition O,int allms)
	{
		allmsR=allms;
		leftTime=allms;
		OR=O;
	}
	public void setDelayTime(int DelayTime)
	{
		this.delayTime+=DelayTime;
		this.STdelayTime+=delayTime;
		allmsR+=DelayTime;
	}
	public abstract void runAct();
	public abstract void reinit();
	public abstract void runFinal();
}
