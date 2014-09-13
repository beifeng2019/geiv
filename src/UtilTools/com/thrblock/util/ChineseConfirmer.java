package com.thrblock.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseConfirmer 
{
	public static boolean isChinese(char c) 
	{
		  String regEx = "[\u4e00-\u9fa5]";
		  Pattern p = Pattern.compile(regEx);
		  Matcher m = p.matcher(c + "");
		  if(m.find())
		  {
			  return true;
		  }
		  return false;
	}
}
