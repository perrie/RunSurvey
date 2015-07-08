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

public class ReadFileKeepLastLines extends ReadFile{
	
	
	private String[] lastLines;
	private int lastLinesIndex;
	
	public ReadFileKeepLastLines(String fileName, int lastLinesLength) throws Exception {
		super(fileName);
		this.lastLines = new String[lastLinesLength];
		this.lastLinesIndex = -1;
		readFile();
	}
	public void doSomething(String strLine) {
        // Print line
    	lastLinesIndex++;
        lastLines[lastLinesIndex%lastLines.length] = strLine;
	}
	
	public String getLastLine(int nth) {
		if (lastLinesIndex < nth) {
			System.err.println("Aren't enough lines recorded: " 
					+ lastLinesIndex + " < " + nth);
			return "";
		}
		return lastLines[(lastLinesIndex - nth) % lastLines.length];
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lastLines.length; i++) {
			sb.append(i + ") " + lastLines[i] + "\n");
		}
		sb.append("lastLinesIndex (" + lastLinesIndex + ") "
				+ "starts at " + (lastLinesIndex) %lastLines.length + "\n");
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		int recordNumber = 5;
		ReadFileKeepLastLines rf = new ReadFileKeepLastLines("/home/perrie/test_console.txt",
				recordNumber);
		
		for (int i = 0; i < recordNumber; i++) { System.out.println(rf.getLastLine(i)); }
		//System.out.println("READING:" + rf);
	}
}