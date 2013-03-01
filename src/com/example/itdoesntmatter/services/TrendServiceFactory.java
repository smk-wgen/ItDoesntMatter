package com.example.itdoesntmatter.services;

import android.content.Context;

public class TrendServiceFactory {
	
	private static TrendService trendService;
	
	public static void setInstance(TrendService aTrendService){
		trendService = aTrendService;
	}
	
	public static TrendService getInstance(Context aContext,String action){
		if(trendService == null){
			return new FuturesTwitterService(aContext);
		}
		return trendService;
	}

}
