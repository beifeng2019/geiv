package engineextends.util.background;

import engineextends.util.common.CMVal;
import geivcore.SerialTask;
import geivcore.UESI;
import geivcore.enginedata.obj.Obj;

public abstract class BackGroundChanger implements SerialTask
{
	UESI UES;
	Obj BgDisp;
	int status = CMVal.ST_WAIT;
	String changeStr = null;
	public BackGroundChanger(UESI UES,String DefaultPath)
	{
		this.UES = UES;
		BgDisp = UES.creatObj(UESI.BGIndex);
		initImgMode(UES,BgDisp,DefaultPath);
		//BgDisp.addGLImage(0, 0, DefaultPath);
		BgDisp.setAlph(0.0f);
		BgDisp.show();
		UES.addSerialTask(this);
	}
	public abstract float getSPD();
	public abstract void initImgMode(UESI UES,Obj imgObj,String Path);
	public abstract void setImgMode(UESI UES,Obj imgObj,String Path);
	public void show()
	{
		status = CMVal.ST_TERIN;
	}
	public void setBGPath(String Path)
	{
		changeStr = Path;
		status = CMVal.ST_TEROUT;
	}
	public void hide()
	{
		changeStr = null;
		status = CMVal.ST_TEROUT;
	}
	@Override
	public void Serial(int clock) {
		switch(status)
		{
			case CMVal.ST_WAIT:{}break;
			case CMVal.ST_TERIN:{
				if(BgDisp.getAlph() < 1.0f)
				{
					BgDisp.setAlph(BgDisp.getAlph() + getSPD());
				}
				else
				{
					status = CMVal.ST_WAIT;
				}
			}break;
			case CMVal.ST_TEROUT:{
				if(BgDisp.getAlph() > 0.0f)
				{
					BgDisp.setAlph(BgDisp.getAlph() - getSPD());
				}
				else
				{
					if(this.changeStr!=null)
					{
						status = CMVal.ST_CHANGE;
					}
					else
					{
						status = CMVal.ST_WAIT;
					}
				}
			}break;
			case CMVal.ST_CHANGE:{
				setImgMode(UES,BgDisp,changeStr);
				status = CMVal.ST_TERIN;
			}break;
		}
	}
}
