package graph;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Edge<T> {

	T node1;
	T node2;

	int weight;

	public Edge( T node1, T node2, int weight ) {
		this.node1 = node1;
		this.node2 = node2;
		this.weight = weight;
	}

	public T getNode1() {
		return node1;
	}

	public void setNode1( T node1 ) {
		this.node1 = node1;
	}

	public T getNode2() {
		return node2;
	}

	public void setNode2( T node2 ) {
		this.node2 = node2;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight( int weight ) {
		this.weight = weight;
	}

	@Override
	public boolean equals( Object o ) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Edge) ) {
			return false;
		}
		Edge edge = (Edge) o;

		// because undirected graph A->B = B->A
		return getWeight() == edge.getWeight() && //
				((getNode1() == edge.getNode1() && getNode2() == edge.getNode2()) || //
						(getNode1() == edge.getNode2() && getNode2() == edge.getNode1()));
	}

	@Override
	public int hashCode() {
		return Objects.hash( getNode1(), getNode2(), getWeight() );
	}

	@Override
	public String toString() {
		return "Edge{" + Stream.of( node1, node2 ).map( Object::toString ).sorted().collect( Collectors.joining( "->" ) ) + ", w=" + weight + '}';
	}
}