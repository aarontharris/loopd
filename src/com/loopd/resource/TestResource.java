package com.loopd.resource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
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
import com.loopd.data.Serializer;

@Path( "test" )
public class TestResource extends BaseResource {

	@Inject
	private Serializer ser;
	@Inject
	private BaseDao dao;

	@Path( "memcached" )
	@GET
	@Produces( MediaType.TEXT_PLAIN )
	public String doGetMemcached() {
		ser.testMemcached();
		return "1";
	}

	@Path( "mongo" )
	@GET
	@Produces( MediaType.TEXT_PLAIN )
	public String doGetMongo() {
		ser.testMongo();
		return "1";
	}

	@Path( "createMediaItem" )
	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public String doGet(
			@DefaultValue( value = "http://www.testdata.com" ) @QueryParam( value = "url" ) String url,
			@DefaultValue( value = "test" ) @QueryParam( value = "type" ) String type,
			@DefaultValue( value = "101" ) @QueryParam( value = "thumbsUp" ) String thumbsUp,
			@DefaultValue( value = "2" ) @QueryParam( value = "thumbsDown" ) String thumbsDown
			) {
		try {
			MediaItem item = new MediaItem();
			item.setUrl( url );
			item.setType( type );
			item.setThumbsUp( Integer.parseInt( thumbsUp ) );
			item.setThumbsDown( Integer.parseInt( thumbsDown ) );
			dao.saveMediaItem( item );
			return dao.toJsonFromMediaItem( item );
		} catch ( NumberFormatException e ) {
			throw new WebApplicationException( "NumberFormatException: I think you gave me an unparsable thumbsUp or thumbsDown" );
		} catch ( Exception e ) {
			log().error( e );
		}
		throw new WebApplicationException( Status.INTERNAL_SERVER_ERROR );
	}

}
