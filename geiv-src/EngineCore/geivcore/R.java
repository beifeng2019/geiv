package geivcore;

import engineextend.actionperformer.T_Arithmetor;
import geivcore.engineSys.io.keyBoard;
import geivcore.engineSys.io.mouse;
import geivcore.engineSys.keywordcontrollor.KeyWordControllor;
import geivcore.engineSys.objcontroller.ObjController;
import geivcore.engineSys.screenpainter.Painter;
import geivcore.engineSys.serialTaskcontroller.SerialTaskController;
import geivcore.engineSys.shadercontroller.ShaderController;
import geivcore.engineSys.soundsplayer.montBGM;
import geivcore.engineSys.soundsplayer.montageControllor;
import geivcore.engineSys.texturecontrollor.TextureControllor;
import geivcore.engineSys.viewcontroller.ViewController;
import geivcore.enginedata.obj.Obj;

import javax.media.opengl.GL;
import javax.swing.JFrame;

import jogl.JOGL;

import com.thrblock.util.ProStorage;

public class R implements UESI {
	// /////////Ԥ���峣��//
	ProStorage storage;
	// ȫ�����ÿ��Զ�ȡ��� ����ʹ�����޸�
	public int FPS = 60;
	public int Dms = 17;
	public int ScreenW = 800, ScreenH = 600;
	public float Depth = 0f;
	String Title = "GDiv 6";
	boolean fixWindows = true;
	// ////////����ȥ/////////////////////
	// �ӽṹ
	public ViewController VC;
	public SerialTaskController SC;
	// ���߽ṹ
	// public alliance alliance;
	// �ϲ�Ԥ��
	public T_Arithmetor T_AL;
	// �¼ܹ� ����/////////////////////////////////
	// ��ͼ���Painter
	public Painter Painter;
	public JOGL MainFrame;
	// IOϵͳ
	public keyBoard keyBoard;
	public mouse mouse;
	// ͼ��ϵͳ
	public ObjController OC;
	// ����ϵͳ
	public TextureControllor TC;
	// �û���ɫ��
	public ShaderController ShaderCont;
	// �ֿ�ϵͳ
	public KeyWordControllor KC;
	// ��Դϵͳ
	public montBGM mBGM;

	// public MP3Player BGM;
	public montageControllor montageControllor;

	public R(String proPath) {
		storage = new ProStorage();
		storage.loadProperty(proPath);
		FPS = Integer.parseInt(storage.getProperty("FPS"));
		Dms = 1000 / FPS;
		ScreenW = Integer.parseInt(storage.getProperty("ScreenWidth"));
		ScreenH = Integer.parseInt(storage.getProperty("ScreenHeight"));
		Title = storage.getProperty("Title");
		fixWindows = Boolean.valueOf(storage.getProperty("FixWindows"));
		System.out.println("GDivConfig found:FPS-" + FPS + ",SW:" + ScreenW
				+ ",SH:" + ScreenH + ",FIX:" + fixWindows);
		init();
	}

	public R() {
		init();
	}

	private void init() {
		OC = new ObjController(this);
		VC = new ViewController(this);
		SC = new SerialTaskController(this);
		TC = new TextureControllor(this);
		KC = new KeyWordControllor(this);
		ShaderCont = new ShaderController(this);
		ShaderCont.initSysShaders();
		mBGM = new montBGM(this);
		MainFrame = new JOGL(this, Title , fixWindows);
		Painter = MainFrame.listener;
		montageControllor = new montageControllor();
		T_AL = new T_Arithmetor(this);

		MainFrame.showFrame();
		MainFrame.startDraw();
	}

	public void wait(int flag) {
		if (flag == 0) {
			while (T_AL.hasActs()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public void wait(int flag, int allms) {
		if (flag == 3) {
			try {
				Thread.sleep(allms);
			} catch (InterruptedException e) {
			}
		}
	}

	public boolean needAttachShader() {
		return ShaderCont.needCreateProgram();
	}

	public boolean attachShader(GL gl) {
		return ShaderCont.buildProgram(gl);
	}

	public int getShaderProgram(String programName) {
		return ShaderCont.getProgram(programName);
	}

	@Override
	public void playMontage(String Path) {
		montageControllor.playWAVmontage(Path);
	}

	@Override
	public void initMontage(String Path, String Valgroup) {
		montageControllor.initWAVmontage(Path, Valgroup);
	}

	@Override
	public void initMontage(String Path) {
		initMontage(Path, montageControllor.DefaultGroup);
	}

	@Override
	public void addMontageSP(String Valgroup, float Val) {
		montageControllor.addWAVSP(Valgroup, Val);
	}

	@Override
	public void setMontageSP(String Valgroup, float Val) {
		montageControllor.setWAVSP(Valgroup, Val);
	}

	@Override
	public float getMontageSP(String Valgroup) {
		return montageControllor.getSPValByGroup(Valgroup);
	}

	@Override
	public void initMonBGM(String WAVPath, long BFlag, long EFlag,
			String ValGroup) {
		mBGM.LoadWAVfile(WAVPath, BFlag, EFlag, ValGroup);
	}

	@Override
	public void playMonBGM() {
		mBGM.WAV_play();
	}

	@Override
	public void pauseMonBGM() {
		mBGM.WAV_pause();
	}

	@Override
	public void stopMonBGM() {
		mBGM.WAV_stop();
	}

	@Override
	public void addSerialTask(SerialTask ST) {
		SC.addSerialTask(ST);
	}

	@Override
	public void removeSerialTask(SerialTask ST) {
		SC.removeSerialTask(ST);
	}

	@Override
	public void addMouseButtonIO(MouseFactor mF) {
		mouse.mouseBotton.addMouseRefObj(mF);
	}

	@Override
	public void removeMouseButtonIO(MouseFactor mF) {
		mouse.mouseBotton.removeMouseRefObj(mF);
	}

	@Override
	public int getCrtMouseX() {
		return mouse.mouseBotton.MouseX;
	}

	@Override
	public int getCrtMouseY() {
		return mouse.mouseBotton.MouseY;
	}

	@Override
	public boolean getMouseButtonStatus(int MouseButtonCode) {
		return mouse.mouseBotton.MouseInfo.get(MouseButtonCode);
	}

	@Override
	public void pushKeyBoardIO(KeyFactor kF) {
		keyBoard.pushKeyIO(kF);
	}

	@Override
	public void popKeyBoardIO() {
		keyBoard.popKeyIO();
	}

	@Override
	public void popAllKeyIO() {
		keyBoard.popAllKeyIO();
	}

	@Override
	public boolean getKeyStatus(int KeyCode) {
		return keyBoard.getKeyStatus(KeyCode);
	}

	@Override
	public void setViewOffsetX(float Offset) {
		VC.viewerTransX = Offset;
	}

	@Override
	public void setViewOffsetY(float Offset) {
		VC.viewerTransY = Offset;
	}

	@Override
	public void setViewOffsetAngle(float Offset) {
		VC.viewerAngle = Offset;
	}

	@Override
	public float getViewOffsetX() {
		return VC.viewerTransX;
	}

	@Override
	public float getViewOffsetY() {
		return VC.viewerTransY;
	}

	@Override
	public float gtViewOffsetAngle() {
		return VC.viewerAngle;
	}

	@Override
	public int getScreenWidth() {
		return ScreenW;
	}

	@Override
	public int getScreenHeight() {
		return ScreenH;
	}

	@Override
	public UESI getUESI() {
		return this;
	}

	@Override
	public void addKWordTYPE(String keyID, String FontType, String HEXColor,
			int FontSize, int FontOpt, int KWidth, int KHeight, int OffsetX,
			int OffsetB) {
		KC.addKWordTYPE(keyID, FontType, HEXColor, FontSize, FontOpt, KWidth,
				KHeight, OffsetX, OffsetB);
	}

	@Override
	public void reflushExistsTexture() {
		Painter.loadAllUnloadTexture();
	}

	@Override
	public void preInitTexture(String path) {
		TC.autoInitImg(path);
	}

	@Override
	public void addPObjToContainer(Obj O) {
		OC.add(O);
	}

	@Override
	public void remPObjToContainer(Obj O) {
		OC.rem(O);
	}

	@Override
	public Obj creatObj(int DivIndex) {
		return new Obj(this, DivIndex);
	}

	@Override
	public void addKWordTYPE(String keyID) {
		KC.addKWordTYPE(keyID);
	}

	@Override
	public void setKWord(String keyID, char C, String path) {
		KC.setKWordPath(keyID, C, path);
	}

	@Override
	public int getFPS() {
		return FPS;
	}

	@Override
	public void createShaderProgram(String spName, String vpPath, String fpPath) {
		ShaderCont.addShaderProgram(spName, vpPath, fpPath);
	}

	@Override
	public void miniSizeWindows() {
		this.MainFrame.setExtendedState(JFrame.ICONIFIED);
	}

	@Override
	public void maxSizeWindows() {
		this.MainFrame.setExtendedState(JFrame.NORMAL);
	}
}
