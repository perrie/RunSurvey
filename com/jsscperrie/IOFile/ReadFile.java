package com.jsscperrie.IOFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * A simple program to read files.
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

public class ReadFile {
	
	private String fileName;
	
	public ReadFile(String fileName) throws Exception {
		this.fileName = fileName;
	}
	
	public void readFile() throws Exception {
      
      try{
         //Open input stream for reading the text file MyTextFile.txt
         InputStream is = new FileInputStream(fileName);
         
         // create new input stream reader
         InputStreamReader instrm = new InputStreamReader(is);
         
         // Create the object of BufferedReader object
         BufferedReader br = new BufferedReader(instrm);
      
         String strLine;
         
         // Read one line at a time 
         while((strLine = br.readLine()) != null)
         {  
            // Print line
        	doSomething(strLine);
         }
         
         br.close();
         instrm.close();
         is.close();
         
      }catch(Exception e){
         e.printStackTrace();
      }
   }
	
	public void doSomething(String strLine) {
		System.out.println(strLine);
	}
	
	public String toString() {
		return "File to read: " + this.fileName;
	}
	
	public static void main(String[] args) throws Exception {
		
		ReadFile rf = new ReadFile("/home/perrie/test_console.txt");
		rf.readFile();
		
		//System.out.println("READING:" + rf);
	}
}