package spaceshooter;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import enginex.Button;
import enginex.State;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class MenuState extends State {
	Spaceshooter			game;
	boolean						initialized			= false;
	
	public Controller	joystick;
	
	ArrayList<Button>	buttons					= new ArrayList<>();
	Button						btnPlay;
	Button						btnQuit;
	
	ScrollingBG				spaceBG					= new ScrollingBG("res/spaceshooter/spacebg.png", 0.0f, 0, 0, 800, 600);
	Image							crosshairImage	= new ImageIcon("res/crosshair.png").getImage();
	Image							logoImage				= new ImageIcon("res/spaceshooter/logo.png").getImage();
	
	public MenuState(Spaceshooter game) {
		super(game);
		this.game = game;
	}
	
	public void initialize() {
		if(initialized)
			return;
		
		create();
		
		initialized = true;
	}
	
	void initControllers() {
		for(Controller c:ControllerEnvironment.getDefaultEnvironment().getControllers())
			if(c.getType() == Controller.Type.STICK)
				joystick = c;
	}
	
	public void create() {
		game.hideDefaultCursor();
		initControllers();
		createButtons();
	}
	
	public void createButtons() {
		btnPlay = new Button(game, "Play Game", game.width / 2 - 100, 200, 200, 75, "res/spaceshooter/btnPlay.png", "res/spaceshooter/btnPlay.png", "res/replicants/sfx/buttonHover.ogg");
		buttons.add(btnPlay);
		
		btnQuit = new Button(game, "Quit Game", game.width / 2 - 100, 300, 200, 75, "res/spaceshooter/btnQuit.png", "res/spaceshooter/btnQuit.png", "res/replicants/sfx/buttonHover.ogg");
		buttons.add(btnQuit);
	}
	
	public void update() {
		initialize();
		joyStickPoll();
		
		for(Button b:buttons)
			b.update();
	}
	
	public void render(Graphics2D g) {
		// Smooth Images
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		
		spaceBG.render(g);
		
		g.drawImage(logoImage, 10, 100, null);
		
		// Buttons
		for(Button b:buttons)
			b.render(g);
		
		// Cursor
		renderCrosshair(g);
	}
	
	void renderCrosshair(Graphics2D g) {
		try {
			Point p = game.getMousePosition();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			g.drawImage(crosshairImage, (int)p.getX() - 10, (int)p.getY() - 10, null);
		}
		catch(Exception e) {}
	}
	
	public void keyPressed(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			// Play Button Clicked...
			if(btnPlay.hover) {
				game.playState = new PlayState(game);
				game.stateMachine.states.set(game.PLAY, game.playState);
				game.stateMachine.states.get(game.PLAY).init();
				game.stateMachine.setState(game.PLAY);
			}
			
			// Quit Button Clicked...
			if(btnQuit.hover)
				game.exit();
		}
	}
	
	public void joyStickPoll() {
		// Update Joystick Input Data
		if(joystick != null) {
			// Poll Joystick for updates
			joystick.poll();
			
			// Get Components
			// Component[] components = joystick.getComponents();
		}
	}
}
