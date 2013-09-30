package com.loopd;

import com.google.inject.Singleton;

@Singleton
public class Logger {

	public void debug( String format, Object... objects ) {
		System.out.println( String.format( format, objects ) );
	}

	public void error( Throwable e ) {
		System.err.println( e.getMessage() );
		e.printStackTrace();
		if ( e.getCause() != null ) {
			System.err.println( "Caused By:" );
			e.getCause().printStackTrace();
		}
	}

}
