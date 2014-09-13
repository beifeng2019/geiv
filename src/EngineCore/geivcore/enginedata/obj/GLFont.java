package geivcore.enginedata.obj;


import geivcore.R;
import geivcore.engineSys.viewcontroller.ModelProjector;
import geivcore.enginedata.canonical.CANFont;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.media.opengl.GL;


public class GLFont extends RectPObj implements CANFont
{
	public BufferedImage fontBufferedImage;
	public String fontTexture;
	public String fontString;
	public String fontName;
	public int fontSize;
	public int fontOpt;
	public int fontOffsetX;
	public int fontOffsetBottom;
	public GLFont(R R, String HEXC, float Dx,
			float Dy, float Width, float Height,String FontName,int FontOpt,int FontSize,String Info,int OffsetX,int OffsetBottom) 
	{
		super(R, HEXC, Dx, Dy, Width, Height);
		fontBufferedImage = new BufferedImage((int)Width,(int)Height,BufferedImage.TYPE_INT_ARGB);
		fontString = Info;
		fontName = FontName;
		fontSize = FontSize;
		fontOpt = FontOpt;
		fontOffsetX = OffsetX;
		fontOffsetBottom = OffsetBottom;
		Graphics fontG = fontBufferedImage.getGraphics();
		Graphics2D fontG_2d=(Graphics2D)fontG;
		fontG_2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		fontG_2d.setColor(new Color(Integer.valueOf(HEXC,16)));
		fontG_2d.setFont(new Font(FontName,FontOpt,FontSize));
		fontG_2d.drawString(Info,OffsetX,Height - OffsetBottom);
		
		fontTexture = ""+fontOffsetX+fontOffsetBottom+fontName+fontString+fontSize+fontOpt+C.toString();
		fontTexture = R.TC.autoInitFont(fontTexture,fontBufferedImage);
	}
	@Override
	public void drawShape(GL gl, ModelProjector refModel)
	{
		if(fontString.equals(""))
		{
			return;
		}
		float cW = refModel.cW;
		float cH = refModel.cH;
		float sW = cW/R.ScreenW;
		float sH = cH/(R.ScreenH);
		
		R.TC.getFontTexture(fontTexture).bind();
		
	    gl.glColor4f(1,1,1,alpha);
	    
	    gl.glBegin(GL.GL_QUADS);
	    gl.glTexCoord2f(0.0f,0.0f);gl.glVertex3f(Dxs[0]*sW,Dys[0]*sH,0.0f);
	    gl.glTexCoord2f(0.0f,1.0f);gl.glVertex3f(Dxs[3]*sW,Dys[3]*sH,0.0f);
	    gl.glTexCoord2f(1.0f,1.0f);gl.glVertex3f(Dxs[2]*sW,Dys[2]*sH,0.0f);
	    gl.glTexCoord2f(1.0f,0.0f);gl.glVertex3f(Dxs[1]*sW,Dys[1]*sH,0.0f);
	    gl.glEnd();
	    gl.glBindTexture(GL.GL_TEXTURE_2D,0);
	}
	public String getFontString()
	{
		return this.fontString;
	}
	public void setFontString(String S)
	{
		int W = fontBufferedImage.getWidth();
		int H = fontBufferedImage.getHeight();
		for(int i = 0;i < W;i++)
		{
			for(int j = 0;j < H;j++)
			{
				fontBufferedImage.setRGB(i,j,0);
			}
		}
		this.fontString = S;
		Graphics fontG = fontBufferedImage.createGraphics();
		Graphics2D fontG_2d=(Graphics2D)fontG;
		fontG_2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		fontG_2d.setColor(C);
		fontG_2d.setFont(new Font(fontName,fontOpt,fontSize));
		fontG_2d.drawString(S,fontOffsetX,getHeight() - fontOffsetBottom);
		fontTexture = ""+fontOffsetX+fontOffsetBottom+fontName+fontString+fontSize+fontOpt+C.toString();
		fontTexture = R.TC.autoInitFont(fontTexture,fontBufferedImage);
	}
	@Override
	public void setColor(String HEXC)
	{
		super.setColor(HEXC);
		this.setFontString(this.getFontString());
	}
	public void setColor(Color C)
	{
		super.setColor(C);
		this.setFontString(this.getFontString());
	}
}
