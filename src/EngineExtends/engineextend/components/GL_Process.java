package engineextend.components;

import engineextend.components.views.GL_ProcessView;
import geivcore.UESI;

public class GL_Process extends GL_Component{

	GL_ProcessView View;
	public GL_Process(UESI UES, GL_ProcessView View) {
		super(UES, View);
		this.View = View;
	}
	public void setProcess(float process)
	{
		View.setProcess(process);
	}
	public float getProcess()
	{
		return View.getProcess();
	}
}
