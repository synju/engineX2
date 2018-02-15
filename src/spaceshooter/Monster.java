package spaceshooter;

import java.awt.Color;
import java.awt.Graphics2D;

public class Monster {
	double x;
	double y;
	int width;
	int height;
	
	boolean alive = true;
	
	public Monster(int x, int y) {
		
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, width, height);
	}
}
