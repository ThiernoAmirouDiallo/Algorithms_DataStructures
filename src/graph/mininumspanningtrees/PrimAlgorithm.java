package graph.mininumspanningtrees;

import static graph.GraphBuilder.getGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import graph.AdjacencyListGraph;
import graph.Edge;
import graph.GraphBuilder;

public class PrimAlgorithm {

	public static void main( String[] args ) {

		/**
		 * retuns this graph
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
		System.out.println( "Prim's MST" );
		System.out.println( "Single componet graph" );
		List<Edge<Character>> edges = GraphBuilder.getEdgeList();

		AdjacencyListGraph<Character> graph = getGraph( edges );
		List<Edge<Character>> primsMst = getPrimsMst( graph );
		Integer cost = primsMst.stream().map( Edge::getWeight ).reduce( 0, Integer::sum );
		System.out.println( "Cost = " + cost + ", MST: " + primsMst.stream().sorted( Comparator.comparing( Objects::toString ) ).collect( Collectors.toList() ) );

		System.out.println( "Graph with multiple components" );
		graph.addEdge( 'I', 'J', 9 );
		System.out.println( getPrimsMst( graph ) );
	}

	public static List<Edge<Character>> getPrimsMst( AdjacencyListGraph<Character> graph ) {
		AdjacencyListGraph.Node<Character> initialNode = graph.getNode( 0 );

		// By default, the priority queue in Java is min Priority queue with natural ordering
		// PriorityQueue of edges represented by Pair.of( node, weight )
		PriorityQueue<Edge<Integer>> edgePriorityQueue = new PriorityQueue<>( Comparator.comparing( Edge::getWeight ) );
		edgePriorityQueue.addAll( initialNode.getAdjacentNodesListWithWeight().stream().map( e -> new Edge<>( initialNode.getId(), e.fst, e.snd ) ).collect( Collectors.toList() ) );

		Set<Integer> joinedVertices = new HashSet<>();
		Set<Edge<Integer>> mstEdges = new HashSet<>();

		while ( !edgePriorityQueue.isEmpty() && joinedVertices.size() < graph.getNumOfNodes() ) {
			Edge<Integer> currentEdge = edgePriorityQueue.poll();

			if ( joinedVertices.size() == 0 ) { // first poll
				mstEdges.add( currentEdge );
				joinedVertices.add( currentEdge.getNode1() );
				joinedVertices.add( currentEdge.getNode2() );

				/**
				 * here node1 is the initial node
				 * add node2 adjacent edges to the priority queue
				 */
				edgePriorityQueue.addAll( graph.getNode( currentEdge.getNode2() )
						.getAdjacentNodesListWithWeight()
						.stream()
						.map( e -> new Edge<>( currentEdge.getNode2(), e.fst, e.snd ) )
						.collect( Collectors.toList() ) );
			} else {
				// crossing edge found: one vertex inside the boundary and the other outside of it
				if ( (joinedVertices.contains( currentEdge.getNode1() ) && !joinedVertices.contains( currentEdge.getNode2() )) ||
						(joinedVertices.contains( currentEdge.getNode2() ) && !joinedVertices.contains( currentEdge.getNode1() )) ) {
					mstEdges.add( currentEdge );

					Integer newVertex = joinedVertices.contains( currentEdge.getNode1() ) ? currentEdge.getNode2() : currentEdge.getNode1();
					joinedVertices.add( newVertex );

					// adding the new node's adjacent edges to the priority queue
					edgePriorityQueue.addAll( graph.getNode( newVertex ).getAdjacentNodesListWithWeight().stream().map( e -> new Edge<>( newVertex, e.fst, e.snd ) ).collect( Collectors.toList() ) );
				} else {
					/**
					 * Nothing to do here because:
					 * 	1- the edge is connecting 2 vertices in the joined set
					 * 	We cannot poll an edge connecting 2 vertices that are both not joined (that only happens in the first poll)).
					 * 	This is because we are only adding edges from connected vertices -> connect/non connected vertices
					 */
				}
			}
		}

		if ( joinedVertices.size() == graph.getNumOfNodes() ) {
			// converting the edges if ids to edges of characters
			return mstEdges.stream()
					.map( e -> new Edge<>( graph.getNode( e.getNode1() ).getValue(), graph.getNode( e.getNode2() ).getValue(), e.getWeight() ) )
					.collect( Collectors.toList() );
		} else {
			// no route connects all the nodes (multiple components found )
			return new ArrayList<>();
		}
	}
}
