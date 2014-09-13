package engineextend.components.views;

import geivcore.enginedata.canonical.CANWH;

public interface GL_TextView extends GL_ComponentView,CANWH{
	public String getViewInfo();
	public void setViewInfo(String info);
}
