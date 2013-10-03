package com.loopd.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BaseDao {

	@Inject
	private Serializer ser;

	public void saveMediaItem( MediaItem item ) throws Exception {
		ser.save( item );
	}

	public MediaItem getMediaItemByUrl( String url ) throws Exception {
		return ser.getObjectByKeyVal( MediaItem.class, "url", url );
	}

	public String getMediaItemJsonByUrl( String url ) throws Exception {
		return ser.getJsonByKeyVal( MediaItem.class, "url", url );
	}

	public MediaItem toMediaItemFromJson( String jsonString ) throws Exception {
		return ser.fromJsonString( jsonString, MediaItem.class );
	}

	public String toJsonFromMediaItem( MediaItem item ) throws Exception {
		return ser.toJsonString( item );
	}

}
