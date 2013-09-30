package com.loopd;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.loopd.data.MediaItem;
import com.loopd.data.Serializer;

public class Test {
	private static Injector injector;

	@Inject
	private SampleService svc;

	@Inject
	private Logger log;
	@Inject
	private Serializer serializer;

	public Test() {
		injector.injectMembers( this );
	}

	public static void main( String args[] ) {
		injector = Guice.createInjector( new LoopdGuiceModule() );
		testSerializer();
	}

	public static void testSerializer() {
		Test t = new Test();
		try {
			t.serializer.testMemcached();
			t.serializer.testMongo();

			MediaItem item = new MediaItem();
			item.setThumbsUp( 15 );
			item.setThumbsDown( 2 );
			item.setType( "Video" );
			item.setUrl( "http://www.youtube.com/asdfa" );
			t.serializer.saveOrUpdate( item );

			long start = System.currentTimeMillis();
			for ( int i = 0; i < 10000; i++ ) {
				t.serializer.getMediaItemByUrl( item.getUrl() );
			}
			long elapsed = System.currentTimeMillis() - start;
			t.log.debug( "Elapsed: %s", elapsed );
		} catch ( Exception e ) {
			t.log.error( e );
		}
	}

	public static void testGuice() {
		Test t = new Test();
		t.svc.blah();
	}

}
