package jogl;


import geivcore.R;
import geivcore.engineSys.io.keyBoard;
import geivcore.engineSys.io.mouse;
import geivcore.engineSys.screenpainter.Painter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sun.opengl.util.FPSAnimator;

public class JOGL extends JFrame
{
	private static final long serialVersionUID = 1L;
	public R R;
	public Painter listener;
    static FPSAnimator animator=null;  
    public JOGL(R R,String Title,boolean fixWindows) throws HeadlessException 
    {  
        super(Title);

        this.R = R;
        
        setSize(R.getScreenWidth()+8,R.getScreenHeight()+27);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        listener=new Painter(R);
        
        GLCapabilities glcaps=new GLCapabilities();
        
        glcaps.setDoubleBuffered(true);
        
        GLCanvas canvas=new GLCanvas(glcaps);
        
        getContentPane().add(canvas, BorderLayout.CENTER);
        canvas.addGLEventListener(listener); 
        canvas.addKeyListener(new keyBoard(R));
        
        mouse myMouse = new mouse(R);
        Image image = new ImageIcon("." + File.separator + "Data" + File.separator + "DefaultTexture" + File.separator + "DefaultTexture.png").getImage();
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(), null));
        
        canvas.addMouseListener(myMouse.mouseBotton);
        canvas.addMouseMotionListener(myMouse.mouseBotton);
        canvas.addMouseWheelListener(myMouse.mouseWheel);
        
        canvas.setFocusable(true);
        
        animator=new FPSAnimator(canvas,R.FPS,true);  
        setResizable(!fixWindows);
        centerWindow(this);
    }
    public void showFrame()
    {
    	final JOGL Frame = this;
        SwingUtilities.invokeLater(new Runnable() {  
            public void run() {  
                Frame.setVisible(true);  
            }  
        });  
    }
    public void startDraw()
    {
        SwingUtilities.invokeLater(new Runnable() { 
            public void run() {  
                animator.start();  
            }  
        }); 
    }
    private void centerWindow(Component frame) { // ���д���  
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension frameSize = frame.getSize();  
        if (frameSize.width > screenSize.width)  
            frameSize.width = screenSize.width;  
        if (frameSize.height > screenSize.height)  
            frameSize.height = screenSize.height;  
        frame.setLocation((screenSize.width - frameSize.width) >> 1,  
                (screenSize.height - frameSize.height) >> 1);  
    }
}
