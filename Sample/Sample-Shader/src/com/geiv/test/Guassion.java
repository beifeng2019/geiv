package com.geiv.test;

import geivcore.SerialTask;
import geivcore.UESI;
import geivcore.engineSys.shadercontroller.ShaderController;
import geivcore.engineSys.shadercontroller.SysShader;
import geivcore.enginedata.canonical.CANExPos;
import geivcore.enginedata.obj.Obj;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Guassion implements SerialTask{
	UESI UES;
	float[] g_aryVerticalOffset;
	float[] vertstatic;
	float bur = 600f;
	Obj T;
	public Guassion(UESI UES) {
		this.UES = UES;

		T = UES.creatObj(UESI.BGIndex);
		T.addGLImage(0, 0, "./mdls.jpg");
		
		T.setShaderProgram(SysShader.SD_GAUSSIAN_MODE9);
		T.setShaderUniform(SysShader.NA_WEIGHTARGS,
				SysShader.BR_GAUSSIAN_MODE9, ShaderController.TP_FLOATS);

		vertstatic = new float[]{-1,-1, 0,-1, 1,-1
				  -1, 0, 0, 0, 1, 0
				  -1, 1, 0, 1, 1, 1};

		g_aryVerticalOffset = Arrays.copyOf(vertstatic, vertstatic.length);
		
		
		for(int i = 0;i < g_aryVerticalOffset.length;i++)
		{
			g_aryVerticalOffset[i] = vertstatic[i]/bur;
		}
		
		T.setShaderUniform(SysShader.NA_OFFSETARGS, g_aryVerticalOffset,
				ShaderController.TP_VERT2S);

		T.setPosition(CANExPos.POS_CENTER);
		T.show();
		
		UES.addSerialTask(this);
	}

	@Override
	public void Serial(int clock) {
		if(UES.getKeyStatus(KeyEvent.VK_Z))
		{
			T.setShaderUniform("g_aryVerticalOffset",g_aryVerticalOffset,ShaderController.TP_VERT2S);
			bur+=4f;
			for(int i = 0;i < g_aryVerticalOffset.length;i++)
			{
				g_aryVerticalOffset[i] = vertstatic[i]/bur;
			}
		}
		else if(UES.getKeyStatus(KeyEvent.VK_X))
		{
			T.setShaderUniform("g_aryVerticalOffset",g_aryVerticalOffset,ShaderController.TP_VERT2S);
			bur-=4f;
			for(int i = 0;i < g_aryVerticalOffset.length;i++)
			{
				g_aryVerticalOffset[i] = vertstatic[i]/bur;
			}
		}
	}
}
