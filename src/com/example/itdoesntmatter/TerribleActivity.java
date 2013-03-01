package com.example.itdoesntmatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.itdoesntmatter.model.Trend;
import com.example.itdoesntmatter.services.BroadcastService;
import com.example.itdoesntmatter.services.FuturesTwitterService;


public class TerribleActivity extends Activity {
	
	private Intent intent;
	private static final String TAG = "TerribleActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idm);
		intent = new Intent(this,BroadcastService.class);
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
		stopService(intent); 		
	}
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	Log.v(TAG, "Received intent..updating ui");
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

		
    }; 

  
    
    public void findContacts(final View aButton){
    	Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    	
    	startActivity(intent);
    }
    
    public void getCurrentTrends(final View aButton){
    	//startService(intent);
    	Intent broadcastIntent = new Intent(BroadcastService.BROADCAST_ACTION);
    	new TwitterTrendTask(this,broadcastIntent).execute();
    }
    
    private class TwitterTrendTask extends AsyncTask<String, Void, String> {
		
		private Intent broadcastIntent;
		private List<Trend> parsedTrends;
		private Context context;
		
		public TwitterTrendTask(final Context aContext,final Intent anIntent){
			this.context = aContext;
			this.broadcastIntent = anIntent;
		}
		@Override
		protected String doInBackground(String... params) {
			Log.v(TAG, "Making call in the background");
			StringBuffer builder = new StringBuffer();
			try{
			final HttpClient client = new DefaultHttpClient();
		    final HttpGet httpGet = new HttpGet(FuturesTwitterService.TWITTER_TRENDS_URL);
		    HttpResponse response = client.execute(httpGet);
			
			StatusLine statusLine = response.getStatusLine();
		      int statusCode = statusLine.getStatusCode();
		      if (statusCode == 200) {
		        HttpEntity entity = response.getEntity();
		        InputStream content = entity.getContent();
		        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		        String line;
		        while ((line = reader.readLine()) != null) {
		          builder.append(line);
		        }
		      } else {
		        Log.e("TwitterService", "Response code " + statusCode);
		      }
		    } catch (ClientProtocolException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
			Log.v(TAG, "Parsing JSON....");
			parsedTrends = parseTrends(builder.toString());
			Log.v(TAG,"No of trends " + parsedTrends.size());
			
			return "OK";
		}
		
		@Override
	    protected void onPostExecute(String result) {
			Log.d(TAG,"In post execute...");
			broadcastIntent.putParcelableArrayListExtra("trends", (ArrayList<? extends Parcelable>)parsedTrends);
			context.sendBroadcast(broadcastIntent);
			
	      
	    }
		
		public List<Trend> parseTrends(String jsonString){
			List<Trend> trends = new ArrayList<Trend>();
			try {
				JSONArray jsonArray = new JSONArray(jsonString);
				for (int i = 0; i < jsonArray.length(); i++) {
			        JSONObject jsonObject = jsonArray.getJSONObject(i);
			        JSONArray trendsArray = jsonObject.getJSONArray("trends");
			        for(int j=0;j<trendsArray.length();j++){
			        	JSONObject trending = trendsArray.getJSONObject(j);
			        	String promoted = trending.getString("promoted_content");
			        	boolean isPromoted = !promoted.equals("null");
			        	Trend trend = new Trend(trending.getString("name"),isPromoted);
			        	trends.add(trend);
			        }
			      }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
			return trends;
		}
		
	}

}
