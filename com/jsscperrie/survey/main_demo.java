package com.jsscperrie.survey;

import java.util.ArrayList;

import javax.swing.Timer;

public class main_demo {
	public static void main(String[] args) {
		
//		Timer.setLogTimers(true);
		
		String[] options = {"BLUE BLUE BLUE BLUE BLUE BLUE", "RED RED RED RED RED RED RED", "GREEN GREEN GREEN GREEN GREEN", "BLACK BLACK"};
		String[] big_options = {"Pink Sherbet","Pink pearl","Pistachio","Platinum","Plum","Portland Orange","Powder blue","Princeton orange","Prussian blue","Psychedelic purple","Puce","Pumpkin","Purple","Purple Heart","Purple Mountain's Majesty","Purple mountain majesty","Purple pizzazz","Purple taupe","Rackley","Radical Red","Raspberry","Raspberry glace","Raspberry pink","Raspberry rose","Raw Sienna","Razzle dazzle rose","Razzmatazz","Red","Red Orange","Red brown","Red violet"};
		ArrayList<Question> testsList0 = new ArrayList<Question>();
		testsList0.add(new Question("WHAT IS THE <strong>Big</strong> SKY? .... I mean it!").setupOptionOption(options, false));
		testsList0.add(new Question("What is colour is the sky?").setupOptionOption(big_options, true));
		testsList0.add(new Question("What is your name?").setupOptionText(true));
		testsList0.add(new Question("Describe something.").setupOptionText(false));
		testsList0.add(new Question("How many pigs were produced before or during Feb 11, 2013?").setupOptionSlider(0, 200));
		testsList0.add(new Question("Only show Red and Orange")
			.setupOptionFile("src/com/jsscperrie/survey/main_demo.java", "this_line_right_here","jsscpe[r]*ie")); // so meta
		testsList0.add(new Question("The dates that only Red and Orange worked on").setupOptionMatch("2013-01-30<BREAK>2013-02-28<BREAK>2013-02-28<BREAK>2013-03-30<BREAK>2013-03-03<BREAK>2013-03-11<BREAK>2013-03-27<BREAK>2013-03-30<BREAK>2013-03-30<BREAK>2013-03-29<BREAK>2013-03-30<BREAK>2013-03-16<BREAK>2013-03-30"));
		
//		ArrayList<Question> testsList = testsList0;
		ArrayList<Question> testsList = new ArrayList<Question>();
		testsList.add(new Question(testsList0, "Bunch of questions"));
//		testsList.add(new Question("WHAT IS THE SKY?").setupOptionOption(options, false));


		
		ArrayList<Question> aq = new ArrayList<Question>();
		aq.add(new Question("What is the first question?").setupOptionOption(options, false));
		aq.add(new Question("What is the first question?").setupOptionOption(options, true));
		aq.add(new Question("How many products were produced before or during Feb 11, 2013?").setupOptionSlider(0, 200));
		aq.add(new Question("Only show Red and Orange Only show Red and "
				+ "Orange Only show Red and Orange Only show Red and Orange "
				+ "Only show Red and Orange Only show Red and Orange")
		.setupOptionFile("src/com/jsscperrie/survey/main_demo.java", "this_line_right_here","jsscpe[r]*ie")); // so meta
		testsList.add(new Question(aq, "SO there"));
		
		String[] names = {"Sung", "Morgan", "Naseem", "Dana", "Ariel", "Robbie", "Madison"};
		ArrayList<Question> rq = new ArrayList<Question>();
		rq.add(new Question("Which individual(s) ate butter?").setupOptionOption(names, false));
		rq.add(new Question("Which individual(s) read books?").setupOptionOption(names, false));
		rq.add(new Question("1 Which individual(s) watch paint dry?").setupOptionOption(names, false));
		rq.add(new Question("2 Which individual(s) cheat on their girlfriend?").setupOptionOption(names, false));
		rq.add(new Question("3 Which individual(s) should know better?").setupOptionOption(names, false));
		rq.add(new Question("4 Which individual(s) tell others to leave their home?").setupOptionOption(names, false));
		rq.add(new Question("5 Which individual(s) pretend they did nothing wrong?").setupOptionOption(names, false));
		testsList.add(new Question(rq, "Hello!"));
		
		
		String[] collab_options = {"1/5 No",
				"2/5 Poor",
				"3/5 Acceptable",
				"4/5 Good",
				"5/5 Excellent. Why is this option so long? Why is this option so long? Why is this option so long? Why is this option so long? Why is this option so long? Why is this option so long? Why is this option so long? Why is this option so long? Why is this option so long?"};
		
		ArrayList<Question> ques1 = new ArrayList<Question>();
		ques1.add((new Question("What is your general assessment of swiss cheese?"))
					.setupOptionOption(collab_options, true));
		ques1.add((new Question("Describe the  nature of the swiss cheese."))
				.setupOptionText(false));
		testsList.add(new Question(ques1,"Heading"));
		testsList.add((new Question("This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. This question is really long. "))
				.setupOptionText(false));
		
		
		boolean hasBreather 
//			= true;
			= false;
		Question[] tests = new Question[testsList.size()];
		for (int t = 0; t < tests.length; t++)
			tests[t] = testsList.get(t);
		BuildSurvey bs = new BuildSurvey(tests, hasBreather, "Session", 1000);
		
		
	}
}
