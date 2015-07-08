package com.jsscperrie.survey;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class BuildSurvey {
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final static int WIDTH = (int) screenSize.getWidth()/2;//600;
	public final static int HEIGHT = (int) screenSize.getHeight() - 100;//800;
	public final static int PADDING = 10;
	public final static int SCROLL_WIDTH = 10;
	public final static int SPACE = 100;
	public final static int TEXT_WIDTH = WIDTH - PADDING * 2;
	public final static int CONTENT_WIDTH = WIDTH + SCROLL_WIDTH + PADDING;
	
	private static int TIME_LIMIT = 1000;
	
	// Instance variables
	private Question[] questions;
	private Answer[] answers;
	private JPanel panelContent;
	private JScrollPane paneScroll;
	private JPanel panelBreather;
	private boolean showBreather = true;
	
	private JFrame frame;
	private SpringLayout layout;
	
	private JButton buttonNext;
	private JLabel labelIDK;
	private JPanel panelBottom;
	private Timer timer;
	// http://stackoverflow.com/questions/20706952/using-java-timer-in-jframe
	
	private JLabel labelHeading;
	private JPanel panelTop;

	private int questionCount;
	
	public BuildSurvey(final Question[] questions, boolean hasBreather, String title, int timeLimit) {
		
		// Questions / Answer
		this.TIME_LIMIT = timeLimit;
		this.questions = questions;
		answers = new Answer[questions.length];
		questionCount = 0;
		timer = new Timer(TIME_LIMIT, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				labelIDK.setVisible(true);
				timer.stop();
				
			}
		});
		
		// Set up bottom part
		buttonNext = new JButton();
		buttonNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (showBreather && panelBreather != null) {
					showBreather = false;
				} else {
					
					showBreather = true;
					// Save answer
					answers[questionCount].setAnswer(
							questions[questionCount].getStringAnswer(e, 
									questionCount));
					questionCount++;
				}		
				// Set next question
				panelContent.removeAll();
				loadComponent();
				frame.pack();				
			}
				
				
				
		});
		int printTime = TIME_LIMIT/1000;
		String printString = "seconds";
		if (TIME_LIMIT > 60000) {
			printTime = TIME_LIMIT/1000/60;
			printString = "minutes";
		}
		labelIDK = new JLabel(printTime + " " + printString);
		labelIDK.setVisible(false);
		labelIDK.setForeground(Color.RED);
		panelBottom = new JPanel(new FlowLayout());
		panelBottom.add(buttonNext);
		panelBottom.add(labelIDK);
		panelBottom.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, 
				PADDING, PADDING));
		
		
		// Set up Heading
		labelHeading = new JLabel("Question #" + (this.questionCount + 1) 
				+ " of " + questions.length, SwingConstants.LEFT);
		panelTop = new JPanel(new FlowLayout());
		panelTop.add(labelHeading);
		panelTop.setPreferredSize(new Dimension(CONTENT_WIDTH, 20));
		
		// Section for content
		panelContent = new JPanel();
		panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.PAGE_AXIS));
		panelContent.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, 
				PADDING, PADDING));
		
						
		paneScroll = new JScrollPane(panelContent);
		paneScroll.setPreferredSize(new Dimension(CONTENT_WIDTH, HEIGHT - SPACE));
		
		// Set up breather
		if (hasBreather) {
			panelBreather = new JPanel(new FlowLayout());
			panelBreather.add(new JLabel("Are you ready for the next question?"));
			panelBreather.setAlignmentY(Component.LEFT_ALIGNMENT);
		} else panelBreather = null;
		
		
		// Frame
        frame = new JFrame(title);
        layout = new SpringLayout();
        frame.setLayout(layout);
     //   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        /*Some piece of code*/
    	frame.addWindowListener(new java.awt.event.WindowAdapter() {
    	    @Override
    	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
    	        if (JOptionPane.showConfirmDialog(frame, 
    	            "Are you sure to close this window?", "Really Closing?", 
    	            JOptionPane.YES_NO_OPTION,
    	            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
    	        	
    	        	System.out.println("YES YES YES");
    	        	buttonNext.doClick();
    	        	for (; questionCount < questions.length; questionCount++) {
    	        		answers[questionCount] = new Answer();
    	        		answers[questionCount].setAnswer("CANCELED BEFORE SAVE");
    	        	}
    	        	saveResults();
    	            System.exit(0);
    	        } else {
    	        	System.out.println("DON't CLOSE");
    	        	frame.setVisible(true);
    	        }
    	    }
    	});
        
        // Load first component
        loadComponent();
        
        //Display the window.
        frame.setMinimumSize(new Dimension(CONTENT_WIDTH + PADDING * 2, HEIGHT));
        frame.pack();
        frame.setVisible(true);
        
		frame.getContentPane().add(panelTop);
		frame.getContentPane().add(paneScroll);//panelContent);
		frame.getContentPane().add(panelBottom);
				
		layout.putConstraint(SpringLayout.NORTH, panelTop, 
				5, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, panelTop, 
				PADDING, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, panelBottom,
				5, SpringLayout.SOUTH, frame.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, paneScroll,
        		5, SpringLayout.SOUTH, panelTop);
        layout.putConstraint(SpringLayout.WEST, paneScroll,
        		PADDING, SpringLayout.WEST, frame.getContentPane());
		
	}
	
	public void loadComponent() {
		
		paneScroll.getVerticalScrollBar().setValue(0);
//		frame.setLocation(0, 0);
		
		if (questionCount == questions.length - 1)
			buttonNext.setText("Finish");
		if (questionCount == questions.length) {
			saveResults();
			System.exit(0);
		}
		labelIDK.setVisible(false);
		labelHeading.setText("Part " + (this.questionCount + 1) + " of " + questions.length);
		panelContent.removeAll();
		
		// Show the breather panel
		if (showBreather && panelBreather != null) {
			
			panelContent.add(panelBreather);
			buttonNext.setText("I'm ready!");
			
		// Show the question panel
		} else {
			panelContent.add(questions[questionCount].getQuestion());
			panelContent.add(questions[questionCount].getOption());
			panelContent.setPreferredSize(new Dimension(WIDTH,
					(int) questions[questionCount].getHeight() + PADDING * 2));
			panelContent.add(Box.createRigidArea(new Dimension(WIDTH, 
					(int) (HEIGHT - SPACE - questions[questionCount].getHeight() - PADDING * 2))));
			
			if (questionCount == questions.length - 1) buttonNext.setText("Finish");
			else buttonNext.setText("Next");
			timer.restart();
			answers[questionCount] = new Answer();
		}
		System.out.println(frame.getContentPane());
	}
	
	public void saveResults() {
		System.out.println("--------------------------------------------");
		for (int q = 0; q < answers.length; q++) {
			System.out.println(q 
					+ ".\t" + answers[q]);
		}
		
	}
	

	
	
}
