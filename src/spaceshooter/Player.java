package spaceshooter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import net.java.games.input.Component;

public class Player {
	Spaceshooter						game;
	
	double									x									= 0;
	double									y									= 0;
	public static final int	WIDTH							= 50;
	public static final int	HEIGHT						= 50;
	int											h									= 50;
	Color										color							= Color.WHITE;
	
	public int 							lives 						= 3;
	
	float										speed							= 5;
	
	boolean									left							= false;
	boolean									right							= false;
	boolean									up								= false;
	boolean									down							= false;
	
	boolean									shooting					= false;
	
	float										moveX							= 0f;
	float										moveY							= 0f;
	
	int											maxBullets				= 100;
	int											bulletCooldown		= 0;
	int											bulletCooldownMax	= 10;
	
	ArrayList<PlayerBullet>	bullets						= new ArrayList<>();
	
	Component[]							components;
	
	public Player(Spaceshooter game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		move();
		
		updateBullets();
		
		shoot();
	}
	
	public PlayState getState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
	
	void showID(Object o) {
		System.out.println(System.identityHashCode(o));
	}
	
	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
		
		renderBullets(g);
	}
	
	public void renderBullets(Graphics2D g) {
		for(PlayerBullet b:bullets)
			b.render(g);
	}
	
	public void move() {
		if(up && !down) {
			if(y > 0)
				y -= speed;
			else
				y = 0;
		}
		else if(down && !up) {
			int v1 = 79;
			if(y < game.height - v1)
				y += speed;
			else
				y = game.height - v1;
		}
		
		if(left && !right) {
			if(x > 0)
				x -= speed;
			else
				x = 0;
			
		}
		else if(right && !left) {
			int v1 = 56;
			if(x < game.width - v1)
				x += speed;
			else
				x = game.width - v1;
		}
		
	}
	
	public void shoot() {
		if(shooting) {
			if(bulletCooldown == 0) {
				if(bullets.size() < maxBullets) {
					bullets.add(new PlayerBullet((int)this.x + WIDTH / 2, (int)this.y));
					bulletCooldown = bulletCooldownMax;
				}
			}
		}
	}
	
	public void updateBullets() {
		if(bulletCooldown > 0) {
			bulletCooldown--;
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			PlayerBullet b = bullets.get(i);
			if(b.outOfWindowBounds)
				bullets.remove(i);
		}
		
		for(PlayerBullet b:bullets)
			b.update();
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A)
			left = true;
		if(e.getKeyCode() == KeyEvent.VK_D)
			right = true;
		if(e.getKeyCode() == KeyEvent.VK_W)
			up = true;
		if(e.getKeyCode() == KeyEvent.VK_S)
			down = true;
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			shooting = true;
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A)
			left = false;
		if(e.getKeyCode() == KeyEvent.VK_D)
			right = false;
		if(e.getKeyCode() == KeyEvent.VK_W)
			up = false;
		if(e.getKeyCode() == KeyEvent.VK_S)
			down = false;
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			shooting = false;
	}
	
	public void joystickPoll(Component[] components) {
		for(Component c:components) {
			String componentName = c.getName();
			float pollData = c.getPollData();
			
			// SHOW EVERYTHING
			// System.out.println("[" + componentName + "]---:---[" + pollData + "]");
			
			// Y Axis
			if(componentName.equals("Y Axis")) {
				if(pollData == -1.0)
					up = true;
				else if(pollData == 1.0)
					down = true;
				else {
					up = false;
					down = false;
				}
			}
			
			// X Axis
			if(componentName.equals("X Axis")) {
				if(pollData == -1.0)
					left = true;
				else if(pollData == 1.0)
					right = true;
				else {
					left = false;
					right = false;
				}
			}
			
			// Button 2
			if(componentName.equals("Button 2")) {
				if(pollData == 1.0) {
					shooting = true;
				}
				else {
					shooting = false;
				}
			}
		}
	}
}
