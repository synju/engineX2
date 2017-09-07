package pathfinding;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enginex.EngineX;
import enginex.State;

public class PathfinderState extends State {
	ArrayList<Node>	nodes;

	int							scale				= 2;
	int							xNodes			= 10;
	int							yNodes			= 10;
	float						nw					= ((float)game.getWidth()) / xNodes;
	float						nh					= ((float)game.getHeight()) / yNodes;

	Builder					builder			= new Builder(game);
	PathFinder			pathfinder	= new PathFinder(game);

	protected PathfinderState(EngineX game) {
		super(game);

		addNodes();
		addGameObject(builder);
	}

	void addNodes() {
		nodes = new ArrayList<>();
		
		for(int y = 0; y < yNodes; y++)
			for(int x = 0; x < xNodes; x++)
				nodes.add(new Node(game, x, y, nw, nh));
	}

	public void update() {
		try {
			builder.update();
			pathfinder.update();
		}
		catch(Exception e) {
		}
	}

	public void render(Graphics2D g) {
		try {
			for(Node n:nodes)
				n.render(g);
			
			pathfinder.render(g);
		}
		catch(Exception e) {
		}
	}

	void resetGame() {
		try {
			addNodes();
			pathfinder = new PathFinder(game);
		}
		catch(Exception e) {
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_R) {
			resetGame();
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			game.exit();
		}

		// START PathFinding!!!
		pathfinder.keyReleased(e);
	}
}
