package com.jakubflaska.novinkyrss;

import java.util.ArrayList;
import java.util.Iterator;

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
	int iHeight = 60;
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
			textViews.add(t);
		}			
	}
	void AddLogo(){
		ImageView logo = new ImageView(iMainActivity);
		logo.setImageResource(iMainActivity.getResources().getIdentifier(iRssSource.getLogoFilename(), "drawable","com.jakubflaska.novinkyrss"));
		iSourceLayout.addView(logo);
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