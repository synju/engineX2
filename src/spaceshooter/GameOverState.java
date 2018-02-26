package spaceshooter;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import enginex.State;

public class GameOverState extends State {
	Spaceshooter	game;
	boolean				initialized	= false;
	
	ScrollingBG		spaceBG;
	
	public GameOverState(Spaceshooter game) {
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
		spaceBG = new ScrollingBG(game.res.spaceBG.getPath(), 0.0f, 0, 0, 800, 600);
	}
	
	public void update() {
		initialize();
	}
	
	public void render(Graphics2D g) {
		// Smooth Images
		game.smoothRendering(g);
		
		spaceBG.render(g);
		g.drawImage(game.res.gameOverImage.getImage(), (int)game.width / 2 - 200, 100, null);
	}
	
	public void keyPressed(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}
	
	public void mousePressed(MouseEvent e) {}
}
