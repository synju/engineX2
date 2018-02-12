package spaceshooter;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import enginex.State;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class PlayState extends State {
	Spaceshooter	game;
	boolean			initialized	= false;

	public Controller joystick;

	Player player;

	ScrollingBG spaceBG = new ScrollingBG("res/spaceshooter/spacebg.png", 0.2f, 0, 0, 800, 600);

	public PlayState(Spaceshooter game) {
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
		player = new Player(game, game.width / 2 - Player.WIDTH / 2, game.height - Player.HEIGHT * 2);
	}

	public void update() {
		initialize();
		joyStickPoll();

		spaceBG.update();
		player.update();
	}

	public void render(Graphics2D g) {
		// Smooth Images
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		spaceBG.render(g);
		player.render(g);
	}

	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.stateMachine.setState(game.PAUSE);

		player.keyReleased(e);
	}

	public void joyStickPoll() {
		// Update Joystick Input Data
		if(joystick != null) {
			// Poll Joystick for updates
			joystick.poll();

			// Update Player
			player.joystickPoll(joystick.getComponents());
		}
	}
}
