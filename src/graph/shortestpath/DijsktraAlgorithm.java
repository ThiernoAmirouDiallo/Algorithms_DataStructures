package graph.shortestpath;

import static graph.GraphBuilder.getGraph;

import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import graph.AdjacencyListGraph;
import graph.Edge;
import graph.GraphBuilder;

public class DijsktraAlgorithm {

	public static void main( String[] args ) {

		/**
		 * returns this graph
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
		System.out.println( "Dijkstra's shortest path" );
		System.out.println( "Single componet graph" );
		List<Edge<Character>> edges = GraphBuilder.getEdgeList();

		AdjacencyListGraph<Character> graph = getGraph( edges );
		List<Edge<Character>> dijkstrasShortestPath = getDijkstrasShortestPath( 'A', 'H', graph );
		Integer cost = dijkstrasShortestPath.stream().map( Edge::getWeight ).reduce( 0, Integer::sum );
		System.out.println( "Cost = " + cost + ", MST: " + dijkstrasShortestPath.stream().sorted( Comparator.comparing( Objects::toString ) ).collect( Collectors.toList() ) );

		System.out.println( "Graph with multiple components" );
		graph.addEdge( 'I', 'J', 9 );
		System.out.println( getDijkstrasShortestPath( 'A', 'I', graph ) );
	}

	public static List<Edge<Character>> getDijkstrasShortestPath( Character start, Character end, AdjacencyListGraph<Character> graph ) {
		int startVertexId = graph.getVerticesMap().get( start );
		int endVertexId = graph.getVerticesMap().get( end );

		// By default, the priority queue in Java is min Priority queue with natural ordering
		// PriorityQueue of edges represented by Pair.of( vertex, distance )
		PriorityQueue<Pair<Integer, Integer>> verticesPriorityQueue = new PriorityQueue<>( Comparator.comparing( pair -> pair.snd ) );

		// distance from joined vertices to this vertex if known
		Map<Integer, Integer> distance = new HashMap<>();

		// distance to start vertex = 0
		verticesPriorityQueue.add( Pair.of( startVertexId, 0 ) );
		distance.put( startVertexId, 0 );

		// initialize unknown distances to Integer.MAX_VALUE
		for ( int i = 1; i < graph.getNumOfNodes(); i++ ) {
			verticesPriorityQueue.add( Pair.of( i, Integer.MAX_VALUE ) ); // set max distance to vertex(i)
			distance.put( i, Integer.MAX_VALUE );
		}

		Map<Integer, Pair<Integer, Integer>> parentsMap = new HashMap<>(); // parentsMap of v(i) is pair v(j))


		boolean foundEndVertex = false; // if the reached the end vertex

		// repeat N times max
		while ( !verticesPriorityQueue.isEmpty() ) {
			Pair<Integer, Integer> currentVertex = verticesPriorityQueue.poll();

			// we found the end vertex we can stop here
			if ( endVertexId == currentVertex.fst && distance.get( endVertexId ) != Integer.MAX_VALUE ) {
				foundEndVertex = true;
				System.out.printf( "The shortest path distance from '%s' to '%s' is %s%n", start, end, distance.get( endVertexId ) );
			}

			// no edge to this vertex was discovered: multiple component graph -> no MST
			if ( currentVertex.snd == Integer.MAX_VALUE ) {
				break;
			}

			// update adjacent vertices distance if shorter paths exits
			graph.getNode( currentVertex.fst ).getAdjacentNodesListWithWeight().forEach( vertexWeightPair -> {
				if ( (distance.get( currentVertex.fst ) + vertexWeightPair.snd) < distance.get( vertexWeightPair.fst ) ) {
					// because we will update the distance, let's update the priority queue by removing and reinserting with the new distance
					verticesPriorityQueue.remove( Pair.of( vertexWeightPair.fst, distance.get( vertexWeightPair.fst ) ) );

					// update dist                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ance
					distance.put( vertexWeightPair.fst, distance.get( currentVertex.fst ) + vertexWeightPair.snd );
					verticesPriorityQueue.add( Pair.of( vertexWeightPair.fst, distance.get( vertexWeightPair.fst ) ) );

					// the parentsMap of the adjacent vertex is the current edge
					parentsMap.put( vertexWeightPair.fst, Pair.of( currentVertex.fst, vertexWeightPair.snd ) );
				}
			} );
		}

		if ( foundEndVertex ) {
			// compute edges
			return computePath( endVertexId, parentsMap, graph );
		} else {
			// no route connects all the nodes (multiple components found )
			return new ArrayList<>();
		}

	}

	private static List<Edge<Character>> computePath( int endVertexId, Map<Integer, Pair<Integer, Integer>> parents, AdjacencyListGraph<Character> graph ) {
		LinkedList<Edge<Character>> mstEdges = new LinkedList<>();
		int currentVertex = endVertexId;

		while ( parents.containsKey( currentVertex ) ) {
			Pair<Integer, Integer> parentWeight = parents.get( currentVertex );
			mstEdges.addFirst( new Edge<>( graph.getNode( parentWeight.fst ).getValue(), graph.getNode( currentVertex ).getValue(), parentWeight.snd ) );

			// update current vertex
			currentVertex = parentWeight.fst;
		}

		return mstEdges;
	}
}
