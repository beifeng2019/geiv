package geivcore;

public class DefaultFactor implements KeyFactor{
	KeyListener keyListener = null;
	@Override
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	@Override
	public void removeKeyListener() {
		this.keyListener = null;
	}

	@Override
	public void keyFactor(int keyCode, boolean ispressed) {
		if(keyListener != null)
		{
			keyListener.doKeyBord(this, keyCode, ispressed);
		}
	}

//	@Override
//	public String getInfo() {
//		return "DefaultFactor";
//	}

}
