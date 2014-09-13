package com.thrblock.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ProStorage {
	Properties P;
	String proPath;
	InputStream inputStream = null;
	BufferedReader bf;
	public ProStorage()
	{
		P = new Properties();
	}
	public void loadProperty(String proPath)
	{
		this.proPath = proPath;
		File pro = new File(proPath);
		try {
			inputStream = new FileInputStream(pro);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		bf = new BufferedReader(new InputStreamReader(inputStream));
		try {
			P.clear();
			P.load(bf);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public String getProperty(String key)
	{
		String old = P.getProperty(key);
		return old;
	}
	public void setProperty(String key,String value)
	{
		P.setProperty(key, value);
	}
	public void close()
	{
		try {
			bf.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void store()
	{
		try {
			FileOutputStream fos = new FileOutputStream(proPath);
			P.store(fos, "JAVA PROPERTY FILE");
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
