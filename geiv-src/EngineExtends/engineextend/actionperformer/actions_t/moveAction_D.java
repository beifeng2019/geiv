package engineextend.actionperformer.actions_t;

import geivcore.enginedata.canonical.CANPosition;

public class moveAction_D extends TAction
{
	float VxR=0,VyR=0;
	float Dx,Dy;
	float Dstx,Dsty;
	/**
	 * GAction moveAction_D(Obj O,int allms,float Dstx,float Dsty)
	 * moveAction_D���ڽ���������O��λ������ȵ�ָ��ֵ
	 * @param O
	 * :��GAciton�Ĳ�������
	 * @param allms
	 * :��GAction��AL�д�����ʱ�䣬��λms
	 * @param Dstx
	 * :ָ���ĺ���꣨��λ���أ�
	 * @param Dsty
	 * :ָ��������꣨��λ���أ�
	 * */
	public moveAction_D(CANPosition O,int allms,float Dstx,float Dsty)
	{
		super(O,allms);
		Dx=OR.getDx();
		Dy=OR.getDy();
		this.Dstx=Dstx;
		this.Dsty=Dsty;
		leftTime=allms;
	}
	public void runAct() {
		reinit();
		Dx+=VxR;
		Dy+=VyR;
		
		OR.setDx(Dx);
		OR.setDy(Dy);

		if(this.OR!=null)
		{
			if(leftTime!=0)
			{
			VxR=((Dstx-Dx)*Dms/leftTime);//�ٶȲ���! ����Ҫ����������
			VyR=((Dsty-Dy)*Dms/leftTime);
			}
		}
	}
	public void reinit() 
	{
			Dx=OR.getDx();
			Dy=OR.getDy();
	}
	@Override
	public void runFinal() 
	{
		OR.setDx(Dstx);
		OR.setDy(Dsty);
	}
}