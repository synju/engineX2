package spaceshooter;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import enginex.State;

public class GameOverState extends State {
	Spaceshooter	game;
	boolean			initialized	= false;

	ScrollingBG	spaceBG			= new ScrollingBG("res/spaceshooter/spacebg.png", 0.0f, 0, 0, 800, 600);
	Image		gameOverImage	= new ImageIcon("res/spaceshooter/gameOver.png").getImage();

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

	public void create() {}

	public void update() {
		initialize();
	}

	public void render(Graphics2D g) {
		// Smooth Images
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		spaceBG.render(g);
		g.drawImage(gameOverImage, (int)game.width/2-200, 100, null);
	}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}

	public void mousePressed(MouseEvent e) {}
}
