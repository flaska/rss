package com.jakubflaska.novinkyrss;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
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
		Intent intent= getIntent(); // gets the previously created intent
		String logo = intent.getStringExtra("logo"); // will return "FirstKeyValue"
		String address = intent.getStringExtra("address"); // will return "SecondKeyValue"	
		ArrayList<RssSourceAddress> list = new ArrayList<RssSourceAddress>();
		list.add(new RssSourceAddress(logo,address));
		this.StartDeliveringFeeds(list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void StartDeliveringFeeds(ArrayList<RssSourceAddress> list){
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

