package spaceshooter;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import enginex.Button;
import enginex.State;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class PauseState extends State {
	Spaceshooter			game;
	boolean						initialized	= false;
	
	public Controller	joystick;
	
	ArrayList<Button>	buttons			= new ArrayList<>();
	Button						btnResume;
	Button						btnQuitToMenu;
	
	ScrollingBG				spaceBG			= new ScrollingBG("res/spaceshooter/spacebg.png", 0.0f, 0, 0, 800, 600);
	
	public PauseState(Spaceshooter game) {
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
		for(Controller c:ControllerEnvironment.getDefaultEnvironment().getControllers()) {
			if(c.getType() == Controller.Type.STICK)
				joystick = c;
		}
	}
	
	public void create() {
		initControllers();
		createButtons();
	}
	
	public void createButtons() {
		btnResume = new Button(game, "Resume Game", game.width / 2 - 100, 200, 200, 75, "res/spaceshooter/btnResume.png", "res/spaceshooter/btnResume.png", "res/replicants/sfx/buttonHover.ogg");
		buttons.add(btnResume);
		
		btnQuitToMenu = new Button(game, "Quit To Menu", game.width / 2 - 100, 300, 200, 75, "res/spaceshooter/btnQuitToMenu.png", "res/spaceshooter/btnQuitToMenu.png", "res/replicants/sfx/buttonHover.ogg");
		buttons.add(btnQuitToMenu);
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
		
		// Buttons
		for(Button b:buttons)
			b.render(g);
	}
	
	public void keyPressed(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.stateMachine.setState(game.PLAY);
		
		if(e.getKeyCode() == KeyEvent.VK_Q)
			game.stateMachine.setState(game.MENU);
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			// Resume Button Clicked...
			if(btnResume.hover)
				game.stateMachine.setState(game.PLAY);
			
			// Quit To Menu Button Clicked...
			if(btnQuitToMenu.hover)
				game.stateMachine.setState(game.MENU);
		}
	}
	
	public void joyStickPoll() {
		// Update Joystick Input Data
		if(joystick != null) {
			// Poll Joystick Input Data
			joystick.poll();
			
			// Get Components
			// Component[] components = joystick.getComponents();
		}
	}
}
