package geivcore.engineSys.soundsplayer;


import engineextends.util.common.CMVal;
import geivcore.UESI;

import java.io.BufferedInputStream;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;


public class montBGM implements Runnable
{
	UESI UES;
	
	public AudioFormat audioFormat;
	public ByteArrayOutputStream sourceData;
	
	private String filename = null;   
	//private String fileRefName = null;
	BufferedWriter writer;
	boolean canWriter = false;
	float processReg;
	
    private int len = 0;  

    private String chunkdescriptor = null;  
    static private int lenchunkdescriptor = 4;  
  
    //private long chunksize = 0;  
    //static private int lenchunksize = 4;  
  
    private String waveflag = null;  
    static private int lenwaveflag = 4;  
  
    private String fmtubchunk = null;  
    static private int lenfmtubchunk = 4;  
      
    //private long subchunk1size = 0;  
    //static private int lensubchunk1size = 4;  
      
    //private int audioformat = 0;  
    //static private int lenaudioformat = 2;  
      
    private int numchannels = 0;  
    //static private int lennumchannels = 2;  
      
    private long samplerate = 0;  
    //static private int lensamplerate = 2;  
      
    private long byterate = 0;  
    //static private int lenbyterate = 4;  
      
    //private int blockalign = 0;  
    //static private int lenblockling = 2;  
      
    private int bitspersample = 0;  
    //static private int lenbitspersample = 2;  
      
    private String datasubchunk = null;  
    static private int lendatasubchunk = 4;  
      
    private long subchunk2size = 0;  
    //static private int lensubchunk2size = 4;  
	
    private FileInputStream fis = null;  
    private BufferedInputStream bis = null; 
    
    int initFlag;
    int realRead;
    int allLength;
    int allLengthMilTime;
    int crtLength;
    //byte[] tempBuffer = new byte[512];
    
    byte[] playBitBuffer = new byte[1024];
    int playCache = playBitBuffer.length*32;
    byte[] soundBitBuffer;
    
    int [] dataWaveBuffer;
    //int runFlag = 0;
    int status = CMVal.ST_WAIT;
    String ValGroup;
    float Val = 1.0f;
    float TearReg = 1.0f;
    //AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;
    
    List<byte[]> cacheQu;
    int LengthOfCache = 0;
    
    public montBGM(UESI UES)
    {
    	this.UES = UES;
		cacheQu = new ArrayList<byte[]>();
		status = CMVal.ST_PAUSE;
		new Thread(this).start();
    }
	public void LoadWAVfile(String filePath, long BFlag, long EFlag,String ValGroup)
	{
		this.filename = filePath;
		//this.fileRefName = filePath.split(".wav")[0] + ".Rh";
		this.ValGroup = ValGroup;
		this.Val = UES.getMontageSP(ValGroup);
		//File waveRefFile = new File(fileRefName);
		//System.out.println(fileRefName);
		try
		{ 
            fis = new FileInputStream(this.filename);  

            bis = new BufferedInputStream(fis);  

            this.chunkdescriptor = readString(lenchunkdescriptor);  
            if(!chunkdescriptor.endsWith("RIFF"))  
                throw new IllegalArgumentException("RIFF miss, " + filename + " is not a wave file.");  
              
            //this.chunksize = 
            readLong();  
            this.waveflag = readString(lenwaveflag);  
            if(!waveflag.endsWith("WAVE"))  
                throw new IllegalArgumentException("WAVE miss, " + filename + " is not a wave file.");  
              
            this.fmtubchunk = readString(lenfmtubchunk);  
            //System.out.println(fmtubchunk);
            if(!fmtubchunk.endsWith("fmt "))  
                throw new IllegalArgumentException("fmt miss, " + filename + " is not a wave file.");  
            //this.subchunk1size = 
            readLong();  
            //this.audioformat = 
            readInt();  
            this.numchannels = readInt();  
            this.samplerate = readLong();  
            this.byterate = readLong();  
            //this.blockalign = 
            readInt();
            this.bitspersample = readInt();  
              
            this.datasubchunk = readString(lendatasubchunk);  
            if(!datasubchunk.endsWith("data"))  
                throw new IllegalArgumentException("data miss, " + filename + " is not a wave file.");  
            this.subchunk2size = readLong();  
            this.len = (int)(this.subchunk2size/(this.bitspersample/8)); 
            
            sourceData = new ByteArrayOutputStream();
            
            allLength = 0;
            allLength = len*bitspersample/8;
            
            audioFormat = new AudioFormat(samplerate,bitspersample,numchannels,true,false);
            System.out.println("Load Success L:"+allLength);
            allLengthMilTime = (int)((float)allLength/byterate*1000);
            System.out.println("ALL TIme: "+(float)allLength/byterate+" s");
            System.out.println("byte rate:"+byterate);
            
            //int playframeLength = (int)(byterate/32);
            //int playframeLength = 64;
            
            //playBitBuffer = new byte[allLength];
            ////////////////////
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat);
            sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat,playCache);
            sourceDataLine.start();
            
            //byte[] audioData = sourceData.toByteArray();
            //InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
            //audioInputStream = new AudioInputStream(byteArrayInputStream,audioFormat,audioData.length/audioFormat.getFrameSize());
            }catch (Exception e) {  
                e.printStackTrace();  
            }
		}
		
	    public void WAV_play()
	    {
	    	status = CMVal.ST_RUN;
	    	TearReg = 1.0f;
	    }
	    public void WAV_pause()
	    {
	    	if(status == CMVal.ST_RUN)
	    	{
	    		status = CMVal.ST_PAUSE;
	    	}
	    }
	    public void WAV_stop()
	    {
	    	if(status == CMVal.ST_RUN)
	    	{
		    	status = CMVal.ST_STOP;
	    	}
	    }
	    public void WAV_close()
	    {
	    	status = CMVal.ST_CLOSE;
	    }
	    void tearStop()
	    {
	    	if(TearReg > 0.005f)
	    	{
	    		TearReg -= 0.005f;
	    		playAndShow();
	    	}
	    	else
	    	{
	    		TearReg = 1.0f;
	    		crtLength = 0;
	    		this.status = CMVal.ST_PAUSE;
	    	}
	    }
	    void playAndShow()
	    {
	    	try{
	    		if(bis == null)
	    		{
	    			status = CMVal.ST_PAUSE;
	    			return;
	    		}
				if((realRead = bis.read(playBitBuffer, 0,playBitBuffer.length)) != -1)
				{
					crtLength+=realRead;
					processReg = (float)crtLength/allLength;
					for(int i = 0;i < playBitBuffer.length;i++)
					{
						playBitBuffer[i] = (byte)(playBitBuffer[i]*Val*TearReg);
					}
					sourceDataLine.write(playBitBuffer,0,playBitBuffer.length);
				}
				else
				{
					//this.status = CMVal.ST_WAIT;
					//crtLength = 0;
					LoadWAVfile(filename,0,0,ValGroup);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	    }
		@Override
		public void run() 
		{
			Thread.currentThread().setName("MontBG");
			while(status != CMVal.ST_CLOSE)
			{
				switch(status)
				{
					case CMVal.ST_PAUSE:
					{
						try {Thread.sleep(17);} 
						catch (InterruptedException e){}
					}break;
					case CMVal.ST_RUN:
					{
						playAndShow();
					}break;
					case CMVal.ST_STOP:
					{
						tearStop();
					}break;
				}
			}
		}
		private int readInt(){  
	        byte[] buf = new byte[2];  
	        int res = 0;  
	        try {  
	            if(bis.read(buf)!=2)  
	                throw new IOException("no more data!!!");  
	            res = (buf[0]&0x000000FF) | (((int)buf[1])<<8);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return res;  
	    }    
	    private long readLong(){  
	        long res = 0;  
	        try {  
	            long[] l = new long[4];  
	            for(int i=0; i<4; ++i){  
	                l[i] = bis.read();  
	                if(l[i]==-1){  
	                    throw new IOException("no more data!!!");  
	                }  
	            }  
	            res = l[0] | (l[1]<<8) | (l[2]<<16) | (l[3]<<24);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return res;  
	    }
	    private String readString(int len){  
	        byte[] buf = new byte[len];  
	        try {  
	            if(bis.read(buf)!=len)  
	                throw new IOException("no more data!!!");  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return new String(buf);  
	    }
}
