package com.example.itdoesntmatter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.itdoesntmatter.model.Trend;
import com.example.itdoesntmatter.services.BroadcastService;
import com.example.itdoesntmatter.services.TrendService;
import com.example.itdoesntmatter.services.TrendServiceFactory;

public class IdmActivity extends Activity {

	//private Intent intent;
	private static final String TAG = "IdmActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idm);
		//intent = new Intent(this,BroadcastService.class);
	}

	
	@Override
	public void onResume() {
		super.onResume();		
		//startService(intent);
		registerReceiver(broadcastReceiver, new IntentFilter(BroadcastService.BROADCAST_ACTION));
	}

	@Override
	public void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
		//stopService(intent); 		
	}
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	Log.v(TAG, "Received intent..updating ui");
        	updateUI(intent);       
        }

		
    }; 

    protected void updateUI(Intent intent){
    	TextView txt1 = (TextView) findViewById(R.id.textView1);
    	TextView txt2 = (TextView) findViewById(R.id.textView2);
    	TextView txt3 = (TextView) findViewById(R.id.textView3);
    	
    	List<Trend> trends = intent.getParcelableArrayListExtra("trends");
    	if(!trends.isEmpty()){
    		txt1.setText(trends.get(0).getName());
        	txt2.setText(trends.get(1).getName());
        	txt3.setText(trends.get(2).getName());
    	}
    	
    }
    
    public void findContacts(final View aButton){
    	Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    	
    	startActivity(intent);
    }
    /**
     * 
     * @param aButton
     */
    //TODO there is some refactoring to do. I initally used AysncTask to update the 
    public void getCurrentTrends(final View aButton){
    	TrendService trendService = TrendServiceFactory.getInstance(this,BroadcastService.BROADCAST_ACTION);
    	List<Trend> trends = trendService.getAllTrends();

    	Log.v(TAG, "Size of trends " + trends.size());
    	Intent updateUIIntent = new Intent(BroadcastService.BROADCAST_ACTION);
    	updateUIIntent.putParcelableArrayListExtra("trends", (ArrayList<? extends Parcelable>)trends);
		sendBroadcast(updateUIIntent);
    	//startService(intent);
    }
}
