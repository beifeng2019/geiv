package engineextend.components.views;

import geivcore.enginedata.canonical.CANExPos;
import geivcore.enginedata.canonical.CANPosition;
import geivcore.enginedata.canonical.CANRefPos;

public interface GL_ComponentView extends CANPosition,CANExPos,CANRefPos{
	public void show();
	public void hide();
	public void destroy();
	public boolean isPrintable();
	public boolean isMouseInside();
}
