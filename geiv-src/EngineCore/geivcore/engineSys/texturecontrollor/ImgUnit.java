package geivcore.engineSys.texturecontrollor;

import java.awt.image.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class ImgUnit 
{
	public String Path;
	public int ImgID;
	public BufferedImage BufferedImage;
	public Texture RefTexture;
	public boolean TextureLoaded = false;
	public int ImgWidth,ImgHeight;
	public ImgUnit(String CompFontOptKey,BufferedImage BufferedFontImage)
	{
		this.Path = "is a Font Texture";
		BufferedImage = BufferedFontImage;
		ImgWidth = BufferedImage.getWidth();
		ImgHeight = BufferedImage.getHeight();
	}
	public ImgUnit(String Path)
	{
		this.Path = Path;
		
		FileInputStream is=null;
		try {is = new FileInputStream(Path);} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		try {BufferedImage = ImageIO.read(is);} 
		catch (IOException e) {e.printStackTrace();} 
		
		ImgWidth = BufferedImage.getWidth();
		ImgHeight = BufferedImage.getHeight();
	}
	public void loadTexture()
	{
		if(TextureLoaded)
		{
			return;
		}
		try{RefTexture = TextureIO.newTexture(BufferedImage, false);
		RefTexture.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
		RefTexture.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
		}catch(Exception e){System.out.println(e.getMessage());
		System.out.println("Error loading texture ");}
		
		TextureLoaded = true;
	}
}
