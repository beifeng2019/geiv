package geivcore.engineSys.texturecontrollor;


import geivcore.R;

import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.concurrent.locks.ReentrantLock;


import com.sun.opengl.util.texture.Texture;

public class TextureControllor 
{
	R R;
	public TextureControllor(R R)
	{
		this.R = R;
	}
	ReentrantLock ImgUnitLock = new ReentrantLock();
	Hashtable<String,ImgUnit> ImgUnit = new Hashtable<String,ImgUnit>(64); 
	public synchronized String autoInitImg(String path)//��� û�е�������룬�еĺ���
	{
		if(ImgUnit.containsKey(path))
		{
			return path;
		}
		else
		{
			ImgUnitLock.lock();
			ImgUnit.put(path,new ImgUnit(path));
			ImgUnitLock.unlock();
			return path;
		}
	}
	public synchronized String autoInitFont(String CompFontOptKey,BufferedImage BufferedFontImage)
	{
		if(ImgUnit.containsKey(CompFontOptKey))
		{
			return CompFontOptKey;
		}
		else 
		{
			ImgUnitLock.lock();
			ImgUnit.put(CompFontOptKey,new ImgUnit(CompFontOptKey,BufferedFontImage));
			ImgUnitLock.unlock();
			return CompFontOptKey;
		}
	}
	public Texture getFontTexture(String key)
	{
		if(!ImgUnit.containsKey(key))
		{
			System.out.println(key+":Not Found!");
		}
		ImgUnit refRes = ImgUnit.get(key);
		if(!refRes.TextureLoaded)
		{
			refRes.loadTexture();
		}
		return refRes.RefTexture;
	}
	public Texture getTexture(String path)
	{
		if(!ImgUnit.containsKey(path))
		{
			autoInitImg(path);
		}
		ImgUnit refRes = ImgUnit.get(path);
		if(!refRes.TextureLoaded)
		{
			refRes.loadTexture();
		}
		return refRes.RefTexture;
	}
	public void loadAllUnLoadedTexture()
	{
		ImgUnitLock.lock();
		for(String key:ImgUnit.keySet())
		{
			ImgUnit refRes = ImgUnit.get(key);
			if(!refRes.TextureLoaded)
			{
				refRes.loadTexture();
			}
		}
		ImgUnitLock.unlock();
	}
	public int getTextureWidth(String path)
	{
		if(!ImgUnit.containsKey(path))
		{
			autoInitImg(path);
		}
		ImgUnit refRes = ImgUnit.get(path);
		return refRes.ImgWidth;
	}
	public int getTextureHeight(String path)
	{
		if(!ImgUnit.containsKey(path))
		{
			autoInitImg(path);
		}
		ImgUnit refRes = ImgUnit.get(path);
		return refRes.ImgHeight;
	}
}
