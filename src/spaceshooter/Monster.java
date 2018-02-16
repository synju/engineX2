package spaceshooter;

import java.awt.Color;
import java.awt.Graphics2D;

public class Monster {
	double x;
	double y;
	int width = 50;
	int height = 50;
	
	int posMin = 0;
	int posMax = 50;
	int pos = posMax/2;
	
	boolean movingRight = true;
	
	boolean alive = true;
	
	public Monster(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		move();
	}
	
	public void move() {
		if(movingRight) {
			if(pos < posMax) {
				this.x++;
				pos++;
			}
			
			if(pos == posMax)
				movingRight = false;
		}
		else {
			if(pos > posMin) {
				this.x--;
				pos--;
			}
			
			if(pos == posMin)
				movingRight = true;
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, width, height);
	}
}
