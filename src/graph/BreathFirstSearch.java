package graph;

import static graph.GraphBuilder.getGraph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class BreathFirstSearch {


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

		// 1 - Breath first search
		System.out.println( "Breath first search" );
		System.out.println( BreathFirstSearch.breathFirstSearch( 0, 5, graph ) );
		System.out.println( BreathFirstSearch.breathFirstSearch( 0, 7, graph ) );
		System.out.println( BreathFirstSearch.breathFirstSearch( 0, 0, graph ) );
		System.out.println( BreathFirstSearch.breathFirstSearch( 2, 6, graph ) );
		System.out.println( BreathFirstSearch.breathFirstSearch( 0, 9, graph ) );
		System.out.println( BreathFirstSearch.breathFirstSearch( 8, 9, graph ) );
	}

	// return the path from startNode to endNode and its cost
	public static String breathFirstSearch( int startNode, int endNode, AdjacencyListGraph<Integer> graph ) {
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

		String path = null;

		while ( !queue.isEmpty() ) {
			int currentNode = queue.poll();

			if ( currentNode == endNode ) {
				path = getPath( currentNode, parent );
				break;
			}

			for ( int n : graph.getNode( currentNode ).getAdjacentNodesList() ) {
				if ( !visited[n] ) {
					parent[n] = currentNode;
					visited[n] = true;
					queue.add( n );
				} else {
					// there is a cyble in the graph
				}
			}
		}

		return path;
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
