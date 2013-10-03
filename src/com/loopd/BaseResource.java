package com.loopd;

import com.google.inject.Inject;


public class BaseResource {

	@Inject
	private Logger log;

	protected Logger log() {
		return log;
	}

	public BaseResource() {
		Application.injectMembers( this );
		log.debug( "%s instantiated", getClass().getSimpleName() );
	}

}
