package towerdefense;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import enginex.State;

public class PlayState extends State {
	TowerDefense	game;
	boolean				initialized	= false;
	
	ArrayList<Tower> towers;
	
	Monster m;

	protected PlayState(TowerDefense game) {
		super(game);
		this.game = game;
	}

	public void create() {
		if(initialized)
			return;
		
		towers = new ArrayList<Tower>();
		towers.add(new Tower(game, 1*50, 1*50, 50, 50, true));
		
		m = new Monster(game, 500,500,100,200);

		initialized = true;
	}

	public void update() {
		create();
		
		m.update();
		
		for(Tower tower:towers)
			tower.update();
	}

	public void render(Graphics2D g) {
		for(Tower tower:towers)
			tower.render(g);
		
		m.render(g);
	}

	public void mousePressed(MouseEvent e) {
		for(Tower tower:towers)
				tower.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		for(Tower tower:towers)
				tower.mouseReleased(e);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE);
			game.exit();
	}
}
