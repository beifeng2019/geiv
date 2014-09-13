package geivcore.engineSys.viewcontroller;


import engineextends.util.common.Matrix;
import geivcore.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.media.opengl.GL;




public class ViewController
{
	R R;
	public List<ModelProjector> ModelPList;

	public float viewerDepth;
	public float viewerFloatAngle;
	public float viewerAngle;
	public float viewerTransX;
	public float viewerTransY;
	
	public boolean thinkingFlag = false;
	public ReentrantLock doThinkLock = new ReentrantLock();
	
	public ViewController(R R)
	{
		this.R = R;
		
		//R.ViewController = this;
		//�������������������Ŀ����Ӿ��Ƕ�,�����䷶Χ����װ�ɺ���
		viewerDepth = 0;
		//viewerFloatAngle = -60;
		//viewerTheta = -60;
		viewerAngle = 0;
		viewerTransX = 0;
		viewerTransY = 0;
		
		ModelPList = new ArrayList<ModelProjector>();
		
		ModelPList.add(new ModelProjector_Default(R));
		ModelPList.add(new ModelProjector_BG(R));
		ModelPList.add(new ModelProjector_UI(R));
	}
	public ModelProjector getProjectModel(int DivIndex)
	{
		ModelProjector result = null;
		boolean found = false;
		for(ModelProjector m:ModelPList)
		{
			if(m.DivIndex == DivIndex)
			{
				result = m;
				found = true;
				break;
			}
		}
		if(!found)
		{
			for(ModelProjector m:ModelPList)
			{
				if(m.DivIndex == -1)
				{
					result = m;
					break;
				}
			}
		}
		return result;
	}
	public void setViewPort(int DivIndex,GL gl)
	{
		//System.out.println(viewerTheta+","+viewerFloatAngle);
		ModelProjector refModel = getProjectModel(DivIndex);
		gl.glTranslatef(viewerTransX,viewerTransY,-viewerDepth);
		
		gl.glTranslatef(0,refModel.cH/2,0);
		gl.glRotatef(viewerAngle,1,0,0);
		gl.glTranslatef(0, -refModel.cH/2,0);
		
		gl.glTranslatef(refModel.cW/2,refModel.cH/2,0);
		gl.glRotatef(viewerFloatAngle,0,0,1);
		gl.glTranslatef(-refModel.cW/2,-refModel.cH/2,0);
		/*
		if(R.gameBGController.ready)
		{
			//////�Ȳ����ƶ�
			gl.glTranslatef((R.gameBGController.stableCx - R.gameBGController.Rx)*refModel.cW/R.ScreenW,(R.gameBGController.stableCy - R.gameBGController.Ry)*refModel.cH/R.ScreenH,0);
			//////�ٲ�����ת
			gl.glTranslatef(R.gameBGController.Rx*refModel.cW/R.ScreenW,R.gameBGController.Ry*refModel.cH/R.ScreenH,0);
			gl.glRotatef(R.gameBGController.Rt,0,0,1);
			gl.glTranslatef(-R.gameBGController.Rx*refModel.cW/R.ScreenW,-R.gameBGController.Ry*refModel.cH/R.ScreenH,0);
		}*/
	}
	public void unProjectViewPort(ProjectPoint PP,int LayerType)
	{
		//ModelProjector_ABS refModel = getProjectModel(LayerType);
		Matrix sourse = new Matrix(1,3);
		//PPʹ��OPENGL ���ϵ
		sourse.mat[0][0] = PP.Dx;
		sourse.mat[0][1] = PP.Dy;
		sourse.mat[0][2] = 1;
		
		Matrix move = new Matrix(3,3);
		move.mat[0][0] = 1;
		move.mat[1][1] = 1;
		move.mat[2][2] = 1;
		
		move.mat[2][0] = 1;//X
		move.mat[2][1] = 1;//Y
		
		Matrix turn = new Matrix(3,3);
		
		turn.mat[0][0] = 1;//cos ��ʱ��Ϊ��
		turn.mat[0][1] = 1;//sin
		turn.mat[1][0] = 1;//-sin
		turn.mat[1][1] = 1;//cos
		
		turn.mat[2][2] = 1;
		/*
		if(R.gameBGController.ready)
		{
			move.mat[2][0] = R.gameBGController.Rx*refModel.cW/R.ScreenW;
			move.mat[2][1] = R.gameBGController.Ry*refModel.cH/R.ScreenH;
			
			sourse = sourse.Multiply(move);
			//////////////////////////////////////
			float Theta = R.gameBGController.Rt*(float)Math.PI/180;
			
			turn.mat[0][0] = Math.cos(Theta);//cos ��ʱ��Ϊ��
			turn.mat[0][1] = Math.sin(Theta);//sin
			turn.mat[1][0] = -Math.sin(Theta);//-sin
			turn.mat[1][1] = Math.cos(Theta);//cos
			
			sourse = sourse.Multiply(turn);
			//////////////////////////////////////////
			move.mat[2][0] = -move.mat[2][0];
			move.mat[2][1] = -move.mat[2][1];
			
			sourse = sourse.Multiply(move);
			////////////////////////////////////////
			move.mat[2][0] = -(R.gameBGController.stableCx - R.gameBGController.Rx)*refModel.cW/R.ScreenW;
			move.mat[2][1] = -(R.gameBGController.stableCy - R.gameBGController.Ry)*refModel.cH/R.ScreenH;
			
			sourse = sourse.Multiply(move);
		}*/
		PP.Dx = (float)sourse.mat[0][0];
		PP.Dy = (float)sourse.mat[0][1];
	}
	public void loadCanvasIdentity(int DivIndex,GL gl)
	{
		ModelProjector refModel = getProjectModel(DivIndex);
		refModel.projectModel(gl);
		if(refModel.useViewPort)
		{
			setViewPort(DivIndex,gl);
		}
	}
	public boolean useViewPort(int DivIndex)
	{
		ModelProjector refModel = getProjectModel(DivIndex);
		return refModel.useViewPort;
	}
	/*
	public void projectTinue(ProjectPoint PP,int LayerType)//��һ���ڲ���Ļ���ת��Ϊʵ����Ļ��꣨����׼��꣩
	{
		ModelProjector_ABS refModel = getProjectModel(LayerType);
		
		float cW = refModel.cW;
		float cH = refModel.cH;
		
		PP.Dx = PP.Dx*cW/R.ScreenW;
		PP.Dy = PP.Dy*cH/(R.ScreenH - 20);
		
		Matrix sourse = new Matrix(1,4);
		sourse.mat[0][0] = PP.Dx;
		sourse.mat[0][1] = PP.Dy;
		sourse.mat[0][2] = PP.Dz;
		sourse.mat[0][3] = 1;
		//
		Matrix MoveMatrix_viewerFloatAngle_anti = new Matrix(4, 4);
		MoveMatrix_viewerFloatAngle_anti.mat[0][0] = 1;
		MoveMatrix_viewerFloatAngle_anti.mat[1][1] = 1;
		MoveMatrix_viewerFloatAngle_anti.mat[2][2] = 1;
		MoveMatrix_viewerFloatAngle_anti.mat[3][3] = 1;
		MoveMatrix_viewerFloatAngle_anti.mat[3][0] = -cW/2;
		MoveMatrix_viewerFloatAngle_anti.mat[3][1] = -cH/2;
		MoveMatrix_viewerFloatAngle_anti.mat[3][2] = 0;
		//
		Matrix RotateMatrix_viewerFloatAngle = new Matrix(4 , 4);
		RotateMatrix_viewerFloatAngle.mat[2][2] = 1;
		RotateMatrix_viewerFloatAngle.mat[3][3] = 1;
		RotateMatrix_viewerFloatAngle.mat[0][0] = Math.cos(R.ViewController.viewerFloatAngle*Math.PI/180);
		RotateMatrix_viewerFloatAngle.mat[1][1] = RotateMatrix_viewerFloatAngle.mat[0][0];
		RotateMatrix_viewerFloatAngle.mat[0][1] = Math.sin(R.ViewController.viewerFloatAngle*Math.PI/180);
		RotateMatrix_viewerFloatAngle.mat[1][0] = -RotateMatrix_viewerFloatAngle.mat[0][1];
		//
		Matrix MoveMatrix_viewerFloatAngle = new Matrix(4, 4);
		MoveMatrix_viewerFloatAngle.mat[0][0] = 1;
		MoveMatrix_viewerFloatAngle.mat[1][1] = 1;
		MoveMatrix_viewerFloatAngle.mat[2][2] = 1;
		MoveMatrix_viewerFloatAngle.mat[3][3] = 1;
		MoveMatrix_viewerFloatAngle.mat[3][0] = cW/2;
		MoveMatrix_viewerFloatAngle.mat[3][1] = cH/2;
		MoveMatrix_viewerFloatAngle.mat[3][2] = 0;
		//
		Matrix MoveMatrix_viewerTheta_anti = new Matrix(4, 4);
		MoveMatrix_viewerTheta_anti.mat[0][0] = 1;
		MoveMatrix_viewerTheta_anti.mat[1][1] = 1;
		MoveMatrix_viewerTheta_anti.mat[2][2] = 1;
		MoveMatrix_viewerTheta_anti.mat[3][3] = 1;
		MoveMatrix_viewerTheta_anti.mat[3][0] = 0;
		MoveMatrix_viewerTheta_anti.mat[3][1] = -cH/2;
		MoveMatrix_viewerTheta_anti.mat[3][2] = 0;
		//
		Matrix RotateMatrix_viewerTheta = new Matrix(4 , 4);
		RotateMatrix_viewerTheta.mat[0][0] = 1;
		RotateMatrix_viewerTheta.mat[3][3] = 1;
		
		RotateMatrix_viewerTheta.mat[1][1] = Math.cos(R.ViewController.viewerTheta*Math.PI/180);
		RotateMatrix_viewerTheta.mat[2][2] = RotateMatrix_viewerTheta.mat[1][1];
		
		RotateMatrix_viewerTheta.mat[1][2] = Math.sin(R.ViewController.viewerTheta*Math.PI/180);
		RotateMatrix_viewerTheta.mat[2][1] = -RotateMatrix_viewerTheta.mat[1][2];
		//
		Matrix MoveMatrix_viewerTheta = new Matrix(4, 4);
		MoveMatrix_viewerTheta.mat[0][0] = 1;
		MoveMatrix_viewerTheta.mat[1][1] = 1;
		MoveMatrix_viewerTheta.mat[2][2] = 1;
		MoveMatrix_viewerTheta.mat[3][3] = 1;
		MoveMatrix_viewerTheta.mat[3][0] = 0;
		MoveMatrix_viewerTheta.mat[3][1] = cH/2;
		MoveMatrix_viewerTheta.mat[3][2] = 0;
		//
		Matrix MoveMatrix_Trans = new Matrix(4, 4);
		MoveMatrix_Trans.mat[0][0] = 1;
		MoveMatrix_Trans.mat[1][1] = 1;
		MoveMatrix_Trans.mat[2][2] = 1;
		MoveMatrix_Trans.mat[3][3] = 1;
		MoveMatrix_Trans.mat[3][0] = R.ViewController.viewerTransX;
		MoveMatrix_Trans.mat[3][1] = R.ViewController.viewerTransY;
		MoveMatrix_Trans.mat[3][2] = -R.ViewController.viewerDepth;
		
		sourse = sourse.Multiply(MoveMatrix_viewerFloatAngle_anti);
		//sourse.Output("p1");
		sourse = sourse.Multiply(RotateMatrix_viewerFloatAngle);
		//sourse.Output("p2");
		sourse = sourse.Multiply(MoveMatrix_viewerFloatAngle);
		//sourse.Output("p3");
		sourse = sourse.Multiply(MoveMatrix_viewerTheta_anti);
		//sourse.Output("p4");
		sourse = sourse.Multiply(RotateMatrix_viewerTheta);
		//sourse.Output("p5");
		sourse = sourse.Multiply(MoveMatrix_viewerTheta);
		//sourse.Output("p6");
		sourse = sourse.Multiply(MoveMatrix_Trans);
		//sourse.Output("p7");
		
		
		PP.Dx = (float)sourse.mat[0][0]*R.ScreenW/cW;
		PP.Dy = (float)sourse.mat[0][1]*(R.ScreenH - 20)/cH;
		
	}*/
}
