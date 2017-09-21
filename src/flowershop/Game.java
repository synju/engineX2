package flowershop;

import enginex.EngineX;

public class Game extends EngineX {
	MenuState							menuState;
	PlayState							playState;

	public final int			MENU			= 0;
	public final int			PLAY			= 1;

	public ProfileManager	profileManager;

	Game() {
		super("Flower Shop", 800, 600, false, false);
		window.setVisible(true);
//		window.setLocation(-1100, 200);
	}

	public void init() {
		// Menu State
		menuState = new MenuState(this);
		stateMachine.pushState(menuState);
		stateMachine.states.get(MENU).init();

		// Play State
		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.states.get(PLAY).init();

		run();
	}

	public static void main(String[] args) {
		new Game().init();
	}
}
