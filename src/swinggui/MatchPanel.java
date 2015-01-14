package swinggui;

import game.Competition;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import schemeClasses.CompetitionScheme;
import schemeClasses.Match;
import libraryClasses.Team;
import libraryClasses.Player;
import libraryClasses.FieldPlayer;

public class MatchPanel extends JPanel {
	
	private Competition cComp;
	private Team cTeam;
	
	public MatchPanel(Competition currentCompetition, Team currentTeam) {
		cComp = currentCompetition;
		cTeam = currentTeam;
		
		initUI();
	}
	
	public final void initUI() {
		//setName("Panel");
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				
		// Fetch match information
		CompetitionScheme curScheme = cComp.getScheme();
		ArrayList<Match> roundMatches = curScheme.getRound(cComp.getRoundsPlayed()).getMatches();
		
		Match curMatch = null;
		for(Match aMatch: roundMatches) {
			if (aMatch.getTeam1().equals(cTeam.getTeamName()) || aMatch.getTeam2().equals(cTeam.getTeamName())) {
				curMatch = aMatch;
			}
		}
		
		// Left team
		JPanel leftPanel = new JPanel();
		leftPanel.setName("Panel");
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		JPanel namePanel1 = new JPanel();
		JLabel teamLabel1 = new JLabel(curMatch.getTeam1());
		teamLabel1.setMinimumSize(new Dimension(0,40));
		teamLabel1.setPreferredSize(new Dimension(teamLabel1.getPreferredSize().width, 40));
		teamLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		namePanel1.add(teamLabel1);
				
		leftPanel.add(namePanel1);
		
		
		JPanel scorePanel1 = new JPanel();
		JLabel scoreLabel1 = new JLabel("Goals: " + String.valueOf(curMatch.getScoreTeam1()));
		scoreLabel1.setMinimumSize(new Dimension(0,40));
		scoreLabel1.setPreferredSize(new Dimension(scoreLabel1.getPreferredSize().width, 40));
		scoreLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		scorePanel1.add(scoreLabel1);
		
		leftPanel.add(scorePanel1);
		
		// Goal makers and assists
		Font scorerFont = new Font("Avenir", Font.ROMAN_BASELINE, 16);
		Font assistFont = new Font("Avenir", Font.ITALIC, 14);
		
		// Each goal maker + assist in own content pane
		if (curMatch.getScoreTeam1() > 0) {
			JPanel goalPeeps = new JPanel();
			goalPeeps.setLayout(new BoxLayout(goalPeeps, BoxLayout.Y_AXIS));
			goalPeeps.setAlignmentX(Component.CENTER_ALIGNMENT);
					
			
			
			for(int i = 0; i < curMatch.getScoreTeam1(); i++) {
				JLabel scoreStats = new JLabel();
				scoreStats.setLayout(new BoxLayout(scoreStats, BoxLayout.Y_AXIS));
				scoreStats.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				// The goal scorer
				System.out.println("Scorer: " + curMatch.getGoalMakerst1().get(i).getName());
				JLabel goalScorerLabel = new JLabel(curMatch.getGoalMakerst1().get(i).getName(), SwingConstants.CENTER);

				goalScorerLabel.setMinimumSize(new Dimension(100,40));
				goalScorerLabel.setPreferredSize(new Dimension(100, 40));
				goalScorerLabel.setMaximumSize(new Dimension(550, 40));
				goalScorerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				goalScorerLabel.setFont(scorerFont);
				
				scoreStats.add(goalScorerLabel);
				
				// The assist credit
				System.out.println("Assist: " + curMatch.getAssistMakerst1().get(i).getName());
				JLabel assistLabel = new JLabel(curMatch.getAssistMakerst1().get(i).getName());
				assistLabel.setMinimumSize(new Dimension(100,40));
				assistLabel.setPreferredSize(new Dimension(100, 40));
				assistLabel.setMaximumSize(new Dimension(550, 100));
				assistLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				assistLabel.setFont(assistFont);
				
				scoreStats.setMinimumSize(new Dimension(100, 80));
				scoreStats.setPreferredSize(new Dimension(100, 80));
				scoreStats.setMaximumSize(new Dimension(550, 80));
				
				scoreStats.add(assistLabel);
				goalPeeps.add(scoreStats);
				
			}
			
			goalPeeps.setMinimumSize(new Dimension(0,20));
			goalPeeps.setPreferredSize(new Dimension(200,500));
			goalPeeps.setMaximumSize(new Dimension(550,500));
			
			leftPanel.add(goalPeeps);
		}
		
		leftPanel.setMinimumSize(new Dimension(100,500));
		leftPanel.setPreferredSize(new Dimension(450,550));
		leftPanel.setMaximumSize(new Dimension(900,612));
		
		// Cards
		if (curMatch.getYellowCardGetterst1().size() > 0 || curMatch.getRedCardGetterst1().size() > 0) {
			JPanel cards1 = new JPanel();
			cards1.setLayout(new BoxLayout(cards1, BoxLayout.Y_AXIS));
			
			JPanel cardsDiv1 = new JPanel();
			JLabel cardsLabel1 = new JLabel("Cards");
			cardsLabel1.setMinimumSize(new Dimension(0,40));
			cardsLabel1.setPreferredSize(new Dimension(cardsLabel1.getPreferredSize().width, 40));
			cardsLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
			cardsDiv1.add(cardsLabel1);
			leftPanel.add(cardsDiv1);
			
			for(int i = 0; i < curMatch.getRedCardGetterst1().size(); i++) {
				System.out.println("At least one red");
				JLabel redLabel = new JLabel(curMatch.getRedCardGetterst1().get(i).getName() + " (R)");
				redLabel.setMinimumSize(new Dimension(100,40));
				redLabel.setPreferredSize(new Dimension(redLabel.getPreferredSize().width, 40));
				redLabel.setMaximumSize(new Dimension(550, 50));
				redLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				redLabel.setFont(scorerFont);
				
				cards1.add(redLabel);
			}
			
			for(int i = 0; i < curMatch.getYellowCardGetterst1().size(); i++) {
				JLabel yellowLabel = new JLabel(curMatch.getYellowCardGetterst1().get(i).getName() + " (Y)");
				yellowLabel.setMinimumSize(new Dimension(100,40));
				yellowLabel.setPreferredSize(new Dimension(yellowLabel.getPreferredSize().width, 40));
				yellowLabel.setMaximumSize(new Dimension(550, 50));
				yellowLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				yellowLabel.setFont(scorerFont);
				
				cards1.add(yellowLabel);
			}
			
			cards1.setMinimumSize(new Dimension(200,200));
			cards1.setPreferredSize(new Dimension(200,500));
			cards1.setMaximumSize(new Dimension(550,500));
			
			leftPanel.add(cards1);
		}
		
		// Injuries
		if (curMatch.getInjuredPlayerst1().size() > 0) {
			
			JPanel injuryDiv1 = new JPanel();
			JLabel injuryLabel1 = new JLabel("Injuries");
			injuryLabel1.setMinimumSize(new Dimension(0,40));
			injuryLabel1.setPreferredSize(new Dimension(injuryLabel1.getPreferredSize().width, 40));
			injuryLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
			injuryDiv1.add(injuryLabel1);
			
			leftPanel.add(injuryDiv1);
			
			JPanel injury1 = new JPanel();
			injury1.setLayout(new BoxLayout(injury1, BoxLayout.Y_AXIS));
			
			for(int i = 0; i < curMatch.getInjuredPlayerst1().size(); i++) {
				Player injuredP = curMatch.getInjuredPlayerst1().get(i);
				int duration = curMatch.getInjuriesLengthst1()[i];
				String injuryWeek = duration == 1 ? " week)" : " weeks)";
				JLabel injuredLabel = new JLabel(injuredP.getName() + " (" + duration + injuryWeek);
				injuredLabel.setMinimumSize(new Dimension(100,40));
				injuredLabel.setPreferredSize(new Dimension(injuredLabel.getPreferredSize().width, 40));
				injuredLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
				injuredLabel.setFont(scorerFont);
				
				injury1.add(injuredLabel);
			}
			
			leftPanel.add(injury1);
		}
		
		add(leftPanel);
		
		
		// Right team
		JPanel rightPanel = new JPanel();
		rightPanel.setName("Panel");
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		JPanel namePanel2 = new JPanel();
		JLabel teamLabel2 = new JLabel(curMatch.getTeam2());
		teamLabel2.setMinimumSize(new Dimension(0,40));
		teamLabel2.setPreferredSize(new Dimension(teamLabel2.getPreferredSize().width, 40));
		teamLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		namePanel2.add(teamLabel2);
		
		rightPanel.add(namePanel2);
		
		JPanel scorePanel2 = new JPanel();
		JLabel scoreLabel2 = new JLabel("Goals: " + String.valueOf(curMatch.getScoreTeam2()));
		scoreLabel2.setMinimumSize(new Dimension(0,40));
		scoreLabel2.setPreferredSize(new Dimension(scoreLabel2.getPreferredSize().width, 40));
		scoreLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		scorePanel2.add(scoreLabel2);
		
		rightPanel.add(scorePanel2);
		
		// Each goal maker + assist in own content pane
		if (curMatch.getScoreTeam2() > 0) {
			JPanel goalPeeps2 = new JPanel();
			goalPeeps2.setLayout(new BoxLayout(goalPeeps2, BoxLayout.Y_AXIS));
			goalPeeps2.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			for(int i = 0; i < curMatch.getScoreTeam2(); i++) {
				JPanel scoreStats = new JPanel();
				scoreStats.setLayout(new BoxLayout(scoreStats, BoxLayout.Y_AXIS));
				scoreStats.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				// The goal scorer
				JLabel goalScorerLabel = new JLabel(curMatch.getGoalMakerst2().get(i).getName());
				goalScorerLabel.setMinimumSize(new Dimension(100,40));
				goalScorerLabel.setPreferredSize(new Dimension(100, 40));
				goalScorerLabel.setMaximumSize(new Dimension(550, 40));
				goalScorerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				goalScorerLabel.setFont(scorerFont);
				
				scoreStats.add(goalScorerLabel);
							
				// The assist credit
				JLabel assistLabel = new JLabel(curMatch.getAssistMakerst2().get(i).getName());
				assistLabel.setMinimumSize(new Dimension(100,40));
				assistLabel.setPreferredSize(new Dimension(100, 40));
				assistLabel.setMaximumSize(new Dimension(550, 40));
				assistLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				assistLabel.setFont(assistFont);
				
				scoreStats.setMinimumSize(new Dimension(100, 80));
				scoreStats.setPreferredSize(new Dimension(100, 80));
				scoreStats.setMaximumSize(new Dimension(550, 80));
				
				scoreStats.add(assistLabel);
				goalPeeps2.add(scoreStats);
			}
			
			rightPanel.add(goalPeeps2);
		}
		
		rightPanel.setMinimumSize(new Dimension(100,500));
		rightPanel.setPreferredSize(new Dimension(450,550));
		rightPanel.setMaximumSize(new Dimension(900,612));
		
		// Cards
		if (curMatch.getYellowCardGetterst2().size() > 0 || curMatch.getRedCardGetterst2().size() > 0) {
			JPanel cards2 = new JPanel();
			cards2.setLayout(new BoxLayout(cards2, BoxLayout.Y_AXIS));
			
			JPanel cardsDiv2 = new JPanel();
			JLabel cardsLabel2 = new JLabel("Cards");
			cardsLabel2.setMinimumSize(new Dimension(0,40));
			cardsLabel2.setPreferredSize(new Dimension(cardsLabel2.getPreferredSize().width, 40));
			cardsLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
			cardsDiv2.add(cardsLabel2);
			rightPanel.add(cardsDiv2);
			
			for(int i = 0; i < curMatch.getRedCardGetterst2().size(); i++) {
				JLabel redLabel = new JLabel(curMatch.getRedCardGetterst2().get(i).getName() + " (R)");
				redLabel.setMinimumSize(new Dimension(100,40));
				redLabel.setPreferredSize(new Dimension(redLabel.getPreferredSize().width, 40));
				redLabel.setMaximumSize(new Dimension(550, 50));
				redLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				redLabel.setFont(scorerFont);
				
				cards2.add(redLabel);
			}
			
			for(int i = 0; i < curMatch.getYellowCardGetterst2().size(); i++) {
				JLabel yellowLabel = new JLabel(curMatch.getYellowCardGetterst2().get(i).getName() + " (Y)");
				yellowLabel.setMinimumSize(new Dimension(100,40));
				yellowLabel.setPreferredSize(new Dimension(yellowLabel.getPreferredSize().width, 40));
				yellowLabel.setMaximumSize(new Dimension(550, 50));
				yellowLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				yellowLabel.setFont(scorerFont);
				
				cards2.add(yellowLabel);
			}
			
			cards2.setMinimumSize(new Dimension(200,200));
			cards2.setPreferredSize(new Dimension(200,500));
			cards2.setMaximumSize(new Dimension(550,500));
			
			rightPanel.add(cards2);
		}
		
		// Injuries
		if (curMatch.getInjuredPlayerst2().size() > 0) {
			JPanel injury2 = new JPanel();
			injury2.setLayout(new BoxLayout(injury2, BoxLayout.Y_AXIS));
			
			JPanel injuryDiv2 = new JPanel();
			JLabel injuryLabel2 = new JLabel("Injuries");
			injuryLabel2.setMinimumSize(new Dimension(0,40));
			injuryLabel2.setPreferredSize(new Dimension(injuryLabel2.getPreferredSize().width, 40));
			injuryLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
			injuryDiv2.add(injuryLabel2);
			
			rightPanel.add(injuryDiv2);
			
			for(int i = 0; i < curMatch.getInjuredPlayerst2().size(); i++) {
				Player injuredP = curMatch.getInjuredPlayerst2().get(i);
				int duration = curMatch.getInjuriesLengthst2()[i];
				String injuryWeek = duration == 1 ? " week)" : " weeks)";
				JLabel injuredLabel = new JLabel(injuredP.getName() + " (" + duration + injuryWeek);
				injuredLabel.setMinimumSize(new Dimension(100,40));
				injuredLabel.setPreferredSize(new Dimension(injuredLabel.getPreferredSize().width, 40));
				injuredLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				injuredLabel.setFont(scorerFont);
				
				injury2.add(injuredLabel);
			}
			
			rightPanel.add(injury2);
		}
		
		add(rightPanel);
	}
}
