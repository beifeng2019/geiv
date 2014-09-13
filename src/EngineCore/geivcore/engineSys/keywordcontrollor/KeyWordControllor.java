package geivcore.engineSys.keywordcontrollor;


import geivcore.R;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import javax.media.opengl.GL;
//import java.util.concurrent.Semaphore;


import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;


public class KeyWordControllor 
{
	//Semaphore InfoLock = new Semaphore(1);
	//Semaphore TextLock = new Semaphore(1);
	R R;
	Hashtable<String,keyInfo> KTYPEINFO = new Hashtable<String,keyInfo>(64); 
	Hashtable<String,Texture> KTYPETEXT = new Hashtable<String,Texture>(64); 
	Hashtable<String,String> KTYPEPATH = new Hashtable<String,String>(64); 
	public KeyWordControllor(R R)
	{
		this.R = R;
	}
	public void addKWordTYPE(String keyID,String FontType,String HEXColor,int FontSize,int FontOpt,int KWidth,int KHeight,int OffsetX,int OffsetB)
	{
		if(!KTYPEINFO.containsKey(keyID))
		{
			keyInfo newInfo = new keyInfo(keyID,FontType,HEXColor,FontSize,FontOpt,KWidth,KHeight,OffsetX,OffsetB);
			KTYPEINFO.put(keyID, newInfo);
		}
	}
	public void addKWordTYPE(String keyID)
	{
		if(!KTYPEINFO.containsKey(keyID))
		{
			keyInfo newInfo = new keyInfo(keyID);
			KTYPEINFO.put(keyID, newInfo);
		}
	}
	public void setKWordPath(String keyID,char C,String Path)
	{
		keyInfo inf = KTYPEINFO.get(keyID);
		if(!inf.isSysFont)
		{
			if(!KTYPEPATH.containsKey(keyID+C))
			{
				KTYPEPATH.put(keyID+C,Path);
			}
			else
			{
				System.out.println(C+" in "+keyID+" has already exists");
			}
		}
		else
		{
			System.out.println("SysFont Can Only Be Set Autoly");
		}
	}
	public void preInit(String keyID,char C)
	{
		createKeyTexture(keyID,C);
	}
	public Texture getTextureByKey(String keyID,char C)
	{
		if(KTYPEINFO.containsKey(keyID))
		{
			Texture res = null;
			keyInfo inf = KTYPEINFO.get(keyID);
			if(inf.isSysFont)
			{
				if(KTYPETEXT.containsKey(keyID+C))
				{
					res = KTYPETEXT.get(keyID+C);
				}
				else
				{
					createKeyTexture(keyID,C);
					res = KTYPETEXT.get(keyID+C);
				}
				return res;
			}
			else
			{
				if(KTYPEPATH.containsKey(keyID+C))
				{
					res = R.TC.getTexture(KTYPEPATH.get(keyID+C));
				}
				else
				{
					System.out.println("Missing:"+C);
					System.out.println("NotSysFont Must Be Set Manualy");
				}
				return res;
			}
		}
		else
		{
			System.out.println("UNKNOWN KeyWordID:"+keyID);
			return null;
		}
	}
	private void createKeyTexture(String keyID,char C)
	{
		keyInfo inf = KTYPEINFO.get(keyID);
		if(inf.isSysFont)
		{
			String CC = C + "";
			
			if(KTYPETEXT.containsKey(keyID+CC))
			{
				return;
			}
			Texture DstTexture = null;
			
			BufferedImage charBufferedImage = new BufferedImage(inf.KWidth,inf.KHeight,BufferedImage.TYPE_INT_ARGB);
			Graphics charG = charBufferedImage.getGraphics();
			
			Graphics2D charG_2d=(Graphics2D)charG;
			charG_2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			charG_2d.setColor(new Color(Integer.valueOf(inf.HEXColor,16)));
			charG_2d.setFont(new Font(inf.FontType,inf.FontOpt,inf.FontSize));
			charG_2d.drawString(CC,inf.OffsetX,inf.KHeight - inf.OffsetB);
			
			DstTexture = LoadTexture(charBufferedImage);
			
			KTYPETEXT.put(keyID+CC,DstTexture);
		}
		else
		{
			System.out.println("NotSysFont Must Be Set Manualy");
		}
	}
	private Texture LoadTexture(BufferedImage BufferedImage)
	{
		Texture RefTexture = null;
		
		try{RefTexture = TextureIO.newTexture(BufferedImage, false);
		RefTexture.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
		RefTexture.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
		}catch(Exception e){System.out.println(e.getMessage());
		System.out.println("Error loading Font texture ");}
		
		return RefTexture;
	}
}
class keyInfo
{
	public boolean isSysFont = true;
	public String keyID;
	public String FontType;
	public String HEXColor;
	public int FontSize;
	public int FontOpt;
	public int KWidth;
	public int KHeight;
	public int OffsetX;
	public int OffsetB;
	public keyInfo(String keyID)
	{
		isSysFont = false;
	}
	public keyInfo(String keyID,String FontType,String HEXColor,int FontSize,int FontOpt,int KWidth,int KHeight,int OffsetX,int OffsetB)
	{
		isSysFont = true;
		this.keyID = keyID;
		this.FontType = FontType;
		this.HEXColor = HEXColor;
		this.FontSize = FontSize;
		this.FontOpt = FontOpt;
		this.KWidth = KWidth;
		this.KHeight = KHeight;
		this.OffsetX = OffsetX;
		this.OffsetB = OffsetB;
	}
}