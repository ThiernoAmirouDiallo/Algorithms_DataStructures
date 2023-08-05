package graph;

public class GraphBuilder {

	public static AdjacencyListGraph getGraph( boolean addSecondComponent ) {

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
