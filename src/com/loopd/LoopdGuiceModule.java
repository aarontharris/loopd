package com.loopd;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;

public class LoopdGuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		try {
			{ // Gson
				bind( Gson.class ).toInstance( new Gson() );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			throw new IllegalStateException( "Guice Initialization Failed" );
		}
	}

}
