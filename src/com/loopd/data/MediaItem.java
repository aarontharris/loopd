package com.loopd.data;

public class MediaItem {

	private String url;
	private String type;
	private int thumbsUp;
	private int thumbsDown;

	// FIXME: how to store potentially millions of tags and their count?
	// maybe store them in another collection and use the databse to only pull the top 10 or something?
	// maybe 1 collection per media item? does that end up being too many collections?
	// maybe each collection is hashed somehow to produce an approximate fixed number of items.
	// Sounds like many many collections isn't a performance problem but could be a size problem: http://docs.mongodb.org/manual/core/data-modeling/
	// FIXME: will need some indexes: http://docs.mongodb.org/manual/administration/indexes/ 
	
	public String getUrl() {
		return url;
	}

	public void setUrl( String url ) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}

	public int getThumbsUp() {
		return thumbsUp;
	}

	public void setThumbsUp( int thumbsUp ) {
		this.thumbsUp = thumbsUp;
	}

	public int getThumbsDown() {
		return thumbsDown;
	}

	public void setThumbsDown( int thumbsDown ) {
		this.thumbsDown = thumbsDown;
	}

	@Override
	public String toString() {
		return "MediaItem [url=" + url + ", type=" + type + ", thumbsUp=" + thumbsUp + ", thumbsDown=" + thumbsDown + "]";
	}

}
