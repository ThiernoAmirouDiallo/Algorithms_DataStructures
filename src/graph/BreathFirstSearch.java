package graph;

import static graph.GraphBuilder.getGraph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class BreathFirstSearch {


	public static void main( String[] args ) {

		/*
		 *                ___ 0 ___
		 * 			(2) /     |    \
		 *    8        1----- 2 ----3
		 *    |        |   /  |  /  |
		 *    9         4     5     |
		 *              \   /       |
		 * 				  6------- 7
		 * */
		AdjacencyListGraph graph = getGraph( true );

		// 1 - Breath first search
		System.out.println( "Breath first search" );
		System.out.println( BreathFirstSearch.breathFirstSearch( 0, 5, graph ) );
		System.out.println( BreathFirstSearch.breathFirstSearch( 0, 0, graph ) );
		System.out.println( BreathFirstSearch.breathFirstSearch( 2, 6, graph ) );
		System.out.println( BreathFirstSearch.breathFirstSearch( 0, 9, graph ) );
		System.out.println( BreathFirstSearch.breathFirstSearch( 8, 9, graph ) );

		// 2 - Breadth first search

		// 3 - Minimum spanning tree

		// 3.1 - Kruskal's algorithm

		// 3.2 - Prim's algorithm

		// 4 - Shortest path
		// 4.1 Dijkstra's algorithm
	}

	// return the path from startNode to endNode and its cost
	public static String breathFirstSearch( int startNode, int endNode, AdjacencyListGraph graph ) {
		// validating inputs
		if ( startNode < 0 || startNode >= graph.getNumOfNodes() || endNode < 0 || endNode >= graph.getNumOfNodes() ) {
			return "Invalid input";
		}

		boolean[] visited = new boolean[graph.getNumOfNodes()];
		int[] parent = new int[graph.getNumOfNodes()];

		parent[startNode] = -1;
		visited[startNode] = true;

		if ( startNode == endNode ) {
			return getPath( endNode, parent );
		}

		Queue<Integer> queue = new LinkedList<>();
		queue.add( startNode );

		String path = breathFirstSearch( endNode, graph, visited, parent, queue );

		return path != null ? path : String.format( "No route found from %s to %s", startNode, endNode );
	}

	private static String breathFirstSearch( int endNode, AdjacencyListGraph graph, boolean[] visited, int[] parent, Queue<Integer> queue ) {
		while ( !queue.isEmpty() ) {
			int currentNode = queue.poll();

			if ( currentNode == endNode ) {
				return getPath( currentNode, parent );
			}

			for ( int n : graph.getNode( currentNode ).getAdjacentNodesList() ) {
				if ( !visited[n] ) {
					parent[n] = currentNode;
					visited[n] = true;
					queue.add( n );

				}
			}

			return breathFirstSearch( endNode, graph, visited, parent, queue );
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
