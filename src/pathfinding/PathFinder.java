package pathfinding;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import enginex.EngineX;
import enginex.GameObject;

@SuppressWarnings("serial")
public class PathFinder extends GameObject {
	boolean					initialized					= false;

	// Check closedList for Path Nodes.. Ordered!
	boolean					pathFound						= false;

	ArrayList<Node>	openList						= new ArrayList<Node>();
	ArrayList<Node>	closedList					= new ArrayList<Node>();
	ArrayList<Node>	deadList						= new ArrayList<Node>();
	ArrayList<Node>	pathNodes						= new ArrayList<>();
	ArrayList<Node>	nodes								= new ArrayList<>(); 

	boolean					visible							= false;
	boolean					pathFindingEnabled	= false;
	boolean					slowSearchEnabled		= false;

	float						w										= 0;
	float						h										= 0;

	Point						startXY;
	Point						endXY;

	boolean					stage1Complete			= false;
	boolean					stage2Complete			= false;
	boolean					stage3Complete			= false;
	boolean					canSearch						= false;
	boolean					canUpdate						= false;

	Node						currentNode;

	boolean					printPathNodes			= false;

	public PathFinder(EngineX game) {
		super(game);
	}

	public ArrayList<Node> getPathNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
		pathFindingEnabled = true;
		pathfind();
		return closedList;
	}

	public void init() {
		if(!initialized)
			// Set Width and Height of PathFinder entity...
			if(w == 0 && h == 0)
			setWH();

		initialized = true;
	}

	public void setWH() {
		w = getState().nw / 2;
		h = getState().nh / 2;
	}

	public void update() {
		// Only Run Once...
		init();

		// Only Run if pathFindingEnabled is true...
		pathfind();
	}

	/** 
	 * Finds the Shortest Path between Node Start Node and End Node
	 */
	void pathfind() {
		if(pathFindingEnabled) {
			System.out.println("finding shortest path...");
			
			while(!pathFound) {
				while(!stage3Complete) {
					if(canSearch()) {
						if(!stage1Complete)
							stage1();

						if(stage1Complete && !stage2Complete)
							stage2();

						if(stage2Complete && !stage3Complete)
							stage3();
					}
				}
			}
			
			System.out.println("shortest path found...");
			pathFindingEnabled = false;
		}
	}

	/**
	 * Stage 1
	 * Calculate H, G, and F Scores...
	 * AND... Add Start Node to Closed List! AND...
	 * Add Initial Surrounding Nodes to Open List!
	 */
	void stage1() {
		Node startNode = null;
		for(Node n:getNodes()) {
			n.resetScores();
			n.calculateH();

			if(n.type == Node.START) {
				startNode = n;
				closedList.add(n);
			}
		}

		// Add Initial Surrounding Nodes to Open List!
		ArrayList<Node> sNodes = startNode.getSurroundingNodes(closedList);
		for(Node sn:sNodes) {
			sn.setParentNode(startNode);
			sn.calculateG();
			sn.calculateF();
			openList.add(sn);
		}

		stage1Complete = true;
	}

	/**
	 * Stage 2
	 * Cycles through All Nodes until END Node is reached. Adjusts Scores
	 * Accordingly.
	 */
	void stage2() {
		// Get node with lowest fScore
		currentNode = openList.get(openList.size() - 1);

		for(Node n:openList)
			if(n.fScore < currentNode.fScore)
				currentNode = n;

		if(currentNode.type == Node.END) {
			stage2Complete = true;
			pathFound = true;
		}
		else {
			// Move Node to closedList
			moveNodeFromListAtoB(currentNode, openList, closedList);

			// Get Surrounding Nodes
			ArrayList<Node> sNodes = currentNode.getSurroundingNodes(closedList);

			// Review Surrounding Nodes and Adjust Scores accordingly
			for(Node sn:sNodes) {
				if(!nodeExists(sn, closedList)) {
					// Adjust G Score according to + or x position...
					// ng is the nodes G Score...
					double ng = currentNode.gScore;
					if(sn.getPosType(currentNode) == "+")
						ng += 1.0;
					else
						ng += 1.4;

					// If node exists in openlist
					// Check if ng (new g) is lower than og (old g)
					if(nodeExists(sn, openList)) {
						// If ng is lower than og, set ParentNode to currentNode.
						if(sn.gScore > ng) {
							sn.setParentNode(currentNode);
							sn.setG(ng);
						}
					}
					else {
						sn.setParentNode(currentNode);
						sn.setG(ng);
					}

					// Recalculate F Score...
					sn.calculateF();
				}
			}

			// Add Nodes to OpenList
			for(Node sn:sNodes)
				if(!nodeExists(sn, openList))
					openList.add(sn);

			currentNode.type = Node.SCOUTED_AREA;
		}
	}

	/**
	 * Stage 3
	 * Last Step... Set parent for each node in the pathNodes.
	 */
	void stage3() {
		for(Node n:getNodes())
			if(nodeExists(n, closedList))
				if(n.type != Node.START && n.type != Node.END)
					n.type = Node.SCOUTED_AREA;

		Node tempNode = null;
		for(Node n:getNodes()) {
			if(n.type == Node.END) {
				pathNodes.add(n);
				tempNode = n.parentNode;
			}
		}

		while(tempNode.type != Node.START) {
			tempNode.type = Node.PATH;
			tempNode = tempNode.parentNode;
		}

		stage3Complete = true;
	}

	/**
	 * Checks if there is a Start Node and an End Node.
	 * @return
	 */
	boolean canSearch() {
		Node a = null;
		Node b = null;

		for(Node n:getNodes()) {
			if(n.type == Node.START)
				a = n;
			if(n.type == Node.END)
				b = n;
		}

		if(a != null && b != null)
			return true;

		return false;
	}

	/** 
	 * Checks if given node exists in given list...	
	 * @param node
	 * @param list
	 * @return
	 */
	boolean nodeExists(Node node, ArrayList<Node> list) {
		for(Node n:list) {
			if(node.x == n.x && node.y == n.y) {
				return true;
			}
		}

		return false;
	}

	public void moveNodeFromListAtoB(Node node, ArrayList<Node> listA, ArrayList<Node> listB) {
		for(Iterator<Node> it = listA.iterator(); it.hasNext();) {
			Node n = it.next();
			if(n.x == node.x && n.y == node.y) {
				listB.add(new Node(game, n.x, n.y, n.w, n.h));

				it.remove();
				break;
			}
		}
	}

	public PathfinderState getState() {
		return (PathfinderState)game.stateMachine.getCurrentState();
	}

	public ArrayList<Node> getNodes() {
		if(nodes.isEmpty())
			return getState().nodes;
		else
			return nodes;
	}

	public boolean nodeInList(Node a, ArrayList<Node> list) {
		for(Iterator<Node> it = list.iterator(); it.hasNext();) {
			Node b = it.next();
			if(a.x == b.x && a.y == b.y) {
				return true;
			}
		}

		return false;
	}

	public void render(Graphics2D g) {
		if(visible) {
			if(startXY != null) {
				g.setColor(Color.GREEN);
				g.fillOval((int)(startXY.x - w / 2), (int)(startXY.y - h / 2), (int)w, (int)h);
			}

			if(endXY != null) {
				g.setColor(Color.BLACK);
				g.fillOval((int)(endXY.x - w / 2), (int)(endXY.y - h / 2), (int)w, (int)h);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		// START PathFinding!!!
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			// Used for auto pathfinding...
			// update();

			if(!pathFindingEnabled)
				pathFindingEnabled = true;
		}
	}
}
