package engineextend.components;


import java.util.ArrayList;
import java.util.List;

import engineextend.components.views.GL_MutiTextView;
import geivcore.UESI;


public class GL_MutiText extends GL_Component
{
	List<Integer> MainFonter;
	GL_MutiTextView mutiTextView;
	public GL_MutiText(UESI UES,GL_MutiTextView mutiTextView) 
	{
		super(UES,mutiTextView);
		MainFonter = new ArrayList<Integer>();
	}
	public void setMutiText(String Info,int lineChars)
	{
		mutiTextView.setMutiLine(MainFonter,lineChars,Info);
	}
}
