package spaceshooter;

import java.awt.Color;
import java.awt.Graphics2D;

public class PlayerBullet {
	double	x;
	double	y;
	float	speed				= 3f;
	int		width				= 10;
	int		height				= 10;
	boolean	outOfWindowBounds	= false;

	public PlayerBullet(int x, int y) {
		this.x = x - width / 2;
		this.y = y;
	}

	public void update() {
		this.y -= speed;
		if(!outOfWindowBounds)
			if(this.y < 0 - height)
				outOfWindowBounds = true;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect((int)x, (int)y, width, height);
	}
}
