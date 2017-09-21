package flowershop;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import enginex.Button;
import enginex.State;

public class MenuState extends State {
	// Game
	Game							game;
	
	// Logo and Sky background
	Image							logoImage						= new ImageIcon("res/flowershop/logo.png").getImage();
	Image							skyBackgroundImage	= new ImageIcon("res/flowershop/sky.png").getImage();
	
	// Buttons
	ArrayList<Button>	buttons							= new ArrayList<>();
	Button						playButton;
	Button						profileButton;
	Button						quitButton;
	
	protected MenuState(Game game) {
		super(game);
		this.game = game;
	}
	
	public void initialize() {
		if(initialized)
			return;
		
		create();
		
		initialized = true;
	}
	
	public void create() {
		game.profileManager = new ProfileManager(game);
		
		createButtons();
	}
	
	public void createButtons() {
		playButton = new Button(game, game.width / 2 - 211 / 2, 250, 211, 93, "res/flowershop/play_button.png", "res/flowershop/play_button.png");
		buttons.add(playButton);
		
		profileButton = new Button(game, 10, 10, 152, 67, "res/flowershop/profile.png", "res/flowershop/profile.png");
		buttons.add(profileButton);
		
		quitButton = new Button(game, 10, game.height - 50, 90, 42, "res/flowershop/quit.png", "res/flowershop/quit.png");
		buttons.add(quitButton);
	}
	
	public void update() {
		initialize();
		
		// Buttons
		for(Button b:buttons)
			b.update();
	}
	
	public void render(Graphics2D g) {
		// Smooth Images
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		
		// Background
		g.drawImage(skyBackgroundImage, 0, 0, null);
		
		// Logo
		g.drawImage(logoImage, 80, 165, null);
		
		// Buttons
		for(Button b:buttons)
			b.render(g);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			game.exit();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			// Play Button Clicked...
			if(playButton.hover) {
				game.stateMachine.setState(game.PLAY);
				System.out.println("Play!!!");
			}
			
			// Load Button Clicked...
			if(profileButton.hover) {
				
				System.out.println("Profiles!!!");
			}
			
			// Quit Button Clicked...
			if(quitButton.hover) {
				System.out.println("Quit!!!");
				game.exit();
			}
		}
	}
}
