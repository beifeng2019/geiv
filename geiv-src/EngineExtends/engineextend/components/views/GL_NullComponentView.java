package engineextend.components.views;

import geivcore.UESI;
import geivcore.enginedata.obj.Obj;

public class GL_NullComponentView extends Obj implements GL_ComponentView{
	public GL_NullComponentView(UESI UES) {
		super((geivcore.R)UES,UESI.UIIndex);
	}
}
