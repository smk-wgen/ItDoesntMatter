package com.example.itdoesntmatter.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class FetchTwitterTrendTask implements Callable<String> {

	private static final String TAG = "FetchTwitterTrendsTask";
	
	@Override
	public String call() throws Exception {
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
		return builder.toString();
	}

}
