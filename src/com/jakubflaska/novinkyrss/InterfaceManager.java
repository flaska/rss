package com.jakubflaska.novinkyrss;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

class RandomColor {
	static Integer[] colors = {R.color.a, R.color.b, R.color.c, R.color.d, R.color.e};
	static Random random = new Random();
	static int LastColor;
	static public Integer GetRandomColor(){
		int color = colors[random.nextInt(5)];
		if (LastColor==color)
			return RandomColor.GetRandomColor();
		LastColor = color;
		return color;
	}
}



public class InterfaceManager {
	static InterfaceManager _instance=null;
	RssListActivity iMainActivity;
	Integer iColumnsNr;
	Integer iScreenWidth;
	static public void InitInstance(){
		_instance = new InterfaceManager(); 
	}
	public static InterfaceManager GetInstance(){
		if(_instance==null)
			InitInstance();
		return _instance;
	}
	public void SetMainActivityPointer(RssListActivity parent) {
		iMainActivity = parent;
	}
	
	public void GetScreenWidth(){
		WindowManager wm = (WindowManager) iMainActivity.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		iScreenWidth = display.getWidth();
		if (iScreenWidth<400)
			iColumnsNr = 1;
		else if (iScreenWidth<600)
			iColumnsNr = 2;		
		else if (iScreenWidth<800)
			iColumnsNr = 3;
		else if (iScreenWidth<1000)
			iColumnsNr = 4;
		else
			iColumnsNr = 5;
	}

	public void RenderSource(RssSource source){
	    final ArrayList<TextView> textViews = new ArrayList<TextView>();
	    this.GetScreenWidth();
		Handler handler = iMainActivity.GetHandler();
		RenderTextViewsRunnable runnable = new RenderTextViewsRunnable(iMainActivity, source, iScreenWidth, iColumnsNr);
		handler.post(runnable);
	}
	
	public void RenderSource(TableLayout layout){
		TableLayout mainlayout = (TableLayout) iMainActivity.findViewById(R.id.tableLayout);
		mainlayout.addView(layout);
	}
}