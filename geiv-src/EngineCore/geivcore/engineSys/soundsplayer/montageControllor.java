package geivcore.engineSys.soundsplayer;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.Semaphore;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class montageControllor implements Runnable
{
	static final int ST_RUN = 0;
	static final int ST_TERM = -1;
	public final String DefaultGroup = "DEFGroup";
	int status = ST_RUN;

	SourceDataLine CSDL;
	
	final float samplerate;
	final int bitspersample;
	final int numchannels;
	AudioFormat audioFormat;
	///////////////////////////WAV
	private String chunkdescriptor = null;  
    static private int lenchunkdescriptor = 4;
    private String waveflag = null;  
    static private int lenwaveflag = 4; 
    private String fmtubchunk = null;  
    static private int lenfmtubchunk = 4;  
    private String datasubchunk = null;  
    static private int lendatasubchunk = 4;  
    
    private long subchunk2size = 0;  
    private int len = 0;
    private long byterate = 0;
	//////////////////////////////
	byte[] playBitBuffer = new byte[1024];
	byte[] unMixedBitBuffer = playBitBuffer.clone();
    int playCache = playBitBuffer.length*8;
    
    Semaphore STLock = new Semaphore(1);
    
    Set<String> taskSet = new HashSet<String>();
    Set<String> taskAddCache = new HashSet<String>();
    Set<String> taskDELCache = new HashSet<String>();
    
    Hashtable<String,BuffStreamSP> markedBISUnit = new Hashtable<String,BuffStreamSP>(64); 
    Hashtable<String,Float> WaveRefSP = new Hashtable<String,Float>(); 
	public montageControllor()
	{
		//R.montageControllor = this;
		samplerate = 44100;
		bitspersample = 16;
		numchannels = 2;
		
		addWAVSP(DefaultGroup,1.0f);
		
		audioFormat = new AudioFormat(samplerate,bitspersample,numchannels,true,false);
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat);
		try 
		{
			CSDL = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
			CSDL.open(audioFormat,playCache);
		} 
		catch (LineUnavailableException e) 
		{e.printStackTrace();}
		CSDL.start();
		new Thread(this).start();
	}
	public void setWAVSP(String groupName,float DefaultValue)
	{
		if(Math.abs(DefaultValue) > 1.0)
		{
			return;
		}
		if(WaveRefSP.containsKey(groupName))
		{
			WaveRefSP.put(groupName,DefaultValue);
		}
	}
	public void addWAVSP(String groupName,float DefaultValue)
	{
		if(Math.abs(DefaultValue) > 1.0)
		{
			return;
		}
		WaveRefSP.put(groupName,DefaultValue);
	}
	public float getSPValByGroup(String groupName)
	{
		if(WaveRefSP.containsKey(groupName))
		{
			return WaveRefSP.get(groupName);
		}
		else
		{
			return WaveRefSP.get(DefaultGroup);
		}
	}
	public void initWAVmontage(String WAVPath,String ValGroup)
	{
		if(markedBISUnit.containsKey(WAVPath))
		{
			System.out.println("WAV:"+WAVPath+" has already exists! init abord!");
			return;
		}
		else
		{
			try
			{
				FileInputStream fis = new FileInputStream(WAVPath);  
				BufferedInputStream bis = new BufferedInputStream(fis); 
				
				this.chunkdescriptor = readString(lenchunkdescriptor,bis);  
	            if(!chunkdescriptor.endsWith("RIFF"))  
	                throw new IllegalArgumentException("RIFF miss, " + WAVPath + " is not a wave file.");
	            bis.skip(4);
	            this.waveflag = readString(lenwaveflag,bis);  
	            if(!waveflag.endsWith("WAVE"))  
	                throw new IllegalArgumentException("WAVE miss, " + WAVPath + " is not a wave file.");  
	            this.fmtubchunk = readString(lenfmtubchunk,bis);  
	            if(!fmtubchunk.endsWith("fmt "))  
	                throw new IllegalArgumentException("fmt miss, " + WAVPath + " is not a wave file.");  
	            bis.skip(4);
	            bis.skip(2);
	            if(this.numchannels != readInt(bis))
	            {
	            	 throw new IllegalArgumentException("Only Support channel num:"+numchannels);
	            }
	            if(this.samplerate != readLong(bis))
	            {
	            	throw new IllegalArgumentException("Only Support samplerate num:"+samplerate);
	            }
	            this.byterate = readLong(bis);  
	            bis.skip(2);
	            if(this.bitspersample != readInt(bis))
	            {
	            	throw new IllegalArgumentException("Only Support bitspersample num:"+bitspersample);
	            }
	            this.datasubchunk = readString(lendatasubchunk,bis);  
	            if(!datasubchunk.endsWith("data"))  
	                throw new IllegalArgumentException("data miss, " + WAVPath + " is not a wave file."); 
	            
	            this.subchunk2size = readLong(bis);
	            
	            this.len = (int)(this.subchunk2size/(this.bitspersample/8)); 
	            System.out.println("load:"+WAVPath+" OK! byterate:"+byterate+" len:"+len);
	            
	            bis.mark(len*bitspersample/4);
	            
	            markedBISUnit.put(WAVPath,new BuffStreamSP(ValGroup,bis));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void playWAVmontage(String WAVPath)
	{
		if(!markedBISUnit.containsKey(WAVPath))
		{
			initWAVmontage(WAVPath,DefaultGroup);
		}
		if(!markedBISUnit.containsKey(WAVPath))
		{
			return;
		}
		else
		{
			STLock.acquireUninterruptibly();
			taskAddCache.add(WAVPath);
			try {
				markedBISUnit.get(WAVPath).BIS.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			STLock.release();
		}
		//System.out.println("load using:"+(System.currentTimeMillis() - CRTSTime)+" ms");
	}
	private int readInt(BufferedInputStream bis){  
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
    private long readLong(BufferedInputStream bis){  
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
    private String readString(int len,BufferedInputStream bis){  
        byte[] buf = new byte[len];  
        try {  
            if(bis.read(buf)!=len)  
                throw new IOException("no more data!!!");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return new String(buf);  
    }
	@Override
	public void run() 
	{
		Thread.currentThread().setName("montage");
		try
		{
		while(status != ST_TERM)
		{
				//���playBitBuffer
				clearByteBuffer(playBitBuffer);
				//����ÿ��taskSet��HashTableȡ����ӦMBIS��
				for(String KEY:taskSet)
				{
					//�����̣���MBIS��ȡ��ݵ�unMixedBitBuffer
					BufferedInputStream MBIS = markedBISUnit.get(KEY).BIS;
					
					if(MBIS.read(unMixedBitBuffer,0,unMixedBitBuffer.length) != -1)
					{
						//����ȡ�ɹ���playBitBuffer�������unMixedBitBuffer���
						mixByteBuffer(playBitBuffer,unMixedBitBuffer,getSPValByGroup(markedBISUnit.get(KEY).ValGroup));
					}
					else
					{
						//����ȡ���ļ�β����MBIS���ò���KEY����DELCACHE��
						MBIS.reset();
						taskDELCache.add(KEY);
					}
					
				}
					
				//��CSDLд��playBitBuffer
				CSDL.write(playBitBuffer, 0,playBitBuffer.length);
				//��taskSet��ɾ��DELCACHE
				taskSet.removeAll(taskDELCache);
				//��DELCACHE��ȫ��Ԫ��ɾ��
				taskDELCache.removeAll(taskDELCache);
				
				//��taskSet�����ADDCACHE
				STLock.acquireUninterruptibly();
				taskSet.addAll(taskAddCache);
				//��ADDCACHE��ȫ��Ԫ��ɾ��
				taskAddCache.removeAll(taskAddCache);
				STLock.release();
				//R.wait(3,1);
			}
		}catch(Exception e){}
	}
	private void clearByteBuffer(byte[] buffer)
	{
		for(int i = 0;i < buffer.length;i++)
		{
			buffer[i] = 0;
		}
	}
	private void mixByteBuffer(byte[] dstBuffer,byte[] addBuffer,float Val)
	{
		if(dstBuffer.length != addBuffer.length)
		{
			System.out.println("Mix Error:Length DisMatch!");
			return;
		}
		else
		{
			for(int i = 0;i < dstBuffer.length;i++)
			{
				if(dstBuffer[i]<0&&addBuffer[i]<0)
				{
					//Y = A +B - (A * B / (-127)) 
					dstBuffer[i] = (byte)(dstBuffer[i] + addBuffer[i]*Val - (dstBuffer[i]*addBuffer[i]*Val/(-127)));
				}
				else
				{
					//Y = A + B - A * B / 128 
					dstBuffer[i] = (byte)(dstBuffer[i] + addBuffer[i]*Val - (dstBuffer[i]*addBuffer[i]*Val/128));
				}
			}
		}
	}
}
class BuffStreamSP
{
	public String ValGroup;
	public BufferedInputStream BIS;
	public BuffStreamSP(String V,BufferedInputStream BIS)
	{
		this.ValGroup = V;
		this.BIS = BIS;
	}
}