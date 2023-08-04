package graph;

import java.util.LinkedList;
import java.util.Objects;

public class AdjacencyListGraph {

	private Node[] nodes;
	private int numOfNodes = 0;

	public AdjacencyListGraph(int numOfNodes) {
		this.numOfNodes = numOfNodes;
		this.nodes = new Node[numOfNodes];

		for (int i = 0; i < numOfNodes; i++) {
			this.nodes[i] = new Node(i);
		}
	}

	public Node getNode(int i) {
		return nodes[i];
	}

	public void addEdge(int node1, int node2) {
		if (node1 >= 0 && node1 < numOfNodes && node2 >= 0 && node2 < numOfNodes) {
			nodes[node1].addEdge(node2);
			nodes[node2].addEdge(node1);
		}
	}

	public void showEdges() {
		for (int i = 0; i < numOfNodes; i++) {
			nodes[i].showEdges();
		}
	}

	public int getNumOfNodes() {
		return numOfNodes;
	}

	public static class Node {
		private int id;
		private LinkedList<Integer> adjacentNodesList = new LinkedList<>();

		public Node(int id) {
			this.id = id;
		}

		public void addEdge(int node) {
			adjacentNodesList.add(node);
		}

		public LinkedList<Integer> getAdjacentNodesList() {
			return adjacentNodesList;
		}

		@Override
		public boolean equals( Object o ) {
			if ( this == o ) {
				return true;
			}
			if ( !(o instanceof Node) ) {
				return false;
			}

			Node node = (Node) o;

			return id == node.id;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(id);
		}

		public void showEdges() {
			System.out.print("Node " + id + " is connected to: ");
			for ( Integer integer : adjacentNodesList ) {
				System.out.print( integer + " " );
			}
			System.out.println();
		}
	}
}
