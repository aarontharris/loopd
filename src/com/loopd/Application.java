package com.loopd;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.inject.Guice;
import com.google.inject.Injector;

@WebListener
public class Application implements ServletContextListener {
	private static Injector injector;

	public void contextInitialized( ServletContextEvent event ) {
		injector = Guice.createInjector( new LoopdGuiceModule() );
	}

	public void contextDestroyed( ServletContextEvent event ) {
		// Do your thing during webapp's shutdown.
	}

	public static void injectMembers( Object o ) {
		if ( injector != null ) {
			injector.injectMembers( o );
		}
	}
}
