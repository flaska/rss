package com.jakubflaska.novinkyrss;

import java.util.ArrayList;

import android.widget.TextView;

class RssSourceDownloadContentThread extends Thread {
    String iInputUrl;
    RssSource iSource;
    RssSourceDownloadContentThread(String inputUrl) {
        this.iInputUrl = inputUrl;
    }
    public void run() {
    	System.out.println("Thread ran: "+iInputUrl);
		RssSourceFile s = new RssSourceFile(iInputUrl);
		s.DownloadXmlFileContent();
		if (s.getResult()){
			String xmlFile = s.GetXmlFileContent();			
			iSource = this.feedSource(xmlFile);
			InterfaceManager.GetInstance().RenderSource(iSource);
		}
    }
    public String getSourceString(int index){
    	return iSource.getiListFeeds().get(index).getiFeedContent();
    }
    RssSource feedSource(String xmlFile){		
		RssParser parser = new RssParser(xmlFile);
		parser.parseRssInputFile();
		ArrayList<RssFeed> listFeeds = parser.getRssFeedsList();
		RssSource source = new RssSource();		
		source.setiListFeeds(listFeeds); 
		return source;
    }
}

public class RssSourceManager {
	ArrayList<String> iListInputUrl;
	ArrayList<RssSourceDownloadContentThread> iSources = new ArrayList<RssSourceDownloadContentThread>();
	public RssSourceManager(ArrayList<String> listInputUrls){
		iListInputUrl = listInputUrls;
	}
	public void obtainFeeds(){
		for (String url : iListInputUrl){
			RssSourceDownloadContentThread thread = new RssSourceDownloadContentThread(url);
			iSources.add(thread);
			thread.start();
		}		
	}	
	public String getContentOfSource(int indexsource, int indexfeed){
		return iSources.get(indexsource).getSourceString(indexfeed);
	}
}

