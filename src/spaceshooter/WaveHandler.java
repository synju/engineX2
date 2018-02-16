package spaceshooter;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class WaveHandler {
	Spaceshooter game;
	float difficulty = 0;
	
	public ArrayList<Monster> monsters = new ArrayList<>();
	
	public WaveHandler(Spaceshooter game) {
		this.game = game;
	}
	
	public void update() {
		checkComplete();
		
		for(Monster monster:monsters)
			monster.update();
	}
	
	public void render(Graphics2D g) {
		for(Monster monster:monsters)
			monster.render(g);
	}
	
	public void generateWave() {
		monsters.add(new Monster(100-25,50));
		monsters.add(new Monster(200-25,50));
		monsters.add(new Monster(300-25,50));
		monsters.add(new Monster(400-25,50));
		monsters.add(new Monster(500-25,50));
		monsters.add(new Monster(600-25,50));
		monsters.add(new Monster(700-25,50));
	}
	
	public void checkComplete() {
		// Check player alive
		if(((PlayState)(game.stateMachine.getCurrentState())).player.lives == 0)
			gameOver();
		
		// Check all monsters dead
		boolean monstersAlive = false;
		for(Monster monster:monsters)
			if(monster.alive)
				monstersAlive = true;
		
		if(!monstersAlive) {
			increaseDifficulty();
			generateWave();
		}
	}
	
	public void increaseDifficulty() {
		difficulty += 0.3;
	}
	
	public void gameOver() {
		// Display gameOverState
	}
}
