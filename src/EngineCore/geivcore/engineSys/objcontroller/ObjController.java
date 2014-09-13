package geivcore.engineSys.objcontroller;

//import java.util.ArrayList;

import geivcore.R;
import geivcore.UESI;
import geivcore.enginedata.obj.Obj;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;


public class ObjController 
{
	R R;
	public boolean useDBUGEPad = false;
	List<List<Obj>> Mlist= new LinkedList<List<Obj>>();
	//��ζ��壺
	public final int MAX_DIV_INDEX;
	List<Obj> BGRegester = new LinkedList<Obj>();
	public final int BGIndex = UESI.BGIndex;
	
	List<Obj> GameObjSRegester = new LinkedList<Obj>();
	public final int GameObjSIndex = UESI.GameObjIndex;
	
	List<Obj> GameShellRegester = new LinkedList<Obj>();
	public final int GameShellIndex = UESI.GameShellIndex;
	
	List<Obj> ObjSOperater = new LinkedList<Obj>();
	public final int OpIndex = UESI.OpIndex;
	
	List<Obj> UIRegester = new LinkedList<Obj>();
	public final int UIIndex = UESI.UIIndex;
	
	List<Obj> EffRegester = new LinkedList<Obj>();
	public final int EffIndex = UESI.EffIndex;
	
	List<Obj> XRRegester = new LinkedList<Obj>();
	public final int XRIndex = UESI.XRIndex;
	
	List<Semaphore> SemapList = new LinkedList<Semaphore>();
	final int maxELArq = 10;
	public ObjController(R R)
	{
		this.R = R;
		//��ι��أ�
		Mlist.add(BGRegester);
		Mlist.add(GameObjSRegester);
		Mlist.add(GameShellRegester);
		Mlist.add(ObjSOperater);
		Mlist.add(UIRegester);
		Mlist.add(EffRegester);
		Mlist.add(XRRegester);
		for(int i = 0;i < Mlist.size();i++)
		{
			SemapList.add(new Semaphore(maxELArq));
		}
		MAX_DIV_INDEX = Mlist.size() - 1;
	}
	public void add(Obj O)
	{
		SemapList.get(O.TYPE).acquireUninterruptibly(maxELArq);
		Mlist.get(O.TYPE).add(O);
		SemapList.get(O.TYPE).release(maxELArq);
	}
	public void rem(Obj O)
	{
		O.printable = false;
		O.destroyFlag = true;

		SemapList.get(O.TYPE).acquireUninterruptibly(maxELArq);
		Mlist.get(O.TYPE).remove(O);
		SemapList.get(O.TYPE).release(maxELArq);
	}
	public List<Obj> acquireDivList(int DivIndex)
	{
		SemapList.get(DivIndex).acquireUninterruptibly();
		return Mlist.get(DivIndex);
	}
	public void releaseDivList(int DivIndex)
	{
		SemapList.get(DivIndex).release();
	}
}
