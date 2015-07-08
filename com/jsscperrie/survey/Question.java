package com.jsscperrie.survey;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import com.jsscperrie.IOFile.ReadFileGetLastLineWith;

public class Question {
	
	// instance variables
	private JPanel question = null;
	private JPanel option = null;
	private JRadioButton checkboxIDK = null;
	private Question[] subQuestions = null;
	private int type;
	private double[] compHeight;
	private String[] hiddenStrings;
	private FocusTimes times = null;
	
	// CONSTANTS
	public static final int RADIO = 0;
	public static final int CHECKBOX = 1;
	public static final int TEXT_FIELD = 2;
	public static final int TEXT_AREA = 3;
	public static final int SPINNER = 4;
	public static final int BUTTON = 5;
	public static final int MULTI_QUESTIONS = 6;
	public static final int TEXT_AREA_IS_ANSWER = 7;
	public static final String[] TYPES = {"RADIO", "CHECKBOX", "TEXT_FIELD", "TEXT_AREA",
			"SPINNER", "BUTTON", "MULTI_QUESTIONS", "TEXT_AREA_IS_ANSWER"};
	public static int totalQuestions = 0;
	
	
	
	
	public Question() {
		type = -1;
//		isIDK = false;
		compHeight = new double[2];
		compHeight[0] = 0;
		compHeight[1] = 0;
	}
	public Question(String question) {
		type = -1;
//		isIDK = false;
		compHeight = new double[2];
		compHeight[0] = 0;
		compHeight[1] = 0;
		setupQuestion(question);
	}
	public Question(Question[] subQuestions, String overall) {
		setupQuestions(subQuestions, overall);
	}
	public Question(ArrayList<Question> subQuestions, String overall) {
		this.subQuestions = new Question[subQuestions.size()];
		setupQuestions(subQuestions.toArray(this.subQuestions), overall);
	}
	
	public double getHeight() {
		return compHeight[0] + compHeight[1];
	}
	public int getType() {
		return type;
	}
	
	/**
	 * Set up other variables for multiquestion situation
	 * @param array
	 */
	private void setupQuestions(Question[] array, String overall) {
		this.subQuestions = array;
		type = MULTI_QUESTIONS;
		compHeight = new double[2];
		compHeight[1] = 0;

		if (overall.equals("")) {
			question = new JPanel();
			question.setVisible(false);
			compHeight[0] = 0;
		} else {
			this.setupQuestion(overall);
		}
		option = new JPanel();
		option.setLayout(new SpringLayout());
				
		for (int o = 0; o < array.length; o++) {
			
			array[o].getOption().setAlignmentX(Component.LEFT_ALIGNMENT);
			option.add(array[o].getQuestion());
			option.add(array[o].getOption());
			
			((SpringLayout) option.getLayout()).putConstraint(
				SpringLayout.NORTH, array[o].getOption(),
				0, SpringLayout.SOUTH, array[o].getQuestion());
			if (o != 0) {
				JSeparator jsep = new JSeparator(SwingConstants.HORIZONTAL);
				jsep.setPreferredSize(new Dimension(BuildSurvey.TEXT_WIDTH,3));
				option.add(jsep);
				((SpringLayout) option.getLayout()).putConstraint(
						SpringLayout.NORTH, jsep,
						10, SpringLayout.SOUTH, array[o-1].getOption());
				((SpringLayout) option.getLayout()).putConstraint(
						SpringLayout.NORTH, array[o].getQuestion(),
						5, SpringLayout.SOUTH, jsep);
				compHeight[1] += 5 + 10 + jsep.getPreferredSize().getHeight();
//				System.out.println(jsep.getPreferredSize());
			} else 
				((SpringLayout) option.getLayout()).putConstraint(
						SpringLayout.NORTH, option,
						5, SpringLayout.NORTH, array[o].getQuestion());
			compHeight[1] += array[o].getQuestion().getPreferredSize().getHeight()
					+ array[o].getOption().getPreferredSize().getHeight();
			
		}
	}
	
	/** 
	 * Static functions to add <br /> around strings that are too long!
	 */
	 
	/**
	 * Adds <br /> in string so that it is "shorter"
	 * @param text the string in question
	 * @return the string fixed
	 */
	public static String fix(String text) {
		return fix(text,0);
	}
	/**
	 * Adds <br /> in string so that it is "shorter"
	 * @param text the string in question
	 * @param extraPadding additional padding (useful when the string is supposed to be even shorter than usual
	 * @return the string fixed
	 */
	public static String fix(String text, int extraPadding) {
		JLabel jc = new JLabel(text);
		
		if (jc.getPreferredSize().getWidth() > BuildSurvey.TEXT_WIDTH - extraPadding) {
			String[] words = text.split(" ");
			StringBuilder accept = new StringBuilder(words[0]);
			
			for (int w = 1; w < words.length; w++) {
				StringBuilder potent = new StringBuilder(accept);
				potent.append(" " + words[w]);
				jc.setText("<html>" + potent + "</html>");
				if (jc.getPreferredSize().getWidth() > BuildSurvey.TEXT_WIDTH - extraPadding - extraPadding)
					accept.append("<br>" + words[w]);
				else
					accept.append(" " + words[w]);
			}
			text = accept.toString();
		}
		return "<html>" + text + "</html>";
	}
	
	// question
	public Component getQuestion() {
		return question;
	}
	public void setupQuestion(String input) {
		
		times = new FocusTimes();
		
		question = new JPanel();
		question.setLayout(new BoxLayout(question, BoxLayout.PAGE_AXIS));
		
		checkboxIDK = new JRadioButton("I don't know");
		checkboxIDK.setFont(new Font("Serif", Font.ITALIC, 12));

		String intro = "";
		JLabel labelQuestion = new JLabel(fix(intro + input));
		labelQuestion.setFont(new Font("Serif", Font.PLAIN, 12));
		labelQuestion.setAlignmentX(Component.RIGHT_ALIGNMENT);
		question.setAlignmentX(Component.CENTER_ALIGNMENT);
				
		question.add(labelQuestion);
		question.setBorder(BorderFactory.createEmptyBorder(0, 0, BuildSurvey.PADDING, 0));
		compHeight[0] = question.getPreferredSize().getHeight();
	}
	
	// option
	public JPanel getOption() {
		return option;
	}
	/**
	 * Sets up check box or radio buttons
	 * @param inputs option labels
	 * @param isRadio if true use radio (user can only select 1) or checkboxes (user may select many)
	 */
	public Question setupOptionOption(String[] inputs, boolean isRadio) {
		
		option = new JPanel();
		option.setLayout(new BoxLayout(option, BoxLayout.Y_AXIS));
		option.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		if (isRadio) {
			type = RADIO;
			JRadioButton[] buttons = new JRadioButton[inputs.length];
			hiddenStrings = new String[1];
			hiddenStrings[0] = "-1";
			for (int i = 0; i < inputs.length; i++) { 
				buttons[i] = new JRadioButton(fix(inputs[i], 20));
			}
			final ButtonGroup group = new ButtonGroup();
			for (int i = 0; i < buttons.length; i++) { group.add(buttons[i]); }
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setFont(new Font("Serif", Font.PLAIN, 12));
				buttons[i].addItemListener(times);
				buttons[i].setMnemonic(i);
				buttons[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent ae) {
						JRadioButton src = (JRadioButton) ae.getSource();
						int i = src.getMnemonic();
						if (src.isSelected()) 
							if (Integer.parseInt(hiddenStrings[0]) != i)
								hiddenStrings[0] = "" + i;
							else {
								hiddenStrings[0] = "-1";
								group.clearSelection();
							}
							
					}

					
				
				});
				option.add(buttons[i]);
			}
		} else {
			type = CHECKBOX;
			JCheckBox[] checkboxes = new JCheckBox[inputs.length];
			for (int i = 0; i < inputs.length; i++) {
				checkboxes[i] = new JCheckBox(fix(inputs[i])); 
			}
			for (int i = 0; i < inputs.length; i++) { 
				checkboxes[i].setFont(new Font("Serif", Font.PLAIN, 12));
				checkboxes[i].addItemListener(times);
				option.add(checkboxes[i]);
			}
		}
		option.add(checkboxIDK);
		compHeight[1] = option.getPreferredSize().getHeight();
		return this;
		
	}
	/**
	 * Sets up text fields or text box for the user to add input
	 * @param isTextField if true, user has a small text box, else a larger text box
	 */
	public Question setupOptionText(boolean isTextField) {
		
		option = new JPanel();
		option.setLayout(new BoxLayout(option, BoxLayout.Y_AXIS));
		option.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		if (isTextField) {
			type = TEXT_FIELD;
			JTextField jtf = new JTextField(10);
			option.add(jtf);
			jtf.addFocusListener(times);
		} else {
			type = TEXT_AREA;
			JTextArea ja = new JTextArea(40, 80);
			ja.addFocusListener(times);
			ja.setLineWrap(true);
	        ja.setWrapStyleWord(true);
	        JScrollPane jsp = new JScrollPane(ja);
	        jsp.setPreferredSize(new Dimension(BuildSurvey.TEXT_WIDTH,80));
	        jsp.setAlignmentX(Component.LEFT_ALIGNMENT);
			option.add(jsp);
		}
		option.add(checkboxIDK);
		compHeight[1] = option.getPreferredSize().getHeight();
		return this;
	}
	/**
	 * Sets up a spinner
	 * @param min minimum value of spinner
	 * @param max maximum value of spinner
	 */
	public Question setupOptionSlider(int min, int max) {
		type = SPINNER;
		option = new JPanel();
		option.setLayout(new BoxLayout(option, BoxLayout.PAGE_AXIS));
		option.setAlignmentX(Component.CENTER_ALIGNMENT);
		JSpinner js = new JSpinner(new SpinnerNumberModel(min, 
				min, max, 1));
		js.setAlignmentX(Component.LEFT_ALIGNMENT);
		js.addChangeListener(times);
		option.add(js);
		option.add(checkboxIDK);
		compHeight[1] = option.getPreferredSize().getHeight();
		return this;
	}
	/**
	 * Sets up button to check if dump file contains answerRegex
	 * @param answerKey the String to look for in the dump file
	 * @param answerRegex the string to look for in the dump file (if exists, we accept that the participant has manipulated the chart correctly)
	 */
	public Question setupOptionFile(String fileName, String answerKey, String answerRegex) {
			// Case where this is a button to check the dump file
			type = BUTTON;
			option = new JPanel();
			option.setLayout(new BoxLayout(option, BoxLayout.PAGE_AXIS));
			option.setAlignmentX(Component.CENTER_ALIGNMENT);
			option.add(checkboxIDK);
			compHeight[1] = option.getPreferredSize().getHeight();
			option.setPreferredSize(new Dimension(BuildSurvey.TEXT_WIDTH, (int) compHeight[1]));
			hiddenStrings = new String[3];
			hiddenStrings[0] = answerRegex;
			hiddenStrings[1] = answerKey;
			hiddenStrings[2] = fileName;
			return this;
	}
	/**
	 * Sets up Text area that will be checked to get the right answer
	 * @param answers answers (will be sorted)
	 */
	public Question setupOptionMatch(String answersString) {
		// This is a case where we'll be getting a text box for the user to answer
		type = TEXT_AREA_IS_ANSWER;
		option = new JPanel();
		option.setLayout(new BoxLayout(option, BoxLayout.Y_AXIS));
		option.setAlignmentX(Component.CENTER_ALIGNMENT);
		JTextArea ja = new JTextArea(40,40);
		ja.setLineWrap(true);
        ja.setWrapStyleWord(true);
        JScrollPane jsp = new JScrollPane(ja);
        jsp.setPreferredSize(new Dimension(BuildSurvey.TEXT_WIDTH,40));
        jsp.setAlignmentX(Component.LEFT_ALIGNMENT);
        option.add(jsp);
        
        option.add(checkboxIDK);
        compHeight[1] = option.getPreferredSize().getHeight();
        hiddenStrings = new String[1];
        hiddenStrings[0] = answersString;		
		return this;
	}
	
	// Parts
	public String toString() {
		return "[Question=" + question + ",option=" + option + "]";
	}

	public String getStringAnswer(ActionEvent e, int questNum) {
		
		StringBuilder sb = new StringBuilder(questNum + "\t");
		StringBuffer spaces = new StringBuffer ();
		for (int i = TYPES[type].length(); i < 12; i++)
			spaces.append(" ");
		sb.append(TYPES[type]  + spaces + "\t");
				
		int cLength = option.getComponents().length;
		// Get question checkboxIDK answer
		if (type != MULTI_QUESTIONS) {
			sb.append("quesIDK=" 
				+ ((JToggleButton) option.getComponent(cLength - 1)).isSelected() + "\t");
			sb.append(times + "\t");
		}
		if (type == CHECKBOX || type == RADIO) {
			// Last box is IDK (hence, cLength - 1)
			for (int c = 0; c < cLength-1; c++) {	
				Component component = option.getComponents()[c];
				sb.append(c + "=" + ((JToggleButton) component).isSelected());
				if (c < cLength - 1) sb.append("\t");
			}
		} else if (type == TEXT_FIELD || type == TEXT_AREA) {			
			char[] charArr = (type == TEXT_FIELD) 
					? ((JTextComponent) option.getComponent(0)).getText().toCharArray()
					: ((JTextComponent) ((JScrollPane) option.getComponent(0))
							.getViewport().getView())
							.getText().toCharArray();
			for (int i = 0; i < charArr.length; i++) {
				if (charArr[i] == '\t')
					sb.append("<\\t>");
				else if (charArr[i] == '\n')
					sb.append("<\\n>");
				else
					sb.append(charArr[i]);
			}
		} else if (type == SPINNER) {
			sb.append(((JSpinner) option.getComponent(0)).getValue());
			
		} else if (type == BUTTON) {
			try {
				String answerRegex = hiddenStrings[0];
				String answerKey = hiddenStrings[1];
				String fileName = hiddenStrings[2];
				ReadFileGetLastLineWith rf = new ReadFileGetLastLineWith(fileName, answerKey);
				
				if (rf.getAnswer().matches(".*" + answerRegex + ".*"))
					sb.append(true);
				else sb.append(false);
				sb.append("\t" + rf.getAnswer());
				
			} catch (Exception e1) {
				sb.append("Unable to read subfile: Have to manually look this up");
			}
		} else if (type == TEXT_AREA_IS_ANSWER) {
			// Get the user's answers
			ArrayList<String> suppliedAnswers = new ArrayList<String> (Arrays.asList(
					((JTextComponent) ((JScrollPane) option.getComponent(0))
							.getViewport().getView())
							.getText()
					.trim().split("\n")));
			ArrayList<String> trueAnswers = new ArrayList<String> (Arrays.asList(
					hiddenStrings[0]
					.trim().split("<BREAK>")));
			for (int s = 0; s < suppliedAnswers.size(); s++) {
				if (suppliedAnswers.get(s).trim().equals(""))
					suppliedAnswers.remove(s);
			}

			if (suppliedAnswers.size() != trueAnswers.size())
				sb.append(false + " " + suppliedAnswers.size() + " " + trueAnswers.size());
			else {
				Collections.sort(suppliedAnswers);
				Collections.sort(trueAnswers);
				boolean isSame = true;
				for (int s = 0; s < suppliedAnswers.size() && isSame; s++) {
					if (!suppliedAnswers.get(s).trim().equals(trueAnswers.get(s)))
						isSame = false;
				}
				sb.append(isSame);	
			}
		} else if (type == MULTI_QUESTIONS) {
			sb.append("\n");
			for (int o = 0; o < subQuestions.length; o++) {
				sb.append("\tL\t" + questNum + "." + o + ".\t");
				sb.append(subQuestions[o].getStringAnswer(e, o));
				sb.append("\n");
			}
		}
		return sb.toString();
		
	}
	

}

