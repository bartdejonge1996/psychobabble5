/**
 * GUI Class that contains the game's frame and manages the game's progression
 */
package swinggui;

import game.*;
import gameLogic.TransferLogic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.synth.SynthLookAndFeel;

import libraryClasses.Team;
import xmlIO.XMLParser;

@SuppressWarnings("serial")
public class Frame_Main extends JFrame implements ActionListener{
	
	private JPanel curPanel;
	private BottomBar bottomBar;
	private Header headerBar;
	private String current;
	private Competition curComp;
	private Team curTeam;
	private int roundNum;
	private String playerName;
	private GameList games;
	private Game currentGame;
	private boolean shouldSave;
	
	public Dimension minSize = new Dimension(20,20);
	public Dimension prefSize = new Dimension(40,20);
	
	/**Function to create ImageIcons, just in case I have trouble finding them again
	 * @param path			- ImageIcon file location
	 * @return				- new ImageIcon object
	 */
	public ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, null);
		}
		else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	/**
	 * Create and initialize Frame_Main JFrame
	 */
	public Frame_Main() {
		current = "nada";
		
		curComp = XMLParser.readCompetition("files/competitionDatabase_v5.xml", "files/competition-scheme.xml");
		roundNum = 0;
		
		// Currently only supports one season
		games = XMLParser.readGameList("files/saves_v7.xml");
		
		shouldSave = false;
		
		initUI();
	}
	
	/**
	 * Initialize the first GUI elements
	 */
	public final void initUI(){
		//set Look And Feel
		SynthLookAndFeel synth = new SynthLookAndFeel();
		try {
			synth.load(Frame_Main.class.getResourceAsStream("lookandfeel.xml"), Frame_Main.class);
		} catch (ParseException e) {
			System.out.println("lookandfeel.xml not found");
		}
		try {
			UIManager.setLookAndFeel(synth);
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		ImageIcon bgImage = createImageIcon("images/background.png");
		BackgroundPanel bg = new BackgroundPanel(bgImage.getImage(), BackgroundPanel.SCALED, 0.5f,0.5f);
		bg.setLayout(new BorderLayout());
		setContentPane(bg);
		
		//initialize some stuff
		setTitle("Football Manager 2015");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(1024, 768));
		setSize(1280, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				exitProcedure();
			}
		});

		boolean showLoadedGames = games.getGames().size() > 0;
		
		SplashPanel splashChoice = new SplashPanel(this, showLoadedGames);
		curPanel = splashChoice;
		add(curPanel, BorderLayout.CENTER);
			
	}
	
	/**
	 * Exit procedure for the game, which saves current game before close if the current game exists
	 */
	public void exitProcedure() {
		// Autosave on game exit
		if (shouldSave) {
			currentGame.save();
			XMLParser.writeGameList("files/saves_v7.xml", games);
		}
		dispose();
		System.exit(0);
	}
	
	/**
	 * Main runtime method for Football Manager
	 * @param args
	 */
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
			@Override 
			public void run() {
				Frame_Main Mainwindow = new Frame_Main();
				Mainwindow.setVisible(true);
			}
		});
	}

	/**
	 * ActionListener method override: switches between game screens based on button clicks
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton possibleMenuB = (JButton) e.getSource();
			
			switch (possibleMenuB.getText()) {
				case "Load Game":

					loadLoadScreen();
					
					break;
				case "New Game":
					
					loadNameChoiceScreen();
					
					break;
				case "Pick your team":
					
					// grab name from NamePanel, then on to choosing team
					playerName = (String)possibleMenuB.getClientProperty("playerName");
					
					loadNewGameScreen();
					
					break;
				case "Play as this team":					
					String teamName = (String)possibleMenuB.getClientProperty("teamName");
					
					shouldSave = true;
					loadMainScreenNewGame(teamName);
					
					break;
				case "Load This Game":
					
					int gameIndex = (int)possibleMenuB.getClientProperty("gameIndex");
					
					shouldSave = true;
					loadMainScreenLoadedGame(gameIndex);
					
					break;
				case "New Season":
					curComp.newSeason();
					roundNum = 0;
					
					currentGame.save();
					XMLParser.writeGameList("files/saves_v7.xml", games);
					
					loadMainScreenNewGame(curTeam.getTeamName());
					
					break;
				case "Quit":
					exitProcedure();
					
					break;
				case "overview ":					
					loadOverView();
					
					break;
				case "statistics":
					loadStatisticsView();
					
					break;
				case "":
					// Should this be encapsulated by a game logic class method?
					// For the demo this runs through the entire season (round by round)
					
					if (roundNum < 38) {
						if(curComp.playRound()) {
							TransferLogic.AutoTransfer(curTeam, curComp.getLibrary());
							roundNum++;
							loadPlayView();
						} else {
							bottomBar.showString("Your team is not currently eligible for playing", new Color(255,0,0));
						}
						
					} else {
						// trigger an event signalling the start of the
						// next season?
						loadSeasonOverview();
					}		
					
					break;
				case "positions ":
					loadPositionsView();
					
					break;
				case "transfers":
					loadTransfersView();
					
					break;
				default:
					System.out.println("This might not be a menu bar item!");
					break;
			}
		}
		
	}
	
	/**
	 * Initialize and display load game screen
	 */
	public void loadLoadScreen() {
		LoadPanel loadYaGame = new LoadPanel(games, this);
		
		remove(curPanel);
		
		curPanel = loadYaGame;
		add(curPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	/**
	 * Initialize and display name choice screen for a new game
	 */
	public void loadNameChoiceScreen() {
		NamePanel nameSelection = new NamePanel(this);
		
		remove(curPanel);
		
		curPanel = nameSelection;
		add(curPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	/**
	 * Initialize and display team choice screen for a new game
	 */
	public void loadNewGameScreen() {
		
		// grab name from current 
		
		TeamChoicePanel teamChoose = new TeamChoicePanel(curComp, this);
		
		remove(curPanel);
		
		curPanel = teamChoose;
		add(curPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	/**
	 * Load the overview screen and game from a save file
	 * @param gameIndex Index of loaded game in current GameList object
	 */
	public void loadMainScreenLoadedGame(int gameIndex) {
		// Create the new game
		currentGame = games.get(gameIndex);
		
		curComp = currentGame.getCompetition();
		curTeam = currentGame.getTeam();
		
		currentGame.save();
		XMLParser.writeGameList("files/saves_v7.xml", games);
		roundNum = curComp.getRoundsPlayed();
		playerName = currentGame.getName();
		
		remove(curPanel);
		// Start playing!
		// Get screen sizes (for fullscreen)
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		bottomBar = new BottomBar(curComp, curTeam);
		
		//Header panel
		headerBar = new Header(this);
		headerBar.setLayout(new BoxLayout(headerBar, BoxLayout.X_AXIS));
		add(headerBar);
		
		//Center panel begins here
		OverviewPanel overviewPanel = new OverviewPanel(curComp, curTeam);
		
		// set current screen string
		current = "overview";
		
		//Center panel ends here
		curPanel = overviewPanel;
		add(curPanel, BorderLayout.CENTER);
		
		//Bottom bar here
		add(bottomBar);
		bottomBar.showStats();
		
		revalidate();
		repaint();
	}
	
	/**
	 * Create a new game and display overview screen of said game
	 * @param chosenTeam Chosen team name for new game
	 */
	public void loadMainScreenNewGame(String chosenTeam) {
		curTeam = curComp.getLibrary().getTeamForName(chosenTeam);
		
		// Create the new game
		currentGame = games.newgame(playerName, chosenTeam);
		currentGame.save();
		XMLParser.writeGameList("files/saves_v7.xml", games);
		
		remove(curPanel);
		// Start playing!
		// Get screen sizes (for fullscreen)
		Toolkit tk = Toolkit.getDefaultToolkit();

		bottomBar = new BottomBar(curComp, curTeam);
		
		//Header panel
		headerBar = new Header(this);
		headerBar.setLayout(new BoxLayout(headerBar, BoxLayout.X_AXIS));
		add(headerBar);
		
		//Center panel begins here
		OverviewPanel overviewPanel = new OverviewPanel(curComp, curTeam);
		
		// set current screen string
		current = "overview";
		
		//Center panel ends here
		curPanel = overviewPanel;
		add(curPanel, BorderLayout.CENTER);
		
		//Bottom bar here
		add(bottomBar);
		bottomBar.showStats();
		
		revalidate();
		repaint();
	}
	
	/**
	 * Initialize and display the overview screen if it isn't already the current screen
	 */
	public void loadOverView() {
		if (!current.equals("overview")) {
			current = "overview";

			remove(bottomBar);
			bottomBar = new BottomBar(curComp, curTeam);
			
			currentGame.save();
			XMLParser.writeGameList("files/saves_v7.xml", games);
			
			// Initialize new JPanel and remove current pane
			OverviewPanel replOverview = new OverviewPanel(curComp, curTeam);
			remove(curPanel); 
			
			// Refresh the view
			add(replOverview, BorderLayout.CENTER, 1);
			curPanel = replOverview;
			
			//Bottom bar here
			add(bottomBar);
			bottomBar.showStats();
			
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Initialize and display the statistics screen if it isn't already the current screen
	 */
	public void loadStatisticsView() {
		// Switch to statistics panel if not current
		if (!current.equals("statistics")) {
			current = "statistics";

			remove(bottomBar);
			bottomBar = new BottomBar(curComp, curTeam);
			
			currentGame.save();
			XMLParser.writeGameList("files/saves_v7.xml", games);
			
			// Initialize new JPanel and remove current pane
			StatisticsPanel replStatview = new StatisticsPanel(curComp, curTeam);
			remove(curPanel); 
			
			// Refresh the view
			add(replStatview, BorderLayout.CENTER, 1);
			curPanel = replStatview;
			
			//Bottom bar here
			add(bottomBar);
			bottomBar.showStats();
			
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Initialize and display the positions screen if it isn't already the current screen
	 */
	public void loadPositionsView() {
		// Switch to positions panel if not current
		if (!current.equals("positions")) {
			current = "positions";
			remove(bottomBar);
			bottomBar = new BottomBar(curComp, curTeam);
			currentGame.save();
			XMLParser.writeGameList("files/saves_v7.xml", games);
			
			// Initialize new JPanel and remove current pane
			PositionsPanel replPositsview = new PositionsPanel(curTeam);
			remove(curPanel); 
			
			// Refresh the view
			add(replPositsview, BorderLayout.CENTER, 1);
			curPanel = replPositsview;
			
			//Bottom bar here
			add(bottomBar);
			bottomBar.showStats();
			
			
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Initialize and display the transfers screen if it isn't already the current screen
	 */
	public void loadTransfersView() {
		// Switch to transfers panel if not current
		if (!current.equals("transfers")) {
			current = "transfers";
			remove(bottomBar);
			bottomBar = new BottomBar(curComp, curTeam);
			currentGame.save();
			XMLParser.writeGameList("files/saves_v7.xml", games);
			
			// Initialize new JPanel and remove current pane
			TransfersPanel replTransfview = new TransfersPanel(curTeam, curComp, bottomBar);
			remove(curPanel); 
			
			// Refresh the view
			add(replTransfview, BorderLayout.CENTER, 1);
			curPanel = replTransfview;
			
			add(bottomBar);
			bottomBar.showStats();
			
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Initialize and display the match overview screen
	 */
	public void loadPlayView() {
		// Then display statistics page showcasing the results of the season
		current = "match";
		
		currentGame.save();
		XMLParser.writeGameList("files/saves_v7.xml", games);
		
		// Initialize new JPanel and remove current pane
		MatchPanel replPlayView = new MatchPanel(curComp, curTeam);
		remove(curPanel); remove(bottomBar);
		
		// Refresh the view
		add(replPlayView, BorderLayout.CENTER, 1);
		curPanel = replPlayView;
		

		//Bottom bar here
		bottomBar = new BottomBar(curComp, curTeam);
		add(bottomBar, BorderLayout.SOUTH);
		bottomBar.showStats();
		
		revalidate();
		repaint();
	}
	
	/**
	 * Initialize and display the season overview screen
	 */
	public void loadSeasonOverview() {
		current = "season";
		
		SeasonPanel seasonSummary = new SeasonPanel(curComp, curTeam, this, playerName);
		remove(curPanel);
		remove(bottomBar);
		remove(headerBar);
		
		add(seasonSummary, BorderLayout.CENTER);
		curPanel = seasonSummary;
		
		revalidate();
		repaint();		
	}

}
