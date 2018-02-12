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

public class MenuState extends State {
	Spaceshooter	game;
	boolean			initialized	= false;

	public Controller joystick;

	ArrayList<Button>	buttons	= new ArrayList<>();
	Button				btnPlay;
	Button				btnQuit;
	
	ScrollingBG spaceBG = new ScrollingBG("res/spaceshooter/spacebg.png", 0.0f, 0, 0, 800, 600);

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

		// Buttons
		for(Button b:buttons)
			b.render(g);
	}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			// Play Button Clicked...
			if(btnPlay.hover) {
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
			//			Component[] components = joystick.getComponents();
		}
	}
}
