package graph;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class DepthFirstSearch {

	// return the path from startNode to endNode and its cost
	public static String depthFirstSearch( int startNode, int endNode, AdjacencyListGraph graph ) {
		// validating inputs
		if ( startNode < 0 || startNode >= graph.getNumOfNodes() || endNode < 0 || endNode >= graph.getNumOfNodes() ) {
			return "Invalid input";
		}

		boolean[] visited = new boolean[graph.getNumOfNodes()];
		int[] parent = new int[graph.getNumOfNodes()];

		parent[startNode] = -1;
		visited[startNode] = true;

		String path = depthFirstSearch( startNode, endNode, graph, visited, parent );
		return path != null ? path : String.format( "No route found from %s to %s", startNode, endNode );
	}

	private static String depthFirstSearch( int startNode, int endNode, AdjacencyListGraph graph, boolean[] visited, int[] parent ) {
		if ( startNode == endNode ) {
			return getPath( startNode, endNode, parent );
		}

		for ( int n : graph.getNode( startNode ).getAdjacentNodesList() ) {
			if ( !visited[n] ) {
				parent[n] = startNode;
				visited[n] = true;

				return depthFirstSearch( n, endNode, graph, visited, parent );
			}
		}

		return null;
	}

	private static String getPath( int startNode, int endNode, int[] parent ) {
		LinkedList<Integer> path = new LinkedList<>();
		int currentNode = endNode;
		int cost = -1; // cost is the number of edges between startNode and endNode
		while ( currentNode != -1 ) {
			path.addFirst( currentNode );
			currentNode = parent[currentNode];
			cost++;
		}

		return "Path: " + path.stream().map( String::valueOf ).collect( Collectors.joining( " -> " ) ) + ", Cost: " + cost;
	}

}
