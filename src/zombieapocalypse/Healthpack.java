package zombieapocalypse;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import enginex.EngineX;
import enginex.GameObject;

@SuppressWarnings("serial")
public class Healthpack extends GameObject {
	int			x;
	int			y;
	static int	w		= 30;
	static int	h		= 30;
	float		alpha	= 1.0f;
	boolean		used	= false;

	Rectangle bounds;

	Healthpack(EngineX game, int x, int y) {
		super(game);
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, w, h);
	}

	public void render(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.drawImage(((PlayState)getCurrentState()).healthpackImage, (int)x - 2, (int)y - 2, null);
	}
}
