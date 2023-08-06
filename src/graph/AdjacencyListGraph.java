package graph;

import com.sun.tools.javac.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AdjacencyListGraph<T> {

	private Node<T>[] nodes;

	HashMap<T, Integer> verticesMap = new HashMap<>();
	AtomicInteger nodeIdCounter = new AtomicInteger( 0 );

	public AdjacencyListGraph( int initialCapacity ) {
		this.nodes = new Node[Math.max( initialCapacity, 2 )]; // in order to add the 2 vertices linked by the first edge
		fillNewNodes( 0, initialCapacity );
	}

	private void fillNewNodes( int from, int toExcluding ) {
		for ( int i = from; i < toExcluding; i++ ) {
			this.nodes[i] = new Node<>( i );
		}
	}

	public Node<T> getNode( int i ) {
		return nodes[i];
	}

	public void addEdge( T node1, T node2 ) {
		addEdge( node1, node2, 1 );
	}

	public void addEdge( T node1, T node2, int weight ) {
		// create new mappings if do not exist
		int node1Id = getId( node1 );
		int node2Id = getId( node2 );

		// nodes array is full: double it size
		if ( verticesMap.size() == nodes.length ) {
			int currentCapacity = nodes.length;
			int newCapacity = currentCapacity * 2;
			nodes = Arrays.copyOf( nodes, newCapacity );
			fillNewNodes( currentCapacity, newCapacity );
		}

		nodes[node1Id].addEdge( node2Id, weight );
		if ( nodes[node1Id].value == null ) {
			nodes[node1Id].value = node1;
		}

		nodes[node2Id].addEdge( node1Id, weight );
		if ( nodes[node2Id].value == null ) {
			nodes[node2Id].value = node2;
		}
	}

	private Integer getId( T node ) {
		verticesMap.computeIfAbsent( node, t -> nodeIdCounter.getAndIncrement() );
		return verticesMap.get( node );
	}

	public void showEdges() {
		for ( int i = 0; i < verticesMap.size(); i++ ) {
			nodes[i].showEdges();
		}
	}

	public int getNumOfNodes() {
		return verticesMap.size();
	}

	public Map<T, Integer> getVerticesMap() {
		return verticesMap;
	}

	public Set<T> getVertices() {
		return verticesMap.keySet();
	}

	public static class Node<T> {

		public int getId() {
			return id;
		}

		private final int id;

		public T getValue() {
			return value;
		}

		private T value;

		// List of Pair.of( node, weight )
		private LinkedList<Pair<Integer, Integer>> adjacentNodesList = new LinkedList<>();

		public Node( int id ) {
			this.id = id;
		}

		public Node( int id, T value ) {
			this.id = id;
			this.value = value;
		}

		public void addEdge( int node, int weight ) {
			adjacentNodesList.add( Pair.of( node, weight ) );
		}

		public List<Integer> getAdjacentNodesList() {
			return adjacentNodesList.stream().map( integerIntegerPair -> integerIntegerPair.fst ).collect( Collectors.toList() );
		}

		public List<Pair<Integer, Integer>> getAdjacentNodesListWithWeight() {
			return adjacentNodesList;
		}

		@Override
		public boolean equals( Object o ) {
			if ( this == o ) {
				return true;
			}
			if ( !(o instanceof Node) ) {
				return false;
			}

			Node node = (Node) o;

			return id == node.id;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode( id );
		}

		public void showEdges() {
			System.out.print( "Node " + id + " is connected to: " );
			for ( Pair<Integer, Integer> integerPair : adjacentNodesList ) {
				System.out.printf( "%s(%s) ", integerPair.fst, integerPair.snd );
			}
			System.out.println();
		}
	}
}
