package geivcore.engineSys.shadercontroller;

import geivcore.UESI;

import java.io.File;

public class SysShader {
	UESI UES;
	public static final String SD_ANTICOLOR = "SYS_ANTICOLOR";
	public static final String SD_EMBOSS_MODE9 = "SYS_Emboss_mode9";
	public static final String SD_GAUSSIAN_MODE9 = "SYS_Gaussian_mode9";
	public static final String SD_LAPLACIAN_MODE9 = "SYS_Laplacian_mode9";
	public static final String SD_MEAN_MODE9 = "SYS_Mean_mode9";
	
	public static final String NA_WEIGHTARGS = "g_aryWeight";
	public static final String NA_OFFSETARGS = "g_aryOffset";
	
	public static final float[] OF_OFFSET_MODE9 = {-1,-1, 0,-1, 1,-1
													-1, 0, 0, 0, 1, 0
													-1, 1, 0, 1, 1, 1};
	
	public static final float[] BR_EMBOSS_MODE9 = {2, 0, 0,
													0,-1, 0,
													0, 0,-1};
	public static final float[] BR_GAUSSIAN_MODE9 = {12,16,12,
														16,25,16,
														12,16,12};
	public static final float[] BR_LAPLACIAN_MODE9 = { 0,-1, 0,
														-1, 4,-1,
														0,-1, 0};
	public static final float[] BR_MEAN_MODE9 = {1, 1, 1,
												1, 1, 1,
												1, 1, 1};
	public SysShader(UESI UES)
	{
		this.UES = UES;
	}
	public void initSysShaders()
	{
		UES.createShaderProgram(SD_ANTICOLOR,"." + File.separator + "GDivSys" + File.separator + "Shaders" + File.separator + "AntiColorVect.txt","." + File.separator + "GDivSys" + File.separator + "Shaders" + File.separator + "AntiColor.txt");
		UES.createShaderProgram(SD_EMBOSS_MODE9,"." + File.separator + "GDivSys" + File.separator + "Shaders" + File.separator + "normalVect.txt","." + File.separator + "GDivSys" + File.separator + "Shaders" + File.separator + "Emboss.txt");
		UES.createShaderProgram(SD_GAUSSIAN_MODE9,"." + File.separator + "GDivSys" + File.separator + "Shaders" + File.separator + "normalVect.txt","." + File.separator + "GDivSys" + File.separator + "Shaders" + File.separator + "Gaussian.txt");
		UES.createShaderProgram(SD_LAPLACIAN_MODE9,"." + File.separator + "GDivSys" + File.separator + "Shaders" + File.separator + "normalVect.txt","." + File.separator + "GDivSys" + File.separator + "Shaders" + File.separator + "Laplacian.txt");
		UES.createShaderProgram(SD_MEAN_MODE9,"." + File.separator + "GDivSys" + File.separator + "Shaders" + File.separator + "normalVect.txt","." + File.separator + "GDivSys" + File.separator + "Shaders" + File.separator + "Mean_antiAliasing.txt");
		
		normalization(BR_EMBOSS_MODE9);
		normalization(BR_GAUSSIAN_MODE9);
	}
	public static void normalization(float[] g_aryWeight)
	{
		float sum = 0;
		for(int i = 0;i < g_aryWeight.length;i++)
		{
			sum+=g_aryWeight[i];
		}
		for(int i = 0;i < g_aryWeight.length;i++)
		{
			g_aryWeight[i] = g_aryWeight[i]/sum;
		}
	}
	public static void discretization(float[] g_aryOffset_src,float[] g_aryOffset_dst,float bur)
	{
		for(int i = 0;i < g_aryOffset_src.length;i++)
		{
			g_aryOffset_dst[i] = g_aryOffset_src[i]/bur;
		}
	}
}
