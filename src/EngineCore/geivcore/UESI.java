package geivcore;

import geivcore.enginedata.obj.Obj;

//���Ϊ�����ϵͳ���õ��ϲ�ӿڣ��Գ������ξ������� �������
/**
 * Universial Engine System Interface
 * @version GDiv 6.0
 * @author Thrblock 三向板砖
 * */
public interface UESI//UperEngineSystemInterface
{
	public static final int BGIndex = 0;
	public static final int GameObjIndex = 1;
	public static final int GameShellIndex = 2;
	
	public static final int OpIndex = 3;
	public static final int UIIndex = 4;
	
	public static final int EffIndex = 5;
	public static final int XRIndex = 6;
	
	//UESI:Universial Engine System Interface
	public UESI getUESI();
	public int getFPS();
	public void miniSizeWindows();
	public void maxSizeWindows();
	//ͼ��
	public Obj creatObj(int DivIndex);
	//��Դ
	public void createShaderProgram(String spName,String vpPath,String fpPath);
	public void preInitTexture(String path);
	public void initMontage(String Path,String Valgroup);
	public void initMontage(String Path);
	public void addMontageSP(String Valgroup,float Val);
	public void setMontageSP(String Valgroup,float Val);
	public float getMontageSP(String Valgroup);
	public void playMontage(String Path);
	/**<p>When a Texture Obj is created,the Texture may
	 *not inited because OPENGL context can not be
	 *confirmed.not OPENGL Paint a Obj until the Texture
	 *can be loaded,thus may cause some timeblock problem
	 *when paint a Obj firstly.
	 *So by calling this method,the Texture can be loaded
	 *before it's firstly used.<br>
	 *��һ��������󱻴���ʱ������OPENGL�����Ļ���ȱʧ�����?�ᱻ���Ʋ�����ֱ����ͼ�ζ���ֱ��
	 *��һ�λ���֮ʱ����ʱ������?��ʹ�������������ĳ��ֿɱ��˸�֪��������
	 *ʹ��reflushExistsTexture��Ԥ��һ����ǰ��������أ����������ʱ�������������ļ���
	 *�����ǵ�һ�λ���֮ʱ��</p>
	 **/
	public void reflushExistsTexture();

	public void initMonBGM(String WAVPath,long BFlag,long EFlag,String Valgroup);
	public void playMonBGM();
	public void pauseMonBGM();
	public void stopMonBGM();
	
	public void addKWordTYPE(String keyID,String FontType,String HEXColor,int FontSize,int FontOpt,int KWidth,int KHeight,int OffsetX,int OffsetB);
	public void addKWordTYPE(String keyID);
	public void setKWord(String keyID,char C,String path);
	//�߼�
	public void addPObjToContainer(Obj O);
	public void remPObjToContainer(Obj O);
	public void addSerialTask(SerialTask ST);
	public void removeSerialTask(SerialTask ST);
	public void wait(int flag);
	public void wait(int flag,int time);
	//IO
	public void addMouseButtonIO(MouseFactor mF);
	public void removeMouseButtonIO(MouseFactor mF);
	public int getCrtMouseX();
	public int getCrtMouseY();
	public boolean getMouseButtonStatus(int MouseButtonCode);
	
	public void pushKeyBoardIO(KeyFactor kF);
	public void popKeyBoardIO();
	public void popAllKeyIO();
	public boolean getKeyStatus(int KeyCode);
	//��ͼ
	public int getScreenWidth();
	public int getScreenHeight();
	
	public void setViewOffsetX(float Offset);
	public void setViewOffsetY(float Offset);
	public void setViewOffsetAngle(float Offset);
	
	public float getViewOffsetX();
	public float getViewOffsetY();
	public float gtViewOffsetAngle();
	
}
