package geivcore.engineSys.soundsplayer;

import geivcore.KeyFactor;
import geivcore.KeyListener;
import geivcore.R;

import java.io.File;

public class montageTester implements KeyFactor,KeyListener
{
	montageControllor mC;
	KeyListener KeyListener = null;
	public montageTester(R R)
	{
		mC = R.montageControllor;
		this.setKeyListener(this);
		R.keyBoard.pushKeyIO(this);
	}
	@Override
	public void setKeyListener(KeyListener keyListener) {
		this.KeyListener = keyListener;
	}
	@Override
	public void removeKeyListener() {
		this.KeyListener = null;
	}
	@Override
	public void keyFactor(int KeyCode, boolean ispressed) {
		if(KeyListener!=null)
		KeyListener.doKeyBord(this, KeyCode, ispressed);
	}
	@Override
	public void doKeyBord(KeyFactor whom, int keyCode, boolean ispressed) {
		if(ispressed)
		{
			if(keyCode == 37)
			{
				mC.playWAVmontage("." + File.separator + "Data" + File.separator + "Sound" + File.separator + "Effi" + File.separator + "dong.wav");
			}
			else if(keyCode == 39)
			{
				mC.playWAVmontage("." + File.separator + "Data" + File.separator + "Sound" + File.separator + "Effi" + File.separator + "ka.wav");
			}
		}
	}
//	@Override
//	public String getInfo() {
//		return "montageTester";
//	}
}
