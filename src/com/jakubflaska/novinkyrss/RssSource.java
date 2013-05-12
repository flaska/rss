package com.jakubflaska.novinkyrss;

import java.util.ArrayList;

public class RssSource extends RssSourceAddress {
	ArrayList<RssFeed> iListFeeds;
	String iSourceName;
	public RssSource (String logo, String address){
		super(logo,address);
	}
	public void setiSourceName(String source){
		iSourceName = source;
	}
	public void setiListFeeds(ArrayList<RssFeed> feeds){
		iListFeeds = feeds;
	}	
	public String getiSourceName(){
		return iSourceName;
	}
	public ArrayList<RssFeed> getiListFeeds(){
		return iListFeeds;
	}
}

