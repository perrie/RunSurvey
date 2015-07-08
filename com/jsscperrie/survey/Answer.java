package com.jsscperrie.survey;

import java.util.Date;

public class Answer {

	private Date start;
	private Date end;
	private String answer;
	
	public Answer() {
		start = new Date();
	}
	public void setAnswer(String answer) {
		this.answer = answer;
		end = new Date();
	}
	public String toString() {
		return start + "\t" + end + "\t" 
			+ (end.getTime() - start.getTime()) + "\t" + answer;
	}
}
