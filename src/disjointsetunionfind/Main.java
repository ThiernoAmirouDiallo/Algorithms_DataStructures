package disjointsetunionfind;

public class Main {

	public static void main( String[] args ) {
		// Without path compression
		DisjointSet ds = new DisjointSet( 10 );
		System.out.println( "Initial state" );
		System.out.println( ds.getS() );
		System.out.println( ds.getRoots() );
		ds.union( 0, 3 );
		ds.union( 1, 2 );
		ds.union( 2, 3 );
		ds.union( 4, 5 );
		ds.union( 4, 6 );
		ds.union( 6, 7 );
		ds.union( 8, 9 );
		System.out.println( "\nWithout path compression" );
		System.out.println( ds.getS() );
		System.out.println( ds.getRoots() );
		for ( int i = 0; i < 10; i++ ) {
			ds.find( i ); // this will not change the state of the disjoint set
		}
		System.out.println( "find(x) has been called for all x" );
		System.out.println( ds.getS() );
		System.out.println( ds.getRoots() );

		// With path compression
		DisjointSet ds2 = new DisjointSet( 10, true );
		ds2.union( 0, 3 );
		ds2.union( 1, 2 );
		ds2.union( 2, 3 );
		ds2.union( 4, 5 );
		ds2.union( 4, 6 );
		ds2.union( 6, 7 );
		ds2.union( 8, 9 );
		System.out.println( "\nWith path compression" );
		System.out.println( ds2.getS() );
		System.out.println( ds2.getRoots() );
		for ( int i = 0; i < 10; i++ ) {
			ds2.find( i ); // this will change the state of the disjoint set
		}
		System.out.println( "find(x) has been called for all x" );
		System.out.println( ds2.getS() );
		System.out.println( ds2.getRoots() );
	}

}
