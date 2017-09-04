package pathfinding;

import enginex.EngineX;

public class Pathfinder extends EngineX {
	PathfinderState ps;

	protected Pathfinder(String gameName, int w, int h) {
		super(gameName, w, h);
	}

	public void init() {
		ps = new PathfinderState(this);
		stateMachine.pushState(ps);
		stateMachine.states.get(0).init();
		run();
	}
	
	public static void main(String[] args) {
		new Pathfinder("Pathfinder", 320, 240).init();
	}
}
