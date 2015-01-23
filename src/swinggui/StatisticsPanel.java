package swinggui;

import game.Competition;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import libraryClasses.Team;

@SuppressWarnings("serial")
public class StatisticsPanel extends JPanel {
	
	private Competition currentCompetition;
	private Team currentTeam;
	
	public Dimension minSize = new Dimension(20,20);
	public Dimension prefSize = new Dimension(40,20);
	
	public StatisticsPanel(Competition curComp, Team curTeam) {
		currentCompetition = curComp;
		currentTeam = curTeam;
		
		initUI();
	}
	
	public final void initUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(new Box.Filler(minSize, prefSize, null));
		
		// add the overview panels
		add(new StandingsPanel(currentCompetition, currentTeam, true));
		add(new AgendaPanel(currentCompetition));
		
		add(new Box.Filler(minSize, prefSize, null));
	}
}
