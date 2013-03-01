package com.example.itdoesntmatter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.itdoesntmatter.model.Trend;
import com.example.itdoesntmatter.util.JSONUtil;

public class FuturesTwitterService implements TrendService {
	public static final String TWITTER_TRENDS_URL = "https://api.twitter.com/1/trends/1.json";
	private static final String TAG = "Futurez";
	@Override
	public List<Trend> getAllTrends() {
		return doGetAllTrends();
	}

	protected List<Trend> doGetAllTrends() {
		Callable<String> xy = new FetchTwitterTrendTask();
		Future<String> jsonStringFuture = executor.submit(xy);
		try {
			return JSONUtil.parseTrends(jsonStringFuture.get());
		} catch (InterruptedException e) {
			Log.e(TAG, e.getLocalizedMessage());
		} catch (ExecutionException e) {
			Log.e(TAG,e.getMessage());
		}
		Log.v(TAG, "Done processing");
		executor.shutdown();
		return new ArrayList<Trend>();
	}

	@Override
	public List<Trend> getNonPromotedTrends() {
		return filterPromoted(doGetAllTrends());
	}
	
	protected List<Trend> filterPromoted(List<Trend> trends) {
		List<Trend> popularOnly = new ArrayList<Trend>();
		for(Trend trend : trends){
			if(!trend.isPromoted())
				popularOnly.add(trend);
		}
		return popularOnly;
	}
	
	Intent anIntent;
	ExecutorService executor;
	Context context;
	public FuturesTwitterService(final Context aContext){
		
		
			this.context = aContext;
			executor = Executors.newFixedThreadPool(2);
			
			
		
	}
	

}
