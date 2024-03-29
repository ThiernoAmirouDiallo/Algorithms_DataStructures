package graph;

import static graph.GraphBuilder.getGraph;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class DepthFirstSearch {

	public static void main( String[] args ) {

		/*
		 *                ___ 0 ___
		 * 			    /     |    \
		 *    8        1----- 2 ----3
		 *    |        |   /  |  /  |
		 *    9         4     5     |
		 *              \   /       |
		 * 				  6------- 7
		 * */
		AdjacencyListGraph<Integer> graph = getGraph( true );

		// 1 - Depth first search
		System.out.println( "Depth first search" );
		System.out.println( DepthFirstSearch.depthFirstSearch( 0, 5, graph ) );
		System.out.println( DepthFirstSearch.depthFirstSearch( 0, 0, graph ) );
		System.out.println( DepthFirstSearch.depthFirstSearch( 2, 6, graph ) );
		System.out.println( DepthFirstSearch.depthFirstSearch( 0, 9, graph ) );
		System.out.println( DepthFirstSearch.depthFirstSearch( 8, 9, graph ) );
	}

	// return the path from startNode to endNode and its cost
	public static String depthFirstSearch( int startNode, int endNode, AdjacencyListGraph<Integer> graph ) {
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

	private static String depthFirstSearch( int startNode, int endNode, AdjacencyListGraph<Integer> graph, boolean[] visited, int[] parent ) {
		if ( startNode == endNode ) {
			return getPath( endNode, parent );
		}

		for ( int n : graph.getNode( startNode ).getAdjacentNodesList() ) {
			if ( !visited[n] ) {
				parent[n] = startNode;
				visited[n] = true;

				return depthFirstSearch( n, endNode, graph, visited, parent );
			} else {
				// there is a cycle in the graph
			}
		}

		return null;
	}

	private static String getPath( int endNode, int[] parent ) {
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
