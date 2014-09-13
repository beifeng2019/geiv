package com.thrblock.util;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;


public class RandomSet 
{
	static Random random = new Random();
	//获得一个给定范围的随机整数
	public static int getRandomNum(int smallistNum,int BiggestNum)
	{
		return (Math.abs(random.nextInt())%(BiggestNum-smallistNum+1))+smallistNum;
	}
	//获得一个随机的布尔值
	public static boolean getRandomBoolean()
	{
		return (getRandomNum(0,1) == 1);
	}
	//获得一个随机在0~1的浮点数
	public static float getRandomFloatIn_1()
	{
		return (float)getRandomNum(0,1000)/1000;
	}
	//获得一个随机的颜色
	public static Color getRandomColor()
	{
		float R = (float)getRandomNum(0,255)/255;
		float G = (float)getRandomNum(0,255)/255;
		float B = (float)getRandomNum(0,255)/255;
		
		return new Color(R,G,B);
	}
	//获得一个随机的暖色调
	public static Color getRandomWarmColor(){
		int Ri = getRandomNum(0,255);
		int Gi = getRandomNum(0,Ri);
		int Bi = getRandomNum(0,Gi);
		
		float R = (float)Ri/255;
		float G = (float)Gi/255;
		float B = (float)Bi/255;
		
		return new Color(R,G,B);
	}
	//获得一个随机的冷色调
	public static Color getRandomColdColor(){
		int Ri = getRandomNum(0,255);
		int Gi = getRandomNum(Ri,255);
		int Bi = getRandomNum(Gi,255);
		
		float R = (float)Ri/255;
		float G = (float)Gi/255;
		float B = (float)Bi/255;
		
		return new Color(R,G,B);
	}
	//以一定概率返回一个布尔值
	public static boolean getRate(int rate)
	{
		if(rate<0 || rate > 100)
		{
			return false;
		}
		else
		{
			if(getRandomNum(0,100)<rate)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	//返回给定数组中的一个随机元素
	public static <T> T getElement(T[] t)
	{
		int index = getRandomNum(0,t.length - 1);
		return t[index];
	}
	//返回给定Collection中的一个随机元素
	public static <T> T getElement(Collection<? extends T> c)
	{
		int atmp = getRandomNum(0,c.size() - 1);
		Iterator<? extends T> iter = c.iterator();
		while(atmp > 0)
		{
			atmp--;
			iter.next();
		}
		return iter.next();
	}
}
