package geivcore.engineSys.screenpainter;

import geivcore.R;
import geivcore.engineSys.viewcontroller.ModelProjector;
import geivcore.enginedata.obj.Obj;
import geivcore.enginedata.obj.PObj;

import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;


import com.sun.opengl.util.GLUT;

public class Painter implements GLEventListener
{
		R R;
		public GL gl;
		public GLU glu;
		public GLUT glut;
		
		float sW;
		float sH;
		
		float a;
		float b;
		double dTheta;

		boolean hasLoadTextureTask = false;
		long begin;
		String shaderName;
	    public Painter(R R)
	    {
	    	this.R = R;
	    	//R.Painter = this;
	    }
	    public void init(GLAutoDrawable drawable) 
	    {  
	    	Thread.currentThread().setName("GL_Draw");
	    	//drawable.setAutoSwapBufferMode(true);
	    	gl =  drawable.getGL();
	        glu = new GLU();  
	        glut = new GLUT(); 
	       
	        //gl.glEnable(GL.GL_DEPTH_TEST);  // ������Ȳ���  
	        //gl.glFrontFace(GL.GL_CCW);      //����CCW����Ϊ�����桱��CCW��CounterClockWise����ʱ��  
	        //gl.glEnable(GL.GL_CULL_FACE);       //��GL_CULL_FACE��ų�������glEnable�����ʾ��������α����޳���
	        //���û��Ч������ճ�ͻ��
	        gl.glEnable(GL.GL_BLEND);
	        //gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
	        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
	   
	        gl.glAlphaFunc(GL.GL_GREATER, 0);
	        gl.glEnable(GL.GL_ALPHA);
	   
	        gl.glPointSize(1);
	        gl.glEnable(GL.GL_POINT_SMOOTH);
	        gl.glHint(GL.GL_POINT_SMOOTH, GL.GL_NICEST);
	      
	        gl.glLineWidth(1);
	        gl.glEnable (GL.GL_LINE_SMOOTH);
	        gl.glHint(GL.GL_LINE_SMOOTH, GL.GL_NICEST);

	        gl.glEnable(GL.GL_TEXTURE_2D);
	    }
	    public void display(GLAutoDrawable drawable) 
	    {
	    	//begin = System.nanoTime();
	    	if(hasLoadTextureTask)
	    	{
	    		hasLoadTextureTask = false;
	    		R.TC.loadAllUnLoadedTexture();
	    	}
	    	if(R.needAttachShader())
	    	{
	    		R.attachShader(gl);
	    	}
	    	
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); 
			for(int DivIndex = 0;DivIndex <= R.OC.MAX_DIV_INDEX;DivIndex++)
			{
				List<Obj> OL = R.OC.acquireDivList(DivIndex);
				//if(DivIndex == R.OC.XRIndex||DivIndex == R.OC.GameShellIndex)
				if(DivIndex == R.OC.XRIndex)
				{
					gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
					//gl.glBlendFunc(GL.GL_ONE_MINUS_DST_COLOR, GL.GL_ZERO);
				}
				else
				{
					gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
				}
				R.VC.loadCanvasIdentity(DivIndex,gl);
				ModelProjector refModel = R.VC.getProjectModel(DivIndex);
				for(Obj O:OL)
				{
					if(!O.printable)
					{
						continue;
					}
					else
					{
						if(O.useCustomMixModel())
						{
							gl.glBlendFunc(O.getMix(0),O.getMix(1));
						}
						List<PObj> PL = O.getPObjList();
						for(PObj P:PL)
						{
							if((shaderName = P.getShaderName())!= null)
							{
								P.prepareShaderUniform(gl);
								P.drawShape(gl, refModel);
								gl.glUseProgram(0);
							}
							else
							{
								P.drawShape(gl, refModel);
							}
						}
						O.relasePObjList();
					}
				}
				R.OC.releaseDivList(DivIndex);
			}
			R.SC.runAllSerialTask();
			gl.glFlush();
			//System.out.println("ALL PainteTask Use Time:"+(System.nanoTime() - begin)/1000000.0);
	    }  
	    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
	    {
	        //float fAspect;
	        // ��ֹΪ��  
	        if (height == 0) {  
	            height = 1;  
	        }  
	        //�ӿ�����Ϊ���ڳߴ�  
	        gl.glViewport(0, 0, width, height);  
	        //fAspect = (float) width / height;  
	        // Reset projection matrix stack  
	        gl.glMatrixMode(GL.GL_PROJECTION);  
	        gl.glLoadIdentity();
	        //͸��ͶӰ �۽Ƕ�,����,�����,Զ����  
	        //glu.gluPerspective(53.0f, fAspect, 1.0, 400.0);
	        //glu.gluPerspective(45.0f, fAspect, 1.0, 400.0);
	        gl.glOrtho(-100,100,-75,75, 1.0, 800.0);
	        // ����ģ�͹۲�����ջ  
	        gl.glMatrixMode(GL.GL_MODELVIEW);  
	        gl.glLoadIdentity(); 
	        
	        //System.out.println("CRT SCREEN:"+width+"X"+height);
	    }  
	    public void dispose(GLAutoDrawable arg0) {}
		@Override
		public void displayChanged(GLAutoDrawable arg0, boolean arg1,
				boolean arg2) {}
		public void loadAllUnloadTexture()
		{
			hasLoadTextureTask = true;
		}
}
