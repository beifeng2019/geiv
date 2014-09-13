package geivcore.enginedata.canonical;

public interface CANExPos {
	public static final int POS_X_CENTER = 1;
	public static final int POS_Y_CENTER = 2;
	public static final int POS_CENTER = POS_X_CENTER|POS_Y_CENTER;
	public static final int POS_X_LEFT = 4;
	public static final int POS_X_RIGHT = 8;
	public static final int POS_Y_TOP = 16;
	public static final int POS_Y_BOTTOM = 32;
	public static final int POS_CORNER_LEFTTOP = POS_X_LEFT|POS_Y_TOP;
	public static final int POS_CORNER_RIGHTTOP = POS_X_RIGHT|POS_Y_TOP;
	public static final int POS_CORNER_LEFTBOTTOM = POS_X_LEFT|POS_Y_BOTTOM;
	public static final int POS_CORNER_RIGHTBOTTOM = POS_X_RIGHT|POS_Y_BOTTOM;
	public void setPosition(int FLAG,float margin);
	public void setPosition(int FLAG);
}
