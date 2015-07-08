package com.jsscperrie.IOFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * A simple program to read files and save the last (user-supplied amount) number of lines.
 * This is useful when just checking the parameters sent to a dump file.
 * @author perrie
 * 
 * Credits:
 * 
 * _Code to read file_
 * URL: http://www.devmanuals.com/tutorials/java/corejava/files/java-read-large-file-efficiently.html
 * Posted: July 6, 2014
 * Author: DevManuals.com
 * Accessed: May 26, 2015
 *
 */
// Based on: http://www.devmanuals.com/tutorials/java/corejava/files/java-read-large-file-efficiently.html;

public class ReadFileKeepAllLines extends ReadFile{
	
	
	private String[] allLines;
	private int lastLinesIndex;
	
	public ReadFileKeepAllLines(String fileName, int bufferLength) throws Exception {
		super(fileName);
		this.allLines = new String[bufferLength];
		this.lastLinesIndex = -1;
		readFile();
	}
	public void doSomething(String strLine) {
        // Print line
    	lastLinesIndex++;
    	allLines[lastLinesIndex % allLines.length] = strLine;
	}
	public int getLastLinesIndex() {
		return lastLinesIndex;
	}
	
	public String getLine(int nth) {
		if (lastLinesIndex < nth) {
			System.err.println("Aren't enough lines recorded: " 
					+ lastLinesIndex + " < " + nth);
			return "";
		}
		return allLines[nth % allLines.length];
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < allLines.length; i++) {
			sb.append(i + ") " + allLines[i] + "\n");
		}
		sb.append("lastLinesIndex (" + lastLinesIndex + ") "
				+ "starts at " + (lastLinesIndex) %allLines.length + "\n");
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		int recordNumber = 5;
		ReadFileKeepAllLines rf = new ReadFileKeepAllLines("/home/perrie/test_console.txt",
				recordNumber);
		
		for (int i = 0; i < recordNumber; i++) { System.out.println(rf.getLine(i)); }
		//System.out.println("READING:" + rf);
	}
}