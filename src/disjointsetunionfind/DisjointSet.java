package disjointsetunionfind;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * DisjointSet or UnionFind data structure implementation
 */
public class DisjointSet {

	/**
	 * Array of elements
	 * s[x] < 0 if x is a root
	 * s[x] = y if x is not a root and y is its parent
	 * ABS( s[x] ) equals number of elements in the tree if x is the root
	 */
	private int[] s;
	private HashSet<Integer> roots = new HashSet<>(); // set of roots
	private boolean pathCompression = false;


	/**
	 * Constructor
	 * @param n number of elements
	 */
	public DisjointSet( int n ) {
		s = new int[n];
		for ( int i = 0; i < n; i++ ) {
			s[i] = -1;
			roots.add( i );
		}
	}

	/**
	 * Constructor
	 * @param n number of elements
	 * @param pathCompression true if path compression is used
	 */
	public DisjointSet( int n, boolean pathCompression ) {
		this( n );
		this.pathCompression = pathCompression;
	}

	public int find( int x ) {
		if ( s[x] < 0 ) {
			return x;
		} else {
			if ( pathCompression ) {
				s[x] = find( s[x] );
			}
			return s[x];
		}
	}

	/**
	 * Union two trees
	 * @param root1 root of the first tree
	 * @param root2 root of the second tree
	 */
	public void union( int root1, int root2 ) {
		int r1 = find( root1 );
		int r2 = find( root2 );
		if ( r1 != r2 ) { // not in the same tree, so we can union them
			if ( s[r2] < s[r1] ) { // r2 has larger tree
				s[r2] += s[r1]; // add size of r1 to r2
				s[r1] = r2; // r2 is the new root
				roots.remove( r1 ); // r1 is not a root anymore
			} else { // r1 has larger tree
				s[r1] += s[r2]; // add size of r2 to r1
				s[r2] = r1; // r1 is the new root
				roots.remove( r2 ); // r2 is not a root anymore
			}
		}
	}

	String getS() {
		return Arrays.stream( s ).boxed().map( String::valueOf ).collect( Collectors.joining( ", " ) );
	}

	String getRoots() {
		return roots.stream().map( i -> String.format( "%s: size(%s)", i, Math.abs( s[i] ) ) ).collect( Collectors.joining( ", " ) );
	}

}
