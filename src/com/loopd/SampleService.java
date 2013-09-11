package com.loopd;

import com.google.inject.Singleton;

@Singleton
public class SampleService {

	public void blah() {
		System.out.println( "Hello Guice" );
	}

}
