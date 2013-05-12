package com.jakubflaska.novinkyrss;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RenderTextViewsRunnable implements Runnable {
	RssListActivity iMainActivity;
	RssSource iRssSource;
	int iNrRows;
	int iWindowWidth;
	int iBoxPadding = 10;
	int iHeight = 70;
	TableLayout iMainLayout;
	TableLayout iSourceLayout;
	ArrayList<TextView> textViews = new ArrayList<TextView>();
	ImageView iLogoImageView;
	public RenderTextViewsRunnable(RssListActivity mainAct,RssSource source, int width, int rownum){
		iMainActivity = mainAct;
		iRssSource = source;
		iNrRows = rownum;
		iWindowWidth = width;
		iMainLayout = (TableLayout) iMainActivity.findViewById(R.id.tableLayout);
		iSourceLayout = new TableLayout(iMainActivity);
		this.AddLogo();
	}
	void CreateTextViews(){
		for (RssFeed feed : iRssSource.getiListFeeds()) {
			TextView t = new TextView(iMainActivity);
			t.setText(feed.getiFeedTitle());
			this.SetLink(t, feed);
			textViews.add(t);
		}			
	}
	void AddLogo(){
		TextView logo = new TextView(iMainActivity);
		logo.setText(iRssSource.getLogoFilename());
		logo.setPadding(20, 10, 20, 10);
		iSourceLayout.addView(logo);
	}
	void SetLink(TextView view, final RssFeed feed){
		view.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	Uri uriUrl = Uri.parse(feed.getiFeedLink()); 
		        Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
		        iMainActivity.startActivity(intent);				
		    }
		});	
	}
	void OrganizeTextViewsIntoTable(){
    	Iterator<TextView> itr = textViews.iterator();
    	while (itr.hasNext()) {
    		TableRow row = new TableRow(iMainActivity);
    		for (Integer i=0;i<iNrRows&&itr.hasNext();i++){
    			TextView w = itr.next();
    			w.setWidth(iWindowWidth/iNrRows);
    			w.setHeight(iHeight);
    			w.setPadding(iBoxPadding, iBoxPadding, iBoxPadding, iBoxPadding);
    			w.setTextColor(iMainActivity.getResources().getColor(R.color.white));
    			w.setBackgroundResource(RandomColor.GetRandomColor());
    			row.addView(w);	
    		}
    		iSourceLayout.addView(row);
    	}		
	}	
	@Override
	public void run() {		
		this.CreateTextViews();
		this.OrganizeTextViewsIntoTable();
		InterfaceManager.GetInstance().RenderSource(iSourceLayout);
    }
}