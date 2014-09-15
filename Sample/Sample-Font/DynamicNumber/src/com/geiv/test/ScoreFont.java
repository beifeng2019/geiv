package com.geiv.test;

import geivcore.SerialTask;
import geivcore.UESI;
import geivcore.enginedata.canonical.CANExPos;
import geivcore.enginedata.obj.Obj;

import java.util.Arrays;

public class ScoreFont implements SerialTask{
	char[] score;
	Obj font;
	public ScoreFont(UESI UES){
		font = UES.creatObj(UESI.BGIndex);
		UES.addKWordTYPE("myKeyName");
		for(char c = '0'; c <= '9';c++){
			UES.setKWord("myKeyName",c,".\\ScoreFont\\Num_" + c + ".png");
		}
		score = new char[6];
		Arrays.fill(score, '0');
		font.addGLWordSet(0,0,"myKeyName","000000");
		font.referanceKeyWord(score);
		font.setPosition(CANExPos.POS_CENTER);
		font.show();
		
		UES.addSerialTask(this);
	}
	@Override
	public void Serial(int clock) {
		addNum(5);
	}
	private void addNum(int index){
		if(score[index] < '9'){
			score[index] ++;
		}else{
			score[index] = '0';
			addNum(index - 1);
		}
	}
}
