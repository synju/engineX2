package flowershop;

import java.awt.Graphics2D;

import enginex.State;

public class PlayState extends State {
	Game game;

	protected PlayState(Game game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized)
			return;

		create();

		initialized = true;
	}

	public void create() {
		
	}

	public void update() {
		initialize();
	}

	public void render(Graphics2D g) {

	}
}
