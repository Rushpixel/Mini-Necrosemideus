package core.util;

import java.util.ArrayList;
import java.util.List;

import game.world.SceneGraph;

public class PathFinding {

	public List<Node> open = new ArrayList<Node>();
	public List<Node> closed = new ArrayList<Node>();
	public List<Node> Path = new ArrayList<Node>();
	public Node[][] graph;

	public int width;
	public int height;
	public float x;
	public float y;

	public Node Start;
	public Node Goal;

	public PathFinding(Vertex2f startOffset, Vertex2f goal, int width, int height) {

		// assign variables
		this.width = width;
		this.height = height;
		x = startOffset.x - (startOffset.x % 32) - width * 16 + 16;
		y = startOffset.y - (startOffset.y % 32) - height * 16 + 16;

		graph = new Node[width][height];
		for (int ix = 0; ix < width; ix++) {
			for (int iy = 0; iy < height; iy++) {
				graph[ix][iy] = new Node(ix, iy, !SceneGraph.map.testSolid(x + ix * 32, y + iy * 32));
			}
		}
		Start = graph[width - width / 2][height - height / 2];
		Goal = graph[(int) Math.round((goal.x - x) / 32)][(int) Math.round((goal.y - y) / 32)];

		// put start into the open list
		open.add(Start);

		// Begin cycle
		calc();
	}

	public void calc() {
		while (open.size() > 0) {
			Node currentNode = getLowestF();
			// Test to see if goal reached
			if (currentNode == Goal) {
				// Finished, goal reached
				// Follow path backwards to reconstruct the result
				reconstructPath();
				return;
			} // else continue

			// move current from open list to closed set
			open.remove(currentNode);
			closed.add(currentNode);

			// find children nodes
			Node[] neighbors = getNeighbors(currentNode);

			// handle children
			for (Node child : neighbors) {
				// If the child is in the closed list, continue to next iterator
				if (closed.contains(child)) continue;
				// Calculate tentative G score
				int TG = currentNode.G + 1;
				// If the child isn't in the open list or the tentative G score
				// is lower then the child's G score
				if (!open.contains(child) || (TG < child.G)) {
					// Set, or change the child's variables, so that current is
					// it's parent
					child.parent = currentNode;
					child.G = TG;
					child.calcH(Goal);
					child.calcF();
					// If it isn't already in the open list, move it there
					if (!open.contains(child)) open.add(child);
				}
			}
		}

		// When there are no more nodes in the open list, and the solution
		// hasn't been found
		Failure();
	}

	public void Failure() {
		// Set the only node in the path to the goal, so at least it moves in
		// the correct direction
		Path.add(Goal);
	}

	public void reconstructPath() {
		// Set the goal as the first node to be reconstructed
		Node current = Goal;

		// While the path doesn't reach the start, continue cycling
		while (current != Start) {
			Path.add(current);
			current = current.parent;
		}
	}

	public Node[] getNeighbors(Node c) {
		ArrayList<Node> list = new ArrayList<Node>();

		// add left
		if (c.x > 0) if(graph[c.x - 1][c.y].valid){
			list.add(graph[c.x - 1][c.y]);
		}
		// add right side
		if (c.x < width - 1) if(graph[c.x + 1][c.y].valid) {
			list.add(graph[c.x + 1][c.y]);
		}
		// add bottom
		if (c.y > 0) if(graph[c.x][c.y - 1].valid){
			list.add(graph[c.x][c.y - 1]);
		}
		// add top
		if (c.y < height - 1) if(graph[c.x][c.y + 1].valid){
			list.add(graph[c.x][c.y + 1]);
		}

		return list.toArray(new Node[0]);
	}

	public Node getLowestF() {
		Node node = null;
		int lowestF = Integer.MAX_VALUE;
		for (Node n : open) {
			if (n.F < lowestF) {
				node = n;
				lowestF = n.F;
			}
		}
		return node;
	}

	public void render() {
		for (int ix = 0; ix < width; ix++) {
			for (int iy = 0; iy < width; iy++) {
				if (!(graph[ix][iy] == Goal || graph[ix][iy] == Start)) {
					if (Path.contains(graph[ix][iy])) {
						graph[ix][iy].render(x, y, 0f, 0f, 0.8f);
						continue;
					}
					if (open.contains(graph[ix][iy])) {
						graph[ix][iy].render(x, y, 1f, 1f, 0f);
					}
					if (closed.contains(graph[ix][iy])) {
						graph[ix][iy].render(x, y, 1f, 0f, 1f);
					}
					graph[ix][iy].render(x, y, 0.3f, 0.3f, 0.3f);
				}
			}
			Start.render(x, y, 0f, 1f, 0f);
			Goal.render(x, y, 1f, 0f, 0f);
		}
	}

}
