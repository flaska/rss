package com.jakubflaska.novinkyrss;

import java.util.ArrayList;
import java.util.Iterator;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RenderTextViewsRunnable implements Runnable {
	RssListActivity iMainActivity;
	RssSource iRssSource;
	int iNrRows;
	int iWindowWidth;
	int iBoxPadding = 10;
	int iHeight = 60;
	TableLayout iMainLayout;
	TableLayout iSourceLayout;
	public RenderTextViewsRunnable(RssListActivity mainAct,RssSource source, int width, int rownum){
		iMainActivity = mainAct;
		iRssSource = source;
		iNrRows = rownum;
		iWindowWidth = width;
		iMainLayout = (TableLayout) iMainActivity.findViewById(R.id.tableLayout);
		iSourceLayout = new TableLayout(iMainActivity);
	}
	void CreateViews(){
		
	}
	@Override
	public void run() {		
		ArrayList<TextView> textViews = new ArrayList<TextView>();
		for (RssFeed feed : iRssSource.getiListFeeds()) {
			TextView t = new TextView(iMainActivity);
			t.setText(feed.getiFeedTitle());
			textViews.add(t);
		}		
    	
    	Iterator<TextView> itr = textViews.iterator();
    	while (itr.hasNext()) {
    		TableRow row = new TableRow(iMainActivity);
    		//row.setPadding(10, 10, 10, 10);
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
    	iMainLayout.addView(iSourceLayout);
    }
    /*public void run() {		
		ArrayList<TextView> textViews = new ArrayList<TextView>();
		for (RssFeed feed : iRssSource.getiListFeeds()) {
			TextView t = new TextView(iMainActivity);
			t.setText(feed.getiFeedTitle());
			textViews.add(t);
		}		
    	
    	Iterator<TextView> itr = textViews.iterator();
    	while (itr.hasNext()) {
    		TableRow row = new TableRow(iMainActivity);
    		//row.setPadding(10, 10, 10, 10);
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
    	iMainLayout.addView(iSourceLayout);
    }*/
	
}