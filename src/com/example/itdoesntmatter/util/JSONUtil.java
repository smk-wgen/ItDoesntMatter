package com.example.itdoesntmatter.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.itdoesntmatter.model.Trend;

public class JSONUtil {
	
	
	public static List<Trend> parseTrends(String jsonString){
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
			Log.e("JSONUtil", e.getMessage());
		}
		return trends;
	}

}
