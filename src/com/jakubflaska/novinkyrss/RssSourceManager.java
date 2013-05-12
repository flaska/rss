package com.jakubflaska.novinkyrss;

import java.util.ArrayList;
import java.util.Hashtable;

import android.widget.TextView;

class RssSourceDownloadContentThread extends Thread {
	RssSourceAddress iInputUrl;
    RssSource iSource;
    RssSourceDownloadContentThread(RssSourceAddress inputUrl) {
        this.iInputUrl = inputUrl;
    }
    public void run() {
    	System.out.println("Thread ran: "+iInputUrl);
		RssSourceFile s = new RssSourceFile(iInputUrl.getAddress());
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
		RssSource source = new RssSource(iInputUrl.getLogoFilename(),iInputUrl.getAddress());		
		source.setiListFeeds(listFeeds); 
		return source;
    }
}

public class RssSourceManager {
	ArrayList<RssSourceAddress> iSourceAddresses;
	ArrayList<RssSourceDownloadContentThread> iSources = new ArrayList<RssSourceDownloadContentThread>();
	RssListActivity iMainActivity;
	public RssSourceManager(RssListActivity activity, ArrayList<RssSourceAddress> listInputUrls){
		iMainActivity = activity;
		iSourceAddresses = listInputUrls;
	}
	public void obtainFeeds(){
		for (RssSourceAddress adress : iSourceAddresses){
			RssSourceDownloadContentThread thread = new RssSourceDownloadContentThread(adress);
			iSources.add(thread);
			thread.start();			
		}
	}	
	public String getContentOfSource(int indexsource, int indexfeed){
		return iSources.get(indexsource).getSourceString(indexfeed);
	}
}

