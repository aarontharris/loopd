package com.loopd.data;

import java.net.InetSocketAddress;
import java.util.Set;

import net.spy.memcached.MemcachedClient;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.loopd.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

@Singleton
public class Serializer {
	private static final int SECONDS_MIN = 60;
	private static final int SECONDS_HOUR = SECONDS_MIN * 60;
	private static final int SECONDS_DAY = SECONDS_HOUR * 24;
	private static final int SECONDS_7DAY = SECONDS_HOUR * 7;
	private static final int SECONDS_14DAY = SECONDS_DAY * 14;

	@Inject
	private Gson gson;
	@Inject
	private Logger log;

	private DB db;
	private MemcachedClient memc;

	@Inject
	private void init() {
		try {
			{ // MongoDB
				MongoClient mongoClient = new MongoClient( "127.0.0.1", 26017 );
				db = mongoClient.getDB( "loop" );
				if ( !db.authenticate( "loopuser", "looppass".toCharArray() ) ) {
					throw new IllegalStateException( "Not authorized to access the DB" );
				}
			}

			{ // Memcached
				memc = new MemcachedClient( new InetSocketAddress( "127.0.0.1", 11211 ) );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new IllegalStateException( "Guice Initialization Failed" );
		}
	}


	private String toKey( Class<?> clazz, String value ) {
		return clazz.getCanonicalName() + "_" + value;
	}

	private String getFromCache( String key ) {
		return (String) memc.get( key );
	}

	@SuppressWarnings( "unused" )
	private void putToCache( String key, int expiration, String jsonStr ) {
		memc.set( key, expiration, jsonStr );
	}

	@SuppressWarnings( "unused" )
	private void putToCache1D( String key, String jsonStr ) {
		memc.set( key, SECONDS_DAY, jsonStr );
	}

	@SuppressWarnings( "unused" )
	private void putToCache7D( String key, String jsonStr ) {
		memc.set( key, SECONDS_7DAY, jsonStr );
	}

	private void putToCache( String key, String jsonStr ) {
		memc.set( key, SECONDS_14DAY, jsonStr );
	}

	public String toJsonString( Object o ) {
		return gson.toJson( o );
	}

	public void saveOrUpdate( Object o ) throws Exception {
		try {
			String itemStr = gson.toJson( o );
			DBCollection collection = db.getCollection( "testCollection" );
			collection.insert( (DBObject) JSON.parse( itemStr ) );
		} catch ( Exception e ) {
			log.error( e );
		}
	}

	public MediaItem getMediaItemByUrl( String url ) throws Exception {
		String key = toKey( MediaItem.class, url );
		String value = null;

		value = getFromCache( key );
		if ( value != null ) {
			return gson.fromJson( value, MediaItem.class );
		}

		DBCollection collection = db.getCollection( "testCollection" );
		DBCursor cursor = collection.find( new BasicDBObject( "url", url ) );
		if ( cursor.hasNext() ) {
			String jsonStr = JSON.serialize( cursor.next() );
			putToCache( key, jsonStr );
			return gson.fromJson( jsonStr, MediaItem.class );
		}
		return null;
	}

	public void testMemcached() {
		try {
			String key = "someKey";
			String val = "someValue";
			memc.set( key, 3600, val );
			log.debug( "%s = %s", key, memc.get( key ) );
		} catch ( Exception e ) {
			log.error( e );
		}
	}

	public void testMongo() {
		try {
			Set<String> colls = db.getCollectionNames();
			for ( String s : colls ) {
				System.out.println( "Collection: " + s );
			}

			System.err.println( "You SHOULD get a Not Authorized Exception: in 3..2..1.." );
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
					System.err.println( "If you see a Not Authorized exception below, that is GOOD, Auth is working" );
					System.err.println( me.getMessage() );
				}
			}
		} catch ( Exception e ) {
			log.error( e );
		}
	}

}
