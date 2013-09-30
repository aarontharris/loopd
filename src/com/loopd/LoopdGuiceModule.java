package com.loopd;

import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class LoopdGuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		try {
			{ // Guice
				bind( Gson.class ).toInstance( new Gson() );
			}

			{ // MongoDB
				MongoClient mongoClient = new MongoClient( "127.0.0.1", 26017 );
				DB db = mongoClient.getDB( "loop" );
				if ( !db.authenticate( "loopuser", "looppass".toCharArray() ) ) {
					throw new IllegalStateException( "Not authorized to access the DB" );
				}
				bind( DB.class ).toInstance( db );
			}

			{ // Memcached
				MemcachedClient client = new MemcachedClient( new InetSocketAddress( "127.0.0.1", 11211 ) );
				bind( MemcachedClient.class ).toInstance( client );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new IllegalStateException( "Guice Initialization Failed" );
		}
	}

}
