package spaceshooter;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class ScrollingBG {
	public Image	bg;
	public String	imagePath	= "res/spaceshooter/spacebg.jpg";
	public float	x			= 0;
	public float	y			= 0;
	public int		w			= 800;
	public int		h			= 600;
	public float	speed		= 0.2f;

	public ScrollingBG(String imagePath, float speed, int x, int y, int w, int h) {
		this.speed = speed;
		this.imagePath = imagePath;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		this.bg = new ImageIcon(imagePath).getImage();
	}

	public void update() {
		this.y += speed;

		if(y >= 600)
			y = 0;
	}

	public void render(Graphics2D g) {
		g.drawImage(bg, (int)x, (int)y, null);
		g.drawImage(bg, (int)x, (int)y - 600, null);
	}
}
