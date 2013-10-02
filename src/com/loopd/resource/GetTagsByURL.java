package com.loopd.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.loopd.GuiceResource;
import com.loopd.data.MediaItem;
import com.loopd.data.Serializer;

@Path( "getTagsByUrl" )
public class GetTagsByURL extends GuiceResource {

	@Inject
	private Serializer ser;

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public String doGet(
			// @DefaultValue( "" )
			@QueryParam( value = "url" ) final String url
			) {
		try {
			log().debug( "getTagsByUrl( '%s' )", url );
			MediaItem item = ser.getMediaItemByUrl( url );
			if ( item != null ) {
				return ser.toJsonString( item );
			}
			return "{}";
		} catch ( Exception e ) {
			log().error( e );
		}
		throw new WebApplicationException( Status.SERVICE_UNAVAILABLE );
	}
}
