package com.loopd.resource;

import java.net.URLDecoder;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.loopd.BaseResource;
import com.loopd.data.BaseDao;
import com.loopd.data.MediaItem;

@Path( "media" )
public class MediaItemResource extends BaseResource {

	@Inject
	private BaseDao dao;

	@GET
	@Path( "get" )
	@Produces( MediaType.APPLICATION_JSON )
	public String doGet(
			// @DefaultValue( "" )
			@QueryParam( value = "url" ) final String url
			) {
		try {
			log().debug( "%s( '%s' )", "getFromUrl", url );
			String jsonString = dao.getMediaItemJsonByUrl( url );
			if ( jsonString != null ) {
				return jsonString;
			}
			return "{}";
		} catch ( Exception e ) {
			log().error( e );
		}
		throw new WebApplicationException( Status.INTERNAL_SERVER_ERROR );
	}

	/**
	 * <pre>
	 * # data - expects a MediaItem as url-encoded json.
	 * # return - {} on success
	 * </pre>
	 */
	@POST
	@Path( "post" )
	@Produces( MediaType.APPLICATION_JSON )
	public String doPost( String data ) {
		try {
			String jsonString = URLDecoder.decode( data, "UTF-8" );
			log().debug( "%s( '%s' )", getClass().getSimpleName(), jsonString );
			MediaItem item = dao.toMediaItemFromJson( jsonString );
			dao.saveMediaItem( item );
			return "{}";
		} catch ( Exception e ) {
			log().error( e );
		}
		throw new WebApplicationException( Status.INTERNAL_SERVER_ERROR );
	}

}
