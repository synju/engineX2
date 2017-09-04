package towerdefense;

import enginex.EngineX;

public class TowerDefense extends EngineX {
	PlayState					playState;
	public final int	PLAY	= 0;

	public static void main(String[] args) {
		new TowerDefense().init();
	}

	TowerDefense() {
		super("Tower Defense", 800*2, 450*2);
	}

	public void init() {
		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.states.get(PLAY).init();
		run();
	}
}
