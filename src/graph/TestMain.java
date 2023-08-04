package graph;

public class TestMain {

	public static void main( String[] args ) {

		/*
		 *                ___ 0 ___
		 * 			    /     |    \
		 *    8        1----- 2 ----3
		 *    |        |   /   \    |
		 *    9        4      5     |
		 *              \   /       |
		 * 				  6------- 7
		 * */
		AdjacencyListGraph graph = getGraph(true);

		// 1 - Depth first search
		System.out.println( "Depth first search" );
		System.out.println( DepthFirstSearch.depthFirstSearch( 0, 5, graph ) );
		System.out.println( DepthFirstSearch.depthFirstSearch( 0, 0, graph ) );
		System.out.println( DepthFirstSearch.depthFirstSearch( 2, 6, graph ) );
		System.out.println( DepthFirstSearch.depthFirstSearch( 0, 9, graph ) );
		System.out.println( DepthFirstSearch.depthFirstSearch( 8, 9, graph ) );

		// 2 - Breadth first search

		// 3 - Minimum spanning tree

		// 3.1 - Kruskal's algorithm

		// 3.2 - Prim's algorithm

		// 4 - Shortest path
		// 4.1 Dijkstra's algorithm
	}

	private static AdjacencyListGraph getGraph( boolean addSecondComponent ) {
		AdjacencyListGraph graph;
		graph = new AdjacencyListGraph( 10 );
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

}
