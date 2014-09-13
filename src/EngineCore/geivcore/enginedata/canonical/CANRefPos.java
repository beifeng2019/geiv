package geivcore.enginedata.canonical;

public interface CANRefPos {
	public static final int RPO_X_LEFTTHAN = 1;
	public static final int RPO_X_RIGHTTHAN = 2;
	public static final int RPO_Y_TOPTHAN = 4;
	public static final int RPO_Y_BOTTOMTHAN = 8;
	
	public static final int RPO_CORNER_LEFTTOP = RPO_X_LEFTTHAN|RPO_Y_TOPTHAN;
	public static final int RPO_CORNER_LEFTBOTTOM = RPO_X_LEFTTHAN|RPO_Y_BOTTOMTHAN;
	public static final int RPO_CORNER_RIGHTTOP = RPO_X_RIGHTTHAN|RPO_Y_TOPTHAN;
	public static final int RPO_CORNER_RIGHTBOTTOM = RPO_X_RIGHTTHAN|RPO_Y_BOTTOMTHAN;
	
	public void setPosition(int FLAG,CANPosition rO,float margin);
	public void setPosition(int FLAG,CANPosition rO);
}