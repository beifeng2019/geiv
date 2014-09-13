package geivcore;

import java.awt.event.MouseEvent;

public interface MouseFactor {
	public void setMouseListener(MouseListener mouseListener);
	public void removeMouseListener();
	public void mouseFactor(MouseEvent e);
	public boolean isMouseInside();
	public String getInfo();
}
