package engineextend.crowdcontroller;

public interface Individual 
{
	public static final int SRC_OUTER = 0;
	public static final int SRC_INNER = 1;
	public static final int SRC_HIDE_ONLY = 2;
	
	public static final int SRC_EXTRA_0 = 3;
	public static final int SRC_EXTRA_1 = 4;
	public static final int SRC_EXTRA_2 = 5;
	
	public boolean isAvalible();
	public void getUse(Object[] ARGS,float... FARGS);
	public void doStp(int clock);
	public void finish(int src);
	public void destroy();
}
