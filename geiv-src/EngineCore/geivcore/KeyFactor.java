package geivcore;


public interface KeyFactor {
	public void setKeyListener(KeyListener keyListener);
	public void removeKeyListener();
	public void keyFactor(int keyCode,boolean ispressed);
	//public String getInfo();
}
