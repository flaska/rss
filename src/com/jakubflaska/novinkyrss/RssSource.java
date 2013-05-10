package com.jakubflaska.novinkyrss;

import java.util.ArrayList;

public class RssSource {
	ArrayList<RssFeed> iListFeeds;
	String iSourceName;
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

