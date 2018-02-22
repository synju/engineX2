package spaceshooter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import enginex.State;
import enginex.Util;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class PlayState extends State {
	Spaceshooter			game;
	boolean						initialized			= false;
	
	public Controller	joystick;
	boolean						joystickEnabled	= true;
	
	Player						player;
	WaveHandler				waveHandler;
	Resources					res							= new Resources();
	
	ScrollingBG				spaceBG					= new ScrollingBG("res/spaceshooter/spacebg.png", 0.5f, 0, 0, 800, 600);
	Image							gameOverImage		= new ImageIcon("res/spaceshooter/gameOver.png").getImage();
	boolean						gameOver				= false;
	
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
		gameOver = false;
		player = new Player(game, game.width / 2 - Player.WIDTH / 2, (game.height - Player.HEIGHT * 2) - 75);
		waveHandler = new WaveHandler(game);
	}
	
	public void update() {
		initialize();
		joyStickPoll();
		
		spaceBG.update();
		
		if(!gameOver) {
			player.update();
			waveHandler.update();
		}
	}
	
	public void gameOver() {
		game.stateMachine.setState(game.MENU);
	}
	
	public void reset() {
		create();
	}
	
	public void render(Graphics2D g) {
		// Smooth Images
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		
		spaceBG.render(g);
		
		if(!gameOver) {
			player.render(g);
			waveHandler.render(g);
		}
		else
			renderGameOver(g);
	}
	
	public void renderGameOver(Graphics2D g) {
		// Game Over Graphic
		g.drawImage(gameOverImage, (int)game.width / 2 - 200, 100, null);
		
		// Draw Score
		Util.drawText(250, 295, "Score: " + Integer.toString(player.currentScore), 32, Color.WHITE, g);
		
		// Draw Score
		Util.drawText(190, 350, "Press Enter To Go To Menu", 32, Color.WHITE, g);
	}
	
	public void keyPressed(KeyEvent e) {
		if(!gameOver)
			player.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		if(!gameOver) {
			if(e.getKeyCode() == KeyEvent.VK_R) {
				game.playState = new PlayState(game);
				game.stateMachine.states.set(game.PLAY, game.playState);
				game.stateMachine.states.get(game.PLAY).init();
				game.stateMachine.setState(game.PLAY);
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				game.stateMachine.setState(game.PAUSE);
			
			player.keyReleased(e);
		}
		else {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				gameOver();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(!gameOver)
			player.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		if(!gameOver)
			player.mouseReleased(e);
	}
	
	public void joyStickPoll() {
		if(!gameOver) {
			// Update Joystick Input Data
			if(joystick != null) {
				// Poll Joystick for updates
				joystick.poll();
				
				// Update Player
				if(joystickEnabled) {
					player.joystickPoll(joystick.getComponents());
				}
			}
		}
	}
}
