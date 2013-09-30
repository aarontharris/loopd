package com.loopd.data;

public class MediaItem {

	private String url;
	private String type;
	private int thumbsUp;
	private int thumbsDown;

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
