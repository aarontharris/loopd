package com.loopd.resource;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.loopd.GuiceResource;

@Path( "urlEncode" )
public class UrlEncode extends GuiceResource {

	/**
	 * <pre>
	 * USAGE1: api/urlEncode?value='value to encode'&encoding=UTF-8
	 * USAGE2: api/urlEncode?value='value to encode'
	 * PARAM1: <b>value</b> is required and must be contained within single-quotes.
	 * PARAM2: <b>encoding</b> (optional), UTF-8 is default.
	 * </pre>
	 */
	@GET
	@Produces( MediaType.TEXT_PLAIN )
	public String doGet(
			@Context HttpServletRequest httpRequest,
			@QueryParam( value = "value" ) final String unused,
			@DefaultValue( "UTF-8" ) @QueryParam( value = "encoding" ) final String encoding
			) {
		try {
			log().debug( "query: %s", httpRequest.getQueryString() );
			
			String[] parts = httpRequest.getQueryString().split( "'" );
			String actualValue = parts[1];

			log().debug( "urlEncode( '%s', '%s' )", actualValue, encoding );
			String encoded = actualValue;
			encoded = URLEncoder.encode( actualValue, encoding );
			return encoded;
		} catch ( Exception e ) {
			log().error( e );
		}
		throw new WebApplicationException( Status.SERVICE_UNAVAILABLE );
	}
}
