package spaceshooter;

import java.util.ArrayList;

public class WaveHandler {
	Spaceshooter game;
	float difficulty = 0;
	
	public ArrayList<Monster> monsters = new ArrayList<>();
	
	public WaveHandler(Spaceshooter game) {
		this.game = game;
	}
	
	public void generateWave() {
		
	}
	
	public void checkComplete() {
		// Check player alive
		if(((PlayState)(game.stateMachine.getCurrentState())).player.lives == 0) {
			gameOver();
		}
		
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
