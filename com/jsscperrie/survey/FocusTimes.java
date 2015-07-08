package com.jsscperrie.survey;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FocusTimes implements FocusListener,ItemListener, ChangeListener {
	
	private StringBuilder timesRecord = null;
	private long timeInUse;
	private long lastTimeGained;
	private static long staticLastTimeGained;
	
	
	public FocusTimes() {
		timesRecord = new StringBuilder();
		lastTimeGained = -1;
		timeInUse = 0;
		staticLastTimeGained = -1;
	}
	
	public void addTab() {
		
	}
	public void addToTimesRecord(String input) {
		if (timesRecord.length() != 0)
			timesRecord.append(" ");
		timesRecord.append(input);
	}

	@Override
	public void focusGained(FocusEvent e) {
		long time = new Date().getTime();
		if (lastTimeGained == -1)
			lastTimeGained = time;
		addToTimesRecord("fG:" + time);
	}

	@Override
	public void focusLost(FocusEvent e) {
		long time = new Date().getTime();
		if (time > lastTimeGained) {
			timeInUse += time - lastTimeGained;
			lastTimeGained = -1;
			staticLastTimeGained = time;
		}
		addToTimesRecord("fL:" + time);
	}
	
	public String toString() {
		return "tot:" + timeInUse + " " + (new String(timesRecord));
	}

	private void stateChangedCommon() {
		long time = new Date().getTime();
		if (staticLastTimeGained == -1 || lastTimeGained == -1) {
			staticLastTimeGained = time;
			lastTimeGained = time; // not used, but indicates if we've never 
				// touched this question
		}
		timeInUse += time - staticLastTimeGained;
		addToTimesRecord("iS:" + time);
		staticLastTimeGained = time;
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		stateChangedCommon();		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		stateChangedCommon();
	}

}
