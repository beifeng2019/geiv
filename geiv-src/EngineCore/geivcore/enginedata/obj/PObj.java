package geivcore.enginedata.obj;

import geivcore.R;
import geivcore.engineSys.shadercontroller.ShaderController;
import geivcore.engineSys.viewcontroller.ModelProjector;
import geivcore.enginedata.canonical.CANAlph;
import geivcore.enginedata.canonical.CANCentral;
import geivcore.enginedata.canonical.CANColor;
import geivcore.enginedata.canonical.CANExPos;
import geivcore.enginedata.canonical.CANPosition;
import geivcore.enginedata.canonical.CANRefPos;
import geivcore.enginedata.canonical.CANReferance;
import geivcore.enginedata.canonical.CANWH;

import java.awt.Color;
import java.util.Hashtable;

import javax.media.opengl.GL;



public abstract class PObj implements CANPosition,CANExPos,CANRefPos,CANCentral,CANColor,CANAlph,CANReferance
{
	R R;
	protected float Dx,Dy;
	protected float alpha = 1.0f;
	protected Color C;
	protected int DivIndex;
	String shaderName;
	Hashtable<String,Object> shaderUniform;
	Hashtable<String,Integer> shaderUniformType;
	Hashtable<String,Float> shaderAttribute;
	public PObj(R R,Color C,float Dx,float Dy)
	{
		this.R = R;
		this.Dx = Dx;
		this.Dy = Dy;
		this.C = C;
	}
	public void setDivIndex(int DivIndex)
	{
		this.DivIndex = DivIndex;
	}
	public int getDivIndex()
	{
		return DivIndex;
	}
	public PObj(R R,String HEXC,float Dx,float Dy)
	{
		this(R,new Color(Integer.valueOf(HEXC,16)),Dx,Dy);
	}
	public abstract void drawShape(GL gl,ModelProjector refModel);
	
	////////////////getϵ��
	public float getDx()
	{
		return Dx;
	}
	public float getDy()
	{
		return Dy;
	}
	public float getAlph()
	{
		return alpha;
	}
	public Color getColor()
	{
		return C;
	}
	public float getCentralX()
	{
		return Dx;
	}
	///
	public float getCentralY()
	{
		return Dy;
	}
	public void setCentralX(float CX)
	{
		float dec = CX - getCentralX();
		setDx(getDx() + dec);
	}
	public void setCentralY(float CY)
	{
		float dec = CY - getCentralY();
		setDy(getDy() + dec);
	}
	//DX,DY,ALPH,COLOR
	public void setPosition(int FLAG,CANPosition rO,float margin)
	{
		if((FLAG&CANRefPos.RPO_X_LEFTTHAN) > 0)
		{
			if(this instanceof CANWH)
			{
				this.setDx(rO.getDx() - ((CANWH)this).getWidth() - margin);
			}
			else
			{
				this.setDx(rO.getDx() - margin);
			}
			if((FLAG&(CANRefPos.RPO_Y_BOTTOMTHAN|CANRefPos.RPO_Y_TOPTHAN)) == 0)
			{
				this.setDy(rO.getDy());
			}
		}
		if((FLAG&CANRefPos.RPO_X_RIGHTTHAN) > 0)
		{
			if(rO instanceof CANWH)
			{
				this.setDx(rO.getDx() + ((CANWH)rO).getWidth() + margin);
			}
			else
			{
				this.setDx(rO.getDx() + margin);
			}
			if((FLAG&(CANRefPos.RPO_Y_BOTTOMTHAN|CANRefPos.RPO_Y_TOPTHAN)) == 0)
			{
				this.setDy(rO.getDy());
			}
		}
		if((FLAG&CANRefPos.RPO_Y_TOPTHAN) > 0)
		{
			if(this instanceof CANWH)
			{
				this.setDy(rO.getDy() - ((CANWH)this).getHeight() - margin);
			}
			else
			{
				this.setDy(rO.getDy() - margin);
			}
			if((FLAG&(CANRefPos.RPO_X_LEFTTHAN|CANRefPos.RPO_X_RIGHTTHAN)) == 0)
			{
				this.setDx(rO.getDx());
			}
		}
		if((FLAG&CANRefPos.RPO_Y_BOTTOMTHAN) > 0)
		{
			if(rO instanceof CANWH)
			{
				this.setDy(rO.getDy() + ((CANWH)rO).getHeight() + margin);
			}
			else
			{
				this.setDy(rO.getDy() + margin);
			}
			if((FLAG&(CANRefPos.RPO_X_LEFTTHAN|CANRefPos.RPO_X_RIGHTTHAN)) == 0)
			{
				this.setDx(rO.getDx());
			}
		}
	}
	public void setPosition(int FLAG,CANPosition rO)
	{
		setPosition(FLAG, rO, 0);
	}
	public void setPosition(int FLAG)
	{
		setPosition(FLAG,0);
	}
	public void setPosition(int FLAG,float margin)
	{
		if((FLAG&CANExPos.POS_X_CENTER) > 0)
		{
			float ScreenCentral = R.ScreenW/2;
			float offset = ScreenCentral - this.getCentralX();
			this.setDx(getDx()+offset+margin);
		}
		if((FLAG&CANExPos.POS_Y_CENTER) > 0)
		{
			float ScreenCentral = R.ScreenH/2;
			float offset = ScreenCentral - this.getCentralY();
			this.setDy(getDy()+offset+margin);
		}
		if((FLAG&CANExPos.POS_X_LEFT) > 0)
		{
			this.setDx(0+margin);
		}
		if((FLAG&CANExPos.POS_X_RIGHT) > 0)
		{
			if(this instanceof CANWH)
			{
				this.setDx(R.ScreenW - margin - ((CANWH)this).getWidth());
			}
			else
			{
				this.setDx(R.ScreenW - margin);
			}
		}
		if((FLAG&CANExPos.POS_Y_TOP) > 0)
		{
			this.setDy(0+margin);
		}
		if((FLAG&CANExPos.POS_Y_BOTTOM) > 0)
		{
			if(this instanceof CANWH)
			{
				this.setDy(R.ScreenH - margin - ((CANWH)this).getHeight());
			}
			else
			{
				this.setDy(R.ScreenH - margin);
			}
		}
	}
	public void setDx(float Dx)
	{
		this.Dx = Dx;
	}
	public void setDy(float Dy)
	{
		this.Dy = Dy;
	}
	public void setAlph(float Alph)
	{
		if(Alph > 1.0f)
		{
			Alph = 1.0f;
		}
		else if(Alph < 0.0f)
		{
			Alph = 0.0f;
		}
		this.alpha = Alph;
	}
	public void setColor(String HEXC)
	{
		setColor(new Color(Integer.valueOf(HEXC,16)));
	}
	public void setColor(Color C)
	{
		this.C = C;
	}
	/////////////////��ɫ��
	public void setShaderProgram(String shaderName)
	{
		shaderUniform = new Hashtable<String,Object>();
		shaderUniformType = new Hashtable<String,Integer>();
		shaderAttribute = new Hashtable<String,Float>();
		this.shaderName = shaderName;
	}
	public void setShaderUniform(String uniformName,Object value,int TPFLAG)
	{
		if(this.shaderName != null)
		{
			shaderUniform.put(uniformName, value);
			shaderUniformType.put(uniformName, TPFLAG);
		}
	}
	public void setShaderAttribute(String attributeName,float value)
	{
		if(this.shaderName != null)
		{
			shaderAttribute.put(attributeName, value);
		}
	}
	public Hashtable<String,Float> getAttributeTable()
	{
		return shaderAttribute;
	}
	public void clearShaderProgram()
	{
		this.shaderName = null;
		if(shaderUniform!=null)
		{
			this.shaderUniform.clear();
		}
		if(shaderAttribute!=null)
		{
			shaderAttribute.clear();
		}
	}
	public String getShaderName()
	{
		return this.shaderName;
	}
	public void prepareShaderUniform(GL gl)
	{
		int shader = R.getShaderProgram(shaderName);
		gl.glUseProgram(shader);
		for(String uName:shaderUniform.keySet())
		{
			Object v = shaderUniform.get(uName);
			int loc = gl.glGetUniformLocation(shader,uName);
			if(v instanceof Float)
			{
				Float value = (Float)v;
				gl.glUniform1f(loc,value);
			}
			else if(v instanceof float[])
			{
				float[] value = (float[])v;
				int FLAG = shaderUniformType.get(uName);
				switch(FLAG)
				{
					case ShaderController.TP_FLOATS:{
						gl.glUniform1fv(loc, value.length,value,0);
					}break;
					case ShaderController.TP_VERT2S:{
						gl.glUniform2fv(loc, value.length/2,value,0);
					}break;
					case ShaderController.TP_VERT3S:{
						gl.glUniform3fv(loc, value.length/3,value,0);
					}break;
					case ShaderController.TP_VERT4S:{
						gl.glUniform4fv(loc, value.length/4,value,0);
					}break;
					case ShaderController.TP_VERTE2:{
						gl.glUniform2fv(loc,1,value,0);
					}break;
					case ShaderController.TP_VERTE3:{
						gl.glUniform3fv(loc,1,value,0);
					}break;
					case ShaderController.TP_VERTE4:{
						gl.glUniform4fv(loc,1,value,0);
					}break;
				}
			}
		}
	}
	/////////////////�ϼ�����
	//refDistance,refAngle,ACTIONS
	public float getRefDistance(PObj PO)
	{
		return getPPDistance(getDx(),getDy(),PO.getCentralX(),PO.getCentralY());
	}
	public float getRefDistance(float Dx,float Dy)
	{
		return getPPDistance(getDx(),getDy(),Dx,Dy);
	}
	public float getRefTheta(PObj PO)
	{
		return getPPTheta(getDx(),getDy(),PO.getCentralX(),PO.getCentralY());
	}
	public float getRefTheta(float Dx,float Dy)
	{
		return getPPTheta(getDx(),getDy(),Dx,Dy);
	}
	protected float getPPDistance(float Dx1,float Dy1,float Dx2,float Dy2)
	{
		return (float)Math.sqrt((Dx2-Dx1)*(Dx2-Dx1)+(Dy2-Dy1)*(Dy2-Dy1));
	}
	protected float getPPTheta(float Dx1,float Dy1,float Dx2,float Dy2)
	{
		float dcx,dcy;

		dcx =Dx2 - Dx1;
		dcy =Dy2 - Dy1;
		double t1 = -dcy/(Math.sqrt(dcx*dcx+dcy*dcy));
		if(t1 > 1)
		{
			t1 = 1;
		}
		else if(t1 < -1)
		{
			t1 = -1;
		}
		float result = (float)Math.acos(t1);
		if(dcx > 0)
		{
			return result;
		}
		else
		{
			return (float)Math.PI*2 - result;
		}
	}
}
