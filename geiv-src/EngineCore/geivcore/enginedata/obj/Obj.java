package geivcore.enginedata.obj;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.media.opengl.GL;

import com.thrblock.util.ChineseConfirmer;

import engineextend.actionperformer.actions_t.TAction;
import engineextend.actionperformer.actions_t.moveAction_D;
import geivcore.KeyFactor;
import geivcore.KeyListener;
import geivcore.MouseFactor;
import geivcore.MouseListener;
import geivcore.R;
import geivcore.engineSys.shadercontroller.ShaderController;
import geivcore.enginedata.canonical.*;

public class Obj implements MouseFactor, KeyFactor, CANKeySet, CANLineWidth,
		CANPosition, CANAlph, CANAngle, CANCentral, CANCollide, CANColor,
		CANFill, CANFont, CANPath, CANPositionMPoints, CANSquare, CANTheta,
		CANWH, CANReferance, CANExPos, CANRefPos {
	R R;
	public int TYPE;
	public boolean printable = false;// 是否绘制
	public boolean destroyFlag = false;// 是否需要清除
	public boolean customMix = false;// 是否使用自定义混合模式
	int mixArg0, mixArg1;//自定义混合模式参数
	public ReentrantLock addLock;
	protected List<PObj> subPObjList;

	KeyListener keyListener;
	MouseListener mouseListener;

	public Obj(R R, int PainterIndex) {
		this.R = R;
		this.TYPE = PainterIndex;
		addLock = new ReentrantLock();
		subPObjList = new ArrayList<PObj>();
		R.OC.add(this);
	}

	// ////////////ϵͳ����/////////
	public List<PObj> getPObjList() {
		addLock.lock();
		return subPObjList;
	}

	public void relasePObjList() {
		addLock.unlock();
	}

	public boolean isPrintable() {
		return this.printable;
	}

	public void show() {
		this.printable = true;
	}

	public void hide() {
		this.printable = false;
	}

	public void destroy() {
		R.OC.rem(this);
	}

	// ///////�������///////////
	public int getTopDivIndex() {
		return subPObjList.size() - 1;
	}

	public PObj getPObj(int DivIndex) {
		return subPObjList.get(DivIndex);
	}

	// ///////ͼ�����///////////
	public int addGLNop(float Dx, float Dy) {
		int res = -1;
		addLock.lock();
		subPObjList.add(new GLNop(R, Dx, Dy));
		res = subPObjList.size() - 1;
		subPObjList.get(res).setDivIndex(TYPE);
		addLock.unlock();
		return res;
	}

	public int addGLFont(String HEXC, float Dx, float Dy, float Width,
			float Height, String FontName, int FontOpt, int FontSize,
			String Info, int OffsetX, int OffsetBottom) {
		int res = -1;
		addLock.lock();
		subPObjList.add(new GLFont(R, HEXC, Dx, Dy, Width, Height, FontName,
				FontOpt, FontSize, Info, OffsetX, OffsetBottom));
		res = subPObjList.size() - 1;
		subPObjList.get(res).setDivIndex(TYPE);
		addLock.unlock();
		return res;
	}

	public int addGLFont(String HEXC, float Dx, float Dy, float Width,
			float Height, String FontName, int FontOpt, int FontSize,
			String Info) {
		return addGLFont(HEXC, Dx, Dy, Width, Height, FontName, FontOpt,
				FontSize, Info, 0, 0);
	}

	public int addGLImage(float Dx, float Dy, float Width, float Height,
			String Path) {
		int res = -1;
		addLock.lock();
		subPObjList.add(new GLImage(R, Dx, Dy, Width, Height, Path));
		res = subPObjList.size() - 1;
		subPObjList.get(res).setDivIndex(TYPE);
		addLock.unlock();
		return res;
	}

	public int addGLImage(double Rx, double Ry, double Rw, double Rh,
			String Path) {
		return addGLImage((float) (Rx * R.ScreenW), (float) (Ry * R.ScreenH),
				(float) (Rw * R.ScreenW), (float) (Rh * R.ScreenH), Path);
	}

	public int addGLImage(float Dx, float Dy, String Path) {
		int res = -1;
		GLImage GL = new GLImage(R, Dx, Dy, 1, 1, Path);
		GL.setWidth(R.TC.getTextureWidth(Path));
		GL.setHeight(R.TC.getTextureHeight(Path));
		addLock.lock();
		subPObjList.add(GL);
		res = subPObjList.size() - 1;
		subPObjList.get(res).setDivIndex(TYPE);
		addLock.unlock();
		return res;
	}

	public int addGLLine(String HEXC, float Dx1, float Dy1, float Dx2, float Dy2) {
		int res = -1;
		addLock.lock();
		subPObjList.add(new GLLine(R, HEXC, Dx1, Dy1, Dx2, Dy2));
		res = subPObjList.size() - 1;
		subPObjList.get(res).setDivIndex(TYPE);
		addLock.unlock();
		return res;
	}

	public int addGLLine(String HEXC, float Dx1, float Dy1, float Dx2,
			float Dy2, float lineWidth) {
		int res = -1;
		addLock.lock();
		subPObjList.add(new GLLine(R, HEXC, Dx1, Dy1, Dx2, Dy2, lineWidth));
		res = subPObjList.size() - 1;
		subPObjList.get(res).setDivIndex(TYPE);
		addLock.unlock();
		return res;
	}

	public int addGLOval(String HEXC, float Dx, float Dy, float laxis,
			float saxis, int accuracy) {
		int res = -1;
		addLock.lock();
		subPObjList.add(new GLOval(R, HEXC, Dx, Dy, laxis, saxis, accuracy));
		res = subPObjList.size() - 1;
		subPObjList.get(res).setDivIndex(TYPE);
		addLock.unlock();
		return res;
	}

	public int addGLOval(String HEXC, float Dx, float Dy, float laxis,
			float saxis) {
		return addGLOval(HEXC, Dx, Dy, laxis, saxis,
				laxis > saxis ? (int) laxis / 2 : (int) saxis / 2);
	}

	public int addGLPoint(String HEXC, float Dx, float Dy) {
		int res = -1;
		addLock.lock();
		subPObjList.add(new GLPoint(R, HEXC, Dx, Dy));
		res = subPObjList.size() - 1;
		subPObjList.get(res).setDivIndex(TYPE);
		addLock.unlock();
		return res;
	}

	public int addGLRect(String HEXC, float Dx, float Dy, float Width,
			float Height) {
		int res = -1;
		addLock.lock();
		subPObjList.add(new GLRect(R, HEXC, Dx, Dy, Width, Height));
		res = subPObjList.size() - 1;
		subPObjList.get(res).setDivIndex(TYPE);
		addLock.unlock();
		return res;
	}

	public int addGLRect(String HEXC, double rX, double rY, double rW, double rH) {
		return addGLRect(HEXC, (float) (R.ScreenW * rX),
				(float) (R.ScreenH * rY), (float) (R.ScreenW * rW),
				(float) (R.ScreenH * rH));
	}

	public int addGLTrigon(String HEXC, float Dx1, float Dy1, float Dx2,
			float Dy2, float Dx3, float Dy3) {
		int res = -1;
		addLock.lock();
		subPObjList.add(new GLTrigon(R, HEXC, Dx1, Dy1, Dx2, Dy2, Dx3, Dy3));
		res = subPObjList.size() - 1;
		subPObjList.get(res).setDivIndex(TYPE);
		addLock.unlock();
		return res;
	}

	public int addGLWordSet(float Dx, float Dy, String keySetName, String Info) {
		int res = -1;
		addLock.lock();
		subPObjList.add(new GLWordSet(R, Dx, Dy, keySetName, Info));
		res = subPObjList.size() - 1;
		subPObjList.get(res).setDivIndex(TYPE);
		addLock.unlock();
		return res;
	}

	// ///////��׼�ӿ�///////////
	@Override
	public float getWidth() {
		return getWidth(0);
	}

	public float getWidth(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANWH UP = (CANWH) PO;
			return UP.getWidth();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANWH");
		}
		return -1;
	}

	@Override
	public float getHeight() {
		return getHeight(0);
	}

	public float getHeight(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANWH UP = (CANWH) PO;
			return UP.getHeight();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANWH");
		}
		return -1;
	}

	@Override
	public void setWidth(float Width) {
		setWidth(0, Width);
	}

	public void setWidth(int DivIndex, float Width) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANWH UP = (CANWH) PO;
			UP.setWidth(Width);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANWH");
		}
	}

	@Override
	public void setHeight(float Height) {
		setHeight(0, Height);
	}

	public void setHeight(int DivIndex, float Height) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANWH UP = (CANWH) PO;
			UP.setHeight(Height);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANWH");
		}
	}

	@Override
	public float getTheta() {
		return getTheta(0);
	}

	public float getTheta(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANTheta UP = (CANTheta) PO;
			return UP.getTheta();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANTheta");
		}
		return -1;
	}

	@Override
	public void setTheta(float Theta) {
		setTheta(0, Theta);
	}

	public void setTheta(int DivIndex, float Theta) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANTheta UP = (CANTheta) PO;
			UP.setTheta(Theta);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANTheta");
		}
	}

	@Override
	public float getSquare() {
		return getSquare(0);
	}

	public float getSquare(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANSquare UP = (CANSquare) PO;
			return UP.getSquare();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANSquare");
		}
		return -1;
	}

	@Override
	public void setDxs(float Dx, int index) {
		setDxs(0, Dx, index);
	}

	public void setDxs(int DivIndex, float Dx, int index) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANPositionMPoints UP = (CANPositionMPoints) PO;
			UP.setDxs(Dx, index);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANPositionMPoints");
		}
	}

	@Override
	public void setDys(float Dy, int index) {
		setDys(0, Dy, index);
	}

	public void setDys(int DivIndex, float Dy, int index) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANPositionMPoints UP = (CANPositionMPoints) PO;
			UP.setDys(Dy, index);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANPositionMPoints");
		}
	}

	@Override
	public float getDxs(int index) {
		return getDxs(0, index);
	}

	public float getDxs(int DivIndex, int index) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANPositionMPoints UP = (CANPositionMPoints) PO;
			return UP.getDxs(index);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANPositionMPoints");
		}
		return -1;
	}

	@Override
	public float getDys(int index) {
		return getDys(0, index);
	}

	public float getDys(int DivIndex, int index) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANPositionMPoints UP = (CANPositionMPoints) PO;
			return UP.getDys(index);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANPositionMPoints");
		}
		return -1;
	}

	@Override
	public int getPointNumber() {
		return getPointNumber(0);
	}

	public int getPointNumber(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANPositionMPoints UP = (CANPositionMPoints) PO;
			return UP.getPointNumber();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANPositionMPoints");
		}
		return -1;
	}

	@Override
	public void setPath(String Path, boolean autoSize) {
		setPath(0, Path, autoSize);
	}

	public void setPath(int DivIndex, String Path, boolean autoSize) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANPath UP = (CANPath) PO;
			UP.setPath(Path, autoSize);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANPath");
		}
	}

	@Override
	public void setPath(String Path) {
		setPath(0, Path);
	}

	public void setPath(int DivIndex, String Path) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANPath UP = (CANPath) PO;
			UP.setPath(Path);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANPath");
		}
	}

	public void setShaderProgram(int DivIndex, String shaderName) {
		PObj PO = subPObjList.get(DivIndex);
		PO.setShaderProgram(shaderName);
	}

	public void setShaderProgram(String shaderName) {
		setShaderProgram(0, shaderName);
	}

	public void setShaderUniform(int DivIndex, String uniformName,
			Object value, int TPFLAG) {
		PObj PO = subPObjList.get(DivIndex);
		PO.setShaderUniform(uniformName, value, TPFLAG);
	}

	public void setShaderUniform(String uniformName, Object value, int TPFLAG) {
		setShaderUniform(0, uniformName, value, TPFLAG);
	}

	public void setShaderUniform(int DivIndex, String uniformName, Object value) {
		setShaderUniform(DivIndex, uniformName, value,
				ShaderController.TP_AFLOAT);
	}

	public void setShaderUniform(String uniformName, Object value) {
		setShaderUniform(0, uniformName, value);
	}

	@Override
	public void setFontString(String S) {
		setFontString(0, S);
	}

	public void setFontString(int DivIndex, String S) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANFont UP = (CANFont) PO;
			UP.setFontString(S);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANFont");
		}
	}

	public void referanceKeyWord(int DivIndex, char[] cs) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANKeySet UP = (CANKeySet) PO;
			UP.referanceKeyWord(cs);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANFont");
		}
	}

	@Override
	public void referanceKeyWord(char[] cs) {
		referanceKeyWord(0, cs);
	}

	@Override
	public String getFontString() {
		return getFontString(0);
	}

	public String getFontString(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANFont UP = (CANFont) PO;
			return UP.getFontString();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANFont");
		}
		return null;
	}

	@Override
	public void setGLFill(boolean useFill) {
		setGLFill(0, true);
	}

	public void setGLFill(int DivIndex, boolean useFill) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANFill UP = (CANFill) PO;
			UP.setGLFill(true);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANFont");
		}
	}

	@Override
	public void setColor(Color C) {
		setColor(0, C);
	}

	public void setColor(int DivIndex, Color C) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANColor UP = (CANColor) PO;
			UP.setColor(C);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANColor");
		}
	}

	@Override
	public void setColor(String HEXC) {
		setColor(new Color(Integer.valueOf(HEXC, 16)));
	}

	public void setColor(int DivIndex, String HEXC) {
		setColor(DivIndex, new Color(Integer.valueOf(HEXC, 16)));
	}

	@Override
	public Color getColor() {

		return getColor(0);
	}

	public Color getColor(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANColor UP = (CANColor) PO;
			return UP.getColor();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANColor");
		}
		return null;
	}

	@Override
	public boolean PolygonCollide(CANPositionMPoints CANMP) {
		return PolygonCollide(0, CANMP);
	}

	public boolean PolygonCollide(int DivIndex, CANPositionMPoints CANMP) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANCollide UP = (CANCollide) PO;
			return UP.PolygonCollide(CANMP);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANCollide");
		}
		return false;
	}

	@Override
	public boolean PtInPolygon(float Dx, float Dy) {
		return PtInPolygon(0, Dx, Dy);
	}

	public boolean PtInPolygon(int DivIndex, float Dx, float Dy) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANCollide UP = (CANCollide) PO;
			return UP.PtInPolygon(Dx, Dy);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANCollide");
		}
		return false;
	}

	public void setCentralX(int DivIndex, float CX) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANCentral UP = (CANCentral) PO;
			UP.setCentralX(CX);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANCentral");
		}
	}

	@Override
	public void setCentralX(float CX) {
		setCentralX(0, CX);
	}

	public void setCentralY(int DivIndex, float CY) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANCentral UP = (CANCentral) PO;
			UP.setCentralY(CY);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANCentral");
		}
	}

	@Override
	public void setCentralY(float CY) {
		setCentralY(0, CY);
	}

	@Override
	public float getCentralX() {
		return getCentralX(0);
	}

	public float getCentralX(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANCentral UP = (CANCentral) PO;
			return UP.getCentralX();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANCentral");
		}
		return -1;
	}

	@Override
	public float getCentralY() {
		return getCentralY(0);
	}

	public float getCentralY(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANCentral UP = (CANCentral) PO;
			return UP.getCentralY();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANCentral");
		}
		return -1;
	}

	@Override
	public float getAngle() {
		return getAngle(0);
	}

	public PObj getDivObj(int DivIndex) {
		return subPObjList.get(DivIndex);
	}

	public float getAngle(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANAngle UP = (CANAngle) PO;
			return UP.getAngle();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANAngle");
		}
		return -1;
	}

	@Override
	public void setAngle(float Angle) {
		setAngle(0, Angle);
	}

	public void setAngle(int DivIndex, float Angle) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANAngle UP = (CANAngle) PO;
			UP.setAngle(Angle);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANAngle");
		}
	}

	@Override
	public void setAlph(float Alph) {
		setAlph(0, Alph);
	}

	public void setAlph(int DivIndex, float Alph) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANAlph UP = (CANAlph) PO;
			UP.setAlph(Alph);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANAlph");
		}
	}

	public void setAllAlph(float Alph) {
		addLock.lock();
		for (PObj PO : subPObjList) {
			try {
				CANAlph UP = (CANAlph) PO;
				UP.setAlph(Alph);
			} catch (ClassCastException e) {
				System.out.println("Canonical ERROR:��ʽʹ�ô���-CANAlph");
			}
		}
		addLock.unlock();
	}

	@Override
	public float getAlph() {
		return getAlph(0);
	}

	public float getAlph(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANAlph UP = (CANAlph) PO;
			return UP.getAlph();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANAlph");
		}
		return -1;
	}

	@Override
	public void setDx(float Dx) {
		float Offset = Dx - getDx();
		addLock.lock();
		for (int i = 0; i < subPObjList.size(); i++) {
			if (i == 0) {
				subPObjList.get(i).setDx(Dx);
			} else {
				subPObjList.get(i).setDx(getDx(i) + Offset);
			}
		}
		addLock.unlock();
	}

	public void setDx(int DivIndex, float Dx) {
		PObj PO = subPObjList.get(DivIndex);
		PO.setDx(Dx);
	}

	@Override
	public void setDy(float Dy) {
		float Offset = Dy - getDy();
		addLock.lock();
		for (int i = 0; i < subPObjList.size(); i++) {
			if (i == 0) {
				subPObjList.get(i).setDy(Dy);
			} else {
				subPObjList.get(i).setDy(getDy(i) + Offset);
			}
		}
		addLock.unlock();
	}

	public void setDy(int DivIndex, float Dy) {
		PObj PO = subPObjList.get(DivIndex);
		PO.setDy(Dy);
	}

	@Override
	public float getDx() {
		return getDx(0);
	}

	public float getDx(int DivIndex) {
		return subPObjList.get(DivIndex).getDx();
	}

	@Override
	public float getDy() {
		return getDy(0);
	}

	public float getDy(int DivIndex) {
		return subPObjList.get(DivIndex).getDy();
	}

	public void setPosition(int DivIndex, int FLAG, CANPosition rO, float margin) {
		PObj PO = subPObjList.get(DivIndex);
		PO.setPosition(FLAG, rO, margin);
	}

	@Override
	public void setPosition(int FLAG, CANPosition rO, float margin) {
		float Ox, Oy;
		Ox = this.getDx();
		Oy = this.getDy();
		setPosition(0, FLAG, rO, margin);
		float OffsetX = getDx() - Ox;
		float OffsetY = getDy() - Oy;
		addLock.lock();
		for (int i = 0; i < subPObjList.size(); i++) {
			if (i == 0) {
				continue;
			} else {
				subPObjList.get(i).setDy(getDy(i) + OffsetY);
				subPObjList.get(i).setDx(getDx(i) + OffsetX);
			}
		}
		addLock.unlock();
	}

	public void setPosition(int DivIndex, int FLAG, CANPosition rO) {
		PObj PO = subPObjList.get(DivIndex);
		PO.setPosition(FLAG, rO);
	}

	@Override
	public void setPosition(int FLAG, CANPosition rO) {
		float Ox, Oy;
		Ox = this.getDx();
		Oy = this.getDy();
		setPosition(0, FLAG, rO);
		float OffsetX = getDx() - Ox;
		float OffsetY = getDy() - Oy;
		addLock.lock();
		for (int i = 0; i < subPObjList.size(); i++) {
			if (i == 0) {
				continue;
			} else {
				subPObjList.get(i).setDy(getDy(i) + OffsetY);
				subPObjList.get(i).setDx(getDx(i) + OffsetX);
			}
		}
		addLock.unlock();
	}

	public void setPosition(int DivIndex, int FLAG, float margin) {
		PObj PO = subPObjList.get(DivIndex);
		PO.setPosition(FLAG, margin);
	}

	@Override
	public void setPosition(int FLAG, float margin) {
		float Ox, Oy;
		Ox = this.getDx();
		Oy = this.getDy();
		setPosition(0, FLAG, margin);
		float OffsetX = getDx() - Ox;
		float OffsetY = getDy() - Oy;
		addLock.lock();
		for (int i = 0; i < subPObjList.size(); i++) {
			if (i == 0) {
				continue;
			} else {
				subPObjList.get(i).setDy(getDy(i) + OffsetY);
				subPObjList.get(i).setDx(getDx(i) + OffsetX);
			}
		}
		addLock.unlock();
	}

	public void setPosition(int DivIndex, int FLAG) {
		PObj PO = subPObjList.get(DivIndex);
		PO.setPosition(FLAG);
	}

	@Override
	public void setPosition(int FLAG) {
		float Ox, Oy;
		Ox = this.getDx();
		Oy = this.getDy();
		setPosition(0, FLAG);
		float OffsetX = getDx() - Ox;
		float OffsetY = getDy() - Oy;
		addLock.lock();
		for (int i = 0; i < subPObjList.size(); i++) {
			if (i == 0) {
				continue;
			} else {
				subPObjList.get(i).setDy(getDy(i) + OffsetY);
				subPObjList.get(i).setDx(getDx(i) + OffsetX);
			}
		}
		addLock.unlock();
	}

	@Override
	public void setLineWidth(float lineWidth) {
		setLineWidth(0, lineWidth);
	}

	public void setLineWidth(int DivIndex, float lineWidth) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANLineWidth UP = (CANLineWidth) PO;
			UP.setLineWidth(lineWidth);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANLineWidth");
		}
	}

	@Override
	public float getLineWidth() {
		return getLineWidth(0);
	}

	public float getLineWidth(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANLineWidth UP = (CANLineWidth) PO;
			return UP.getLineWidth();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANLineWidth");
		}
		return -1;
	}

	@Override
	public void setKeyWord(String S) {
		setKeyWord(0, S);
	}

	public void setKeyWord(int DivIndex, String S) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANKeySet UP = (CANKeySet) PO;
			UP.setKeyWord(S);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANKeySet");
		}
	}

	@Override
	public String getKeyWord() {
		return getKeyWord(0);
	}

	public String getKeyWord(int DivIndex) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANKeySet UP = (CANKeySet) PO;
			return UP.getKeyWord();
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANKeySet");
		}
		return null;
	}

	@Override
	public float getRefDistance(float Dx, float Dy) {
		return getRefDistance(0, Dx, Dy);
	}

	public float getRefDistance(int DivIndex, float Dx, float Dy) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANReferance UP = (CANReferance) PO;
			return UP.getRefDistance(Dx, Dy);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANReferance");
		}
		return 0;
	}
	public float getRefDistance(Obj O){
		return getRefDistance(O.getPObj(0));
	}
	@Override
	public float getRefDistance(PObj PO) {
		return getRefDistance(0, PO);
	}

	public float getRefDistance(int DivIndex, PObj POb) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANReferance UP = (CANReferance) PO;
			return UP.getRefDistance(POb);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANReferance");
		}
		return 0;
	}

	@Override
	public float getRefTheta(float Dx, float Dy) {
		return getRefTheta(0, Dx, Dy);
	}

	public float getRefTheta(int DivIndex, float Dx, float Dy) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANReferance UP = (CANReferance) PO;
			return UP.getRefTheta(Dx, Dy);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANReferance");
		}
		return 0;
	}
	public float getRefTheta(Obj O){
		return getRefTheta(O.getPObj(0));
	}
	@Override
	public float getRefTheta(PObj PO) {
		return getRefTheta(0, PO);
	}

	public float getRefTheta(int DivIndex, PObj POb) {
		PObj PO = subPObjList.get(DivIndex);
		try {
			CANReferance UP = (CANReferance) PO;
			return UP.getRefTheta(POb);
		} catch (ClassCastException e) {
			System.out.println("Canonical ERROR:��ʽʹ�ô���-CANReferance");
		}
		return 0;
	}

	public void setMutiLine(List<Integer> MainFonter, int lineChars, String Str) {
		Obj O = this;
		int begin = 0, end = 0, lineIndex = 0;
		int count = 0;
		for (int i : MainFonter) {
			O.setFontString(i, "");
		}
		while (end < Str.length()) {
			while (end < Str.length()
					&& (count <= lineChars && Str.charAt(end) != '\n')) {
				if (ChineseConfirmer.isChinese(Str.charAt(end))) {
					count += 2;
				} else {
					count += 1;
				}
				end++;
			}
			char[] line = new char[end - begin];
			for (int i = begin; i < end; i++) {
				line[i - begin] = Str.charAt(i);
			}
			String lineString = new String(line);
			O.setFontString(MainFonter.get(lineIndex), lineString);
			lineIndex++;
			if (lineIndex == MainFonter.size()) {
				break;
			}
			if (end < Str.length() && Str.charAt(end) == '\n') {
				end++;
			}
			begin = end;
			count = 0;
		}
	}

	public void setInverseColor(boolean useInverse) {
		if (useInverse) {
			setCustomMixMode(GL.GL_ONE_MINUS_DST_COLOR, GL.GL_ZERO);
		} else {
			clearCustomMixModel();
		}
	}

	public void setColorAddMixMode(boolean turnOn) {
		if (turnOn) {
			setCustomMixMode(GL.GL_SRC_ALPHA, GL.GL_ONE);
		} else {
			clearCustomMixModel();
		}
	}

	public boolean useCustomMixModel() {
		return customMix;
	}

	public void clearCustomMixModel() {
		customMix = false;
	}

	public void setCustomMixMode(int arg0, int arg1) {
		mixArg0 = arg0;
		mixArg1 = arg1;
		customMix = true;
	}

	public int getMix(int index) {
		if (index == 0) {
			return mixArg0;
		} else if (index == 1) {
			return mixArg1;
		}
		return -1;
	}

	public void moveCTo(float Dx, float Dy, int allms) {
		TAction objMove = new moveAction_D(this, allms, Dx - this.getWidth()
				/ 2, Dy - this.getHeight() / 2);
		R.T_AL.addActions(objMove);
	}

	public void moveDTo(float Dx, float Dy, int allms) {
		TAction objMove = new moveAction_D(this, allms, Dx, Dy);
		R.T_AL.addActions(objMove);
	}

	@Override
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	@Override
	public void removeKeyListener() {
		this.keyListener = null;
	}

	@Override
	public void keyFactor(int KeyCode, boolean ispressed) {
		if (keyListener != null) {
			keyListener.doKeyBord(this, KeyCode, ispressed);
		}
	}

	@Override
	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
		R.mouse.mouseBotton.addMouseRefObj(this);
	}

	@Override
	public void removeMouseListener() {
		R.mouse.mouseBotton.removeMouseRefObj(this);
		this.mouseListener = null;
	}

	@Override
	public void mouseFactor(MouseEvent e) {
		if (this.mouseListener != null) {
			this.mouseListener.onClick(this, e);
		}
	}

	@Override
	public boolean isMouseInside() {
		if (subPObjList.size() == 0) {
			return false;
		}
		PObj PO = subPObjList.get(0);
		if (PO instanceof SquareablePObj) {
			if (R.VC.useViewPort(PO.getDivIndex())) {
				return ((SquareablePObj) PO).PtInPolygon(
						R.mouse.mouseBotton.MouseX - R.VC.viewerTransX,
						R.mouse.mouseBotton.MouseY - R.VC.viewerTransY);
			} else {
				return ((SquareablePObj) PO).PtInPolygon(
						R.mouse.mouseBotton.MouseX, R.mouse.mouseBotton.MouseY);
			}
		} else {
			return false;
		}
	}

	@Override
	public String getInfo() {
		return this.toString();
	}
}
