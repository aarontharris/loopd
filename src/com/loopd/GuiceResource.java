package com.loopd;

import com.google.inject.Inject;


public class GuiceResource {

	@Inject
	private Logger log;

	protected Logger log() {
		return log;
	}

	public GuiceResource() {
		Application.injectMembers( this );
	}

}
