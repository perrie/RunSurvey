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

public class ReadFileGetLastLineWith extends ReadFile{
	
	
	
	private String key;
	private String answer;
	
	public ReadFileGetLastLineWith(String fileName, String key) throws Exception {
		super(fileName);
		this.key = key;
		this.answer = "";
		readFile();
	}
	public void doSomething(String strLine) {
		if (strLine.indexOf(key) != -1)
			answer = strLine;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public String toString() {
		return "[" + key + "=" + answer + "]";
	}
	
	public static void main(String[] args) throws Exception {
		ReadFileGetLastLineWith rf = new ReadFileGetLastLineWith("/home/perrie/test_console.txt",
				"print_totals");
		System.out.println("READING:" + rf.getAnswer());
	}
}
