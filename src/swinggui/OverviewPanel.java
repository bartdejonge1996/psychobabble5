/**
 * GUI class that displays an overview of the current competition's game state
 */
package swinggui;

import game.Competition;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import libraryClasses.Team;

@SuppressWarnings("serial")
public class OverviewPanel extends JPanel {
	
	private Competition currentComp;
	private Team currentTeam;
	
	public Dimension minSize = new Dimension(20,20);
	public Dimension prefSize = new Dimension(40,20);

	/**
	 * Create and initialize an OverviewPanel
	 * @param cComp Current Competition
	 */
	public OverviewPanel(Competition cComp, Team cTeam) {

		currentComp = cComp;
		currentTeam = cTeam;
		
		initUI();
	}
	
	/**
	 * Initialize GUI elements contained in the OverviewPanel
	 */
	public final void initUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(new Box.Filler(minSize, prefSize, null));
		
		// add the overview panels
		add(new StandingsPanel(currentComp, currentTeam, false));
		add(new RecentMatchesPanel(currentComp));
		
		add(new Box.Filler(minSize, prefSize, null));
	}
}
