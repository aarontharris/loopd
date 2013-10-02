package com.loopd.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.loopd.GuiceResource;
import com.loopd.data.Serializer;

@Path( "test" )
public class TestResource extends GuiceResource {

	@Inject
	private Serializer ser;

	@GET
	@Produces( MediaType.TEXT_PLAIN )
	public String doGet() {
		ser.testMemcached();
		ser.testMongo();
		return "1";
	}
}
