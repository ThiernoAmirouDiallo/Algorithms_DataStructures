package graph.mininumspanningtrees;

import static graph.GraphBuilder.getGraph;

import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
		List<Edge<Character>> primsMst2 = getPrimsMstAnotherWay( graph );
		Integer cost = primsMst.stream().map( Edge::getWeight ).reduce( 0, Integer::sum );
		Integer cost2 = primsMst2.stream().map( Edge::getWeight ).reduce( 0, Integer::sum );
		System.out.println( "Cost = " + cost + ", MST: " + primsMst.stream().sorted( Comparator.comparing( Objects::toString ) ).collect( Collectors.toList() ) );
		System.out.println( "Cost = " + cost2 + ", MST: " + primsMst2.stream().sorted( Comparator.comparing( Objects::toString ) ).collect( Collectors.toList() ) );

		System.out.println( "Graph with multiple components" );
		graph.addEdge( 'I', 'J', 9 );
		System.out.println( getPrimsMst( graph ) );
		System.out.println( getPrimsMstAnotherWay( graph ) );
	}

	public static List<Edge<Character>> getPrimsMst( AdjacencyListGraph<Character> graph ) {
		// By default, the priority queue in Java is min Priority queue with natural ordering
		// PriorityQueue of edges represented by Pair.of( vertex, distance )
		PriorityQueue<Pair<Integer, Integer>> verticesPriorityQueue = new PriorityQueue<>( Comparator.comparing( pair -> pair.snd ) );

		// distance from joined vertices to this vertex if known
		Map<Integer, Integer> distance = new HashMap<>();

		// distance to node 0 = 0
		verticesPriorityQueue.add( Pair.of( 0, 0 ) );

		// initialize unknown distances to Integer.MAX_VALUE
		for ( int i = 1; i < graph.getNumOfNodes(); i++ ) {
			verticesPriorityQueue.add( Pair.of( i, Integer.MAX_VALUE ) ); // set max distance to vertex(i)
			distance.put( i, Integer.MAX_VALUE );
		}

		Set<Integer> joinedVertices = new HashSet<>(); // to track reached vertices
		Map<Integer, Pair<Integer, Integer>> parent = new HashMap<>(); // parent of i is pair (j, weight)

		// repeat N times
		while ( !verticesPriorityQueue.isEmpty() ) {
			Pair<Integer, Integer> currentVertex = verticesPriorityQueue.poll();

			// no edge to this vertex was discovered: multiple component graph -> no MST
			if ( currentVertex.snd == Integer.MAX_VALUE ) {
				break;
			}

			// add the discovered
			joinedVertices.add( currentVertex.fst );

			// update adjacent vertices distance
			graph.getNode( currentVertex.fst ).getAdjacentNodesListWithWeight().forEach( vertexWeightPair -> {
				if ( !joinedVertices.contains( vertexWeightPair.fst ) && vertexWeightPair.snd < distance.get( vertexWeightPair.fst ) ) {
					// because we will update the distance, let's update the priority queue by removing and reinserting with the new distance
					verticesPriorityQueue.remove( Pair.of( vertexWeightPair.fst, distance.get( vertexWeightPair.fst ) ) );

					// update distance
					distance.put( vertexWeightPair.fst, vertexWeightPair.snd );
					verticesPriorityQueue.add( Pair.of( vertexWeightPair.fst, distance.get( vertexWeightPair.fst ) ) );

					// the parent of the adjacent vertex is the current edge
					parent.put( vertexWeightPair.fst, Pair.of( currentVertex.fst, vertexWeightPair.snd ) );
				}
			} );
		}

		if ( joinedVertices.size() == graph.getNumOfNodes() ) {
			// compute edges
			return computeMstEdges( parent, graph );
		} else {
			// no route connects all the nodes (multiple components found )
			return new ArrayList<>();
		}

	}

	private static List<Edge<Character>> computeMstEdges( Map<Integer, Pair<Integer, Integer>> parents, AdjacencyListGraph<Character> graph ) {
		List<Edge<Character>> mstEdges = new ArrayList<>();

		parents.forEach( ( child, parentWeightPair ) -> mstEdges.add( new Edge<>( graph.getNode( parentWeightPair.fst ).getValue(), graph.getNode( child ).getValue(), parentWeightPair.snd ) ) );

		return mstEdges;
	}

	public static List<Edge<Character>> getPrimsMstAnotherWay( AdjacencyListGraph<Character> graph ) {
		AdjacencyListGraph.Node<Character> initialNode = graph.getNode( 0 );

		// By default, the priority queue in Java is min Priority queue with natural ordering
		// PriorityQueue of edges represented by Pair.of( node, weight )
		PriorityQueue<Edge<Integer>> edgePriorityQueue = new PriorityQueue<>( Comparator.comparing( Edge::getWeight ) );
		edgePriorityQueue.addAll( initialNode.getAdjacentNodesListWithWeight().stream().map( e -> new Edge<>( initialNode.getId(), e.fst, e.snd ) ).collect( Collectors.toList() ) );

		Set<Integer> joinedVertices = new HashSet<>();
		Set<Edge<Integer>> mstEdges = new HashSet<>();

		// Repeat M times (number of edges)
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
			return mstEdges.stream().map( e -> new Edge<>( graph.getNode( e.getNode1() ).getValue(), graph.getNode( e.getNode2() ).getValue(), e.getWeight() ) ).collect( Collectors.toList() );
		} else {
			// no route connects all the nodes (multiple components found )
			return new ArrayList<>();
		}
	}
}
