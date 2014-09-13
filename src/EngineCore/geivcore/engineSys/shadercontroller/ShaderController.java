package geivcore.engineSys.shadercontroller;

import geivcore.UESI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Hashtable;

import javax.media.opengl.GL;


public class ShaderController {
	public static final int TP_AFLOAT = 1 << 0;
	public static final int TP_FLOATS = 1 << 1;
	
	public static final int TP_VERT2S = 1 << 2;
	public static final int TP_VERT3S = 1 << 3;
	public static final int TP_VERT4S = 1 << 4;
	
	public static final int TP_VERTE2 = 1 << 5;
	public static final int TP_VERTE3 = 1 << 6;
	public static final int TP_VERTE4 = 1 << 7;
	private boolean needC = false;
	private Hashtable<String,ShaderProgram> SPTable;
	SysShader SYS;
	public ShaderController(UESI UES)
	{
		SPTable = new Hashtable<String,ShaderProgram>();
		SYS = new SysShader(UES);
	}
	public void initSysShaders()
	{
		SYS.initSysShaders();
	}
	public boolean needCreateProgram()
	{
		return needC;
	}
	public void addShaderProgram(String programName,String vecShaderPath,String fraShaderPath)
	{
		synchronized(SPTable)
		{
			SPTable.put(programName, new ShaderProgram(programName,vecShaderPath,fraShaderPath));
		}
		needC = true;
	}
	public int getProgram(String programName)
	{
		ShaderProgram p = SPTable.get(programName);
		if(p!=null)
		{
			return p.shaderProgram;
		}
		else
		{
			return -1;
		}
	}
	public boolean buildProgram(GL gl)
	{
		boolean result = true;
		synchronized(SPTable)
		{
			for(ShaderProgram sp:SPTable.values())
			{
				if(sp.shaderProgram == 0)
				{
					if(!sp.build(gl))
					{
						result = false;
					}
				}
			}
		}
		needC = false;
		return result;
	}
}
class ShaderProgram
{
	int shaderProgram = 0;
	String vecShaderPath;
	String fraShaderPath;
	String spName;
	public ShaderProgram(String spName,String vecShaderPath,String fraShaderPath)
	{
		this.spName = spName;
		this.vecShaderPath = vecShaderPath;
		this.fraShaderPath = fraShaderPath;
	}
	public boolean build(GL gl)
	{
		int vertexShaderProgram = gl.glCreateShader(GL.GL_VERTEX_SHADER);
        int fragmentShaderProgram = gl.glCreateShader(GL.GL_FRAGMENT_SHADER);
        gl.glShaderSource(vertexShaderProgram, 1,loadShader(vecShaderPath), null, 0);
        gl.glCompileShader(vertexShaderProgram);
        gl.glShaderSource(fragmentShaderProgram, 1,loadShader(fraShaderPath), null, 0);
        gl.glCompileShader(fragmentShaderProgram);
        int shaderprogram = gl.glCreateProgram();
        gl.glAttachShader(shaderprogram, vertexShaderProgram);
        gl.glAttachShader(shaderprogram, fragmentShaderProgram);
        
        //gl.glBindAttribLocation(shaderprogram, 5,"sys_pIndex");
        
        gl.glLinkProgram(shaderprogram);
        gl.glValidateProgram(shaderprogram);
        
        //System.out.println("pg:"+shaderprogram+" loc:"+gl.glGetAttribLocation(shaderprogram, "sys_pIndex"));
        ////////Check
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(shaderprogram, GL.GL_LINK_STATUS, intBuffer);
        
        if (intBuffer.get(0) != GL.GL_TRUE)
        {
            gl.glGetProgramiv(shaderprogram, GL.GL_INFO_LOG_LENGTH, intBuffer);
            int size = intBuffer.get(0);
            System.err.println("Program link error: ");
            if (size > 0)
            {
                ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                gl.glGetProgramInfoLog(shaderprogram, size, intBuffer, byteBuffer);
                for (byte b : byteBuffer.array())
                {
                    System.err.print((char) b);
                }
            }
            else
            {
                System.out.println("Unknown");
            }
            this.shaderProgram = -1;
            return false;
        }
        else
        {
        	this.shaderProgram = shaderprogram;
        	return true;
        }
	}
	private String[] loadShader(String name)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
        	File inFile = new File(name);
            InputStream is = new FileInputStream(inFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
                sb.append('\n');
            }
            is.close();
        }
        catch (Exception e)
        {
            System.out.println("Error in load file:"+name);
            e.printStackTrace();
        }
        String result = sb.toString();
        //System.out.println("Shader is \n" + result);
        return new String[]
        {result};
    }
}
