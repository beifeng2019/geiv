package geivcore.enginedata.obj;

import geivcore.engineSys.viewcontroller.ModelProjector;
import geivcore.enginedata.canonical.CANPath;

import javax.media.opengl.GL;


public class GLImage extends RectPObj implements CANPath
{
	public String ImgHashCode;
	public GLImage(geivcore.R R, float Dx,float Dy, float Width, float Height,String Path) {
		super(R, "FFFFFF",Dx, Dy, Width, Height);
		ImgHashCode = R.TC.autoInitImg(Path);
	}
	public void setPath(String Path)
	{
		setPath(Path,false);
	}
	@Override
	public void setPath(String Path, boolean autoSize) {
		ImgHashCode = R.TC.autoInitImg(Path);
		if(autoSize)
		{
			this.setWidth(R.TC.getTextureWidth(Path));
			this.setHeight(R.TC.getTextureHeight(Path));
		}
	}
	@Override
	public void drawShape(GL gl, ModelProjector refModel) 
	{
		float cW = refModel.cW;
		float cH = refModel.cH;
		float sW = cW/R.ScreenW;
		float sH = cH/(R.ScreenH);
		
		R.TC.getTexture(ImgHashCode).bind();
		//gl.glBindTexture(GL.GL_TEXTURE_2D,R.TC.getTexture(ImgHashCode).getTextureObject());
		gl.glColor4f((float)C.getRed()/255, (float)C.getGreen()/255,(float)C.getBlue()/255,alpha);
		
	    gl.glBegin(GL.GL_QUADS);
	    
	    gl.glTexCoord2f(0.0f,0.0f);gl.glVertex3f(Dxs[0]*sW,Dys[0]*sH,0.0f);
	    gl.glTexCoord2f(0.0f,1.0f);gl.glVertex3f(Dxs[3]*sW,Dys[3]*sH,0.0f);
	    gl.glTexCoord2f(1.0f,1.0f);gl.glVertex3f(Dxs[2]*sW,Dys[2]*sH,0.0f);
	    gl.glTexCoord2f(1.0f,0.0f);gl.glVertex3f(Dxs[1]*sW,Dys[1]*sH,0.0f);
	    gl.glEnd();
	    gl.glBindTexture(GL.GL_TEXTURE_2D,0);
	}
}
