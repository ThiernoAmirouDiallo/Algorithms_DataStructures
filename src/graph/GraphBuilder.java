package graph;

import java.util.ArrayList;
import java.util.List;

public class GraphBuilder {

	public static AdjacencyListGraph<Integer> getGraph( boolean addSecondComponent ) {

		/*
		 *  returns the graph
		 *               ___ 0 ___
		 * 			    /     |    \
		 *    8        1----- 2 ----3
		 *    |        |   /  |  /  |
		 *    9         4     5     |
		 *              \   /       |
		 * 				  6------- 7
		 * */

		AdjacencyListGraph<Integer> graph;
		graph = new AdjacencyListGraph<>( 10 );
		graph.addEdge( 0, 1 );
		graph.addEdge( 0, 2 );
		graph.addEdge( 1, 2 );
		graph.addEdge( 0, 3 );
		graph.addEdge( 2, 3 );
		graph.addEdge( 1, 4 );
		graph.addEdge( 2, 4 );
		graph.addEdge( 2, 5 );
		graph.addEdge( 3, 5 );
		graph.addEdge( 4, 6 );
		graph.addEdge( 5, 6 );
		graph.addEdge( 3, 7 );
		graph.addEdge( 6, 7 );
		graph.addEdge( 6, 7 );

		if ( addSecondComponent ) {
			graph.addEdge( 8, 9 );
		}

		return graph;
	}

	public static AdjacencyListGraph<Character> getGraph( List<Edge<Character>> edges ) {
		AdjacencyListGraph<Character> graph = new AdjacencyListGraph<>( 2 );
		edges.forEach( edge -> graph.addEdge( edge.node1, edge.node2, edge.weight ) );

		return graph;
	}

	public static List<Edge<Character>> getEdgeList() {

		/**
		 *                       B  \15
		 *                  5/  /      C_________
		 *                A   5/ 16/   |  \      |
		 *              / \2 /  /   10|    \11   |
		 *            /    D ---13--E  \   |     |
		 *          16    /17    /  |  2\ /      |12
		 *            \  /  12/   8|      H      |
		 *               F ___4___ G ____9/      |
		 *               |_______________________|
		 */

		List<Edge<Character>> edges = new ArrayList<>();
		edges.add( new Edge<>( 'A', 'B', 5 ) );
		edges.add( new Edge<>( 'A', 'D', 2 ) );
		edges.add( new Edge<>( 'A', 'F', 16 ) );
		edges.add( new Edge<>( 'B', 'C', 15 ) );
		edges.add( new Edge<>( 'B', 'D', 5 ) );
		edges.add( new Edge<>( 'C', 'D', 16 ) );
		edges.add( new Edge<>( 'C', 'E', 10 ) );
		edges.add( new Edge<>( 'C', 'H', 11 ) );
		edges.add( new Edge<>( 'C', 'F', 12 ) );
		edges.add( new Edge<>( 'D', 'F', 17 ) );
		edges.add( new Edge<>( 'D', 'E', 13 ) );
		edges.add( new Edge<>( 'E', 'F', 12 ) );
		edges.add( new Edge<>( 'E', 'G', 8 ) );
		edges.add( new Edge<>( 'E', 'H', 2 ) );
		edges.add( new Edge<>( 'F', 'G', 4 ) );
		edges.add( new Edge<>( 'G', 'H', 9 ) );

		return edges;
	}
}
