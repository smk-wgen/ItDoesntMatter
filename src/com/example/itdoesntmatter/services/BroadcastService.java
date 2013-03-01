package com.example.itdoesntmatter.services;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;

import com.example.itdoesntmatter.model.Trend;
import com.google.inject.Inject;


public class BroadcastService extends Service {

	private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "com.example.itdoesntmatter.displaytrends";
    private final Handler handler = new Handler();
    protected Intent intent;
    int counter = 0;
    private Context context;
    
   
    
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "In onCreate");
    	intent = new Intent(BROADCAST_ACTION);	
    	
    }
	
	@Override
    public void onStart(Intent intent, int startId) {
		System.out.println("In on start");
		context = getApplicationContext();
		handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 5000); // 5 second   
    }
	
	private Runnable sendUpdatesToUI = new Runnable() {
    	public void run() {
    		System.out.println("InRun");
    		Log.d(TAG, "entered place where we call the tweets and get results");
    		
    				assert context != null;
					TrendService trendService = TrendServiceFactory.getInstance(context,null);
					
					trendService.getAllTrends();
					
					handler.postDelayed(this, 10000); // 10 seconds
				
    		
    		
    		
    	    
    	}
    }; 

}
