package graph.mininumspanningtrees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import graph.Edge;
import graph.GraphBuilder;
import graph.disjointsetunionfind.DisjointSet;

public class KhruskalAlgorithm {

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
		System.out.println( "Kruskal's MST" );
		System.out.println( "Single componet graph" );
		List<Edge<Character>> edges = GraphBuilder.getEdgeList();
		List<Edge<Character>> kruskalsMST = getKruskalsMST( edges );
		Integer cost = kruskalsMST.stream().map( Edge::getWeight ).reduce( 0, Integer::sum );
		System.out.println( "Cost = " + cost + ", MST: " + kruskalsMST.stream().sorted( Comparator.comparing( Objects::toString ) ).collect( Collectors.toList() ) );

		System.out.println( "Graph with multiple components" );
		edges.add( new Edge<>( 'I', 'J', 9 ) );
		System.out.println( getKruskalsMST( edges ) );
	}

	public static List<Edge<Character>> getKruskalsMST( List<Edge<Character>> edges ) {
		// By default, the priority queue in Java is min Priority queue with natural ordering
		PriorityQueue<Edge<Character>> edgePriorityQueue = new PriorityQueue<>( edges.size(), Comparator.comparing( Edge::getWeight ) );
		edgePriorityQueue.addAll( edges );

		// this could have been given as an input (would have been necessary if not connected vertices existed)
		Set<Character> vertices = new HashSet<>();
		edges.forEach( edge -> {
			vertices.add( edge.getNode1() );
			vertices.add( edge.getNode2() );
		} );

		// mapping vertices to ints for the disjoint set
		HashMap<Character, Integer> verticesMap = new HashMap<>();
		AtomicInteger i = new AtomicInteger( 0 );
		vertices.forEach( v -> verticesMap.put( v, i.getAndIncrement() ) );

		// creating the disjoint set
		DisjointSet disjointSet = new DisjointSet( vertices.size(), true );

		List<Edge<Character>> mstEdges = new ArrayList<>();

		while ( !edgePriorityQueue.isEmpty() && disjointSet.getNumberComponents() > 1 ) {
			Edge<Character> currentEdge = edgePriorityQueue.poll();

			Integer vertex1 = verticesMap.get( currentEdge.getNode1() );
			Integer vertex2 = verticesMap.get( currentEdge.getNode2() );

			int root1 = disjointSet.find( vertex1 );
			int root2 = disjointSet.find( vertex2 );

			if ( root1 != root2 ) {
				mstEdges.add( currentEdge );
				disjointSet.union( vertex1, vertex2 );
			}
		}

		if ( disjointSet.getNumberComponents() == 1 ) {
			return mstEdges;
		} else {
			// multiple components found no route connects all the nodes
			return new ArrayList<>();
		}
	}

}
