package com.jakubflaska.novinkyrss;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RssListActivity extends Activity {
	static Integer iFeedCount = 0;
	private Handler iHandler = new Handler();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.InitGui();
		this.StartDeliveringFeeds();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void StartDeliveringFeeds(){
		/*ArrayList<String> listUrl = new ArrayList<String>();
		listUrl.add(new String("http://www.novinky.cz/rss2/"));
		listUrl.add(new String("http://idnes.cz.feedsportal.com/c/34387/f/625936/index.rss"));
		listUrl.add(new String("http://www.ceskatelevize.cz/ct24/rss/hlavni-zpravy/"));
		RssSourceManager sourceManager = new RssSourceManager(this,listUrl);*/
		ArrayList<RssSourceAddress> list = new ArrayList<RssSourceAddress>();
		RssSourceAddress address = new RssSourceAddress("ct24",this.getString(R.string.ct24));
		list.add(address);
		RssSourceManager sourceManager = new RssSourceManager(this,list);
		sourceManager.obtainFeeds();		
	}
	
	public void InitGui(){
		setContentView(R.layout.activity_main);
		InterfaceManager.GetInstance().SetMainActivityPointer(this);		
	}
	
	public Handler GetHandler(){
		return iHandler;
	}
}

