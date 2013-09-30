package com.loopd;

import java.net.InetSocketAddress;
import java.util.Set;

import net.spy.memcached.MemcachedClient;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class Test {
	private static Injector injector;

	@Inject
	private SampleService svc;

	public Test() {
		injector.injectMembers( this );
	}

	public static void main( String args[] ) {
		injector = Guice.createInjector( new LoopdGuiceModule() );
		testGuice();
		testMongoDB();
		testMemcached();
	}

	public static void testGuice() {
		Test t = new Test();
		t.svc.blah();
	}

	public static void testMemcached() {
		try {
			// anything further than 30 days from now will instead
			// be interpreted as unixtime -- seconds since Jan 1 1970.
			int maxTimeFromNow = 60 * 60 * 24 * 30;

			MemcachedClient c = new MemcachedClient( new InetSocketAddress( "127.0.0.1", 11211 ) );
			String key = "someKey";
			c.set( key, 3600, "somevalue" );

			String value = (String) c.get( key );
			System.out.println( String.format( "'%s'='%s'", key, value ) );

		} catch ( Exception e ) {
			System.err.println( e.getMessage() );
			e.printStackTrace();
		}
	}

	public static void testMongoDB() {
		try {
			// JsonObject obj = new JsonObject();
			// obj.addProperty( "key1", "value1" );
			// System.out.println( obj.toString() );

			// To directly connect to a single MongoDB server (note that this will not auto-discover the primary even
			// if it's a member of a replica set:
			MongoClient mongoClient = new MongoClient( "127.0.0.1", 26017 );

			DB db = mongoClient.getDB( "loop" );

			// db.addUser( { user: "loopuser", pwd: "macmacmac", roles: [ "readWrite" ] } )
			boolean auth = db.authenticate( "loopuser", "looppass".toCharArray() );

			Set<String> colls = db.getCollectionNames();
			for ( String s : colls ) {
				System.out.println( "Collection: " + s );
			}

			for ( String collectionName : db.getCollectionNames() ) {
				try {
					DBCollection collection = db.getCollection( collectionName );
					DBCursor cursor = collection.find();
					while ( cursor.hasNext() ) {
						DBObject obj = cursor.next();
						for ( String key : obj.keySet() ) {
							System.out.println( String.format( "%s.%s='%s'", collectionName, key, obj.get( key ) ) );
						}
					}
				} catch ( MongoException me ) {
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
