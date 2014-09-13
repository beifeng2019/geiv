package geivcore.enginedata.obj;


import geivcore.engineSys.viewcontroller.ModelProjector;
import geivcore.enginedata.canonical.CANFont;
import geivcore.enginedata.canonical.CANKeySet;

import java.awt.Color;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;


public class GLWordSet extends PObj implements CANKeySet,CANFont
{
	char[] Info;
	String KeySetName;
	String KeySet;
	public GLWordSet(geivcore.R R,float Dx, float Dy,String keySetName,String Info) 
	{
		super(R, Color.WHITE, Dx, Dy);
		this.Info = Info.toCharArray();
		this.KeySetName = keySetName;
		KeySet = Info;
	}
	@Override
	public void drawShape(GL gl, ModelProjector refModel) 
	{
		float cW = refModel.cW;
		float cH = refModel.cH;
		float sW = cW/R.ScreenW;
		float sH = cH/(R.ScreenH);
		
		Texture t = null;
		for(int i = 0;i < Info.length;i++)
		{
			t = R.KC.getTextureByKey(KeySetName,Info[i]);
			if(t == null){
				continue;
			}
			t.bind();
			
		    gl.glColor4f(1,1,1,alpha);
		    gl.glBegin(GL.GL_QUADS);
		    
			gl.glTexCoord2f(0.0f,0.0f);gl.glVertex3f((Dx + t.getWidth()*i)*sW,Dy*sH,0.0f);
		    gl.glTexCoord2f(1.0f,0.0f);gl.glVertex3f((Dx + t.getWidth()*(i+1))*sW,Dy*sH,0.0f);
		    gl.glTexCoord2f(1.0f,1.0f);gl.glVertex3f((Dx + t.getWidth()*(i+1))*sW,(Dy+t.getHeight())*sH,0.0f);
		    gl.glTexCoord2f(0.0f,1.0f);gl.glVertex3f((Dx + t.getWidth()*i)*sW,(Dy+t.getHeight())*sH,0.0f);
		    
		    gl.glEnd();
		}
		gl.glBindTexture(GL.GL_TEXTURE_2D,0);
	}
	@Override
	public void referanceKeyWord(char[] cs) {
		Info = cs;
	}
	public void setKeyWord(String S)
	{
		Info = S.toCharArray();
		KeySet = S;
	}
	public String getKeyWord()
	{
		return KeySet;
	}
	@Override
	public void setFontString(String S) {
		setKeyWord(S);
	}
	@Override
	public String getFontString() {
		return getKeyWord();
	}
}
