package com.example.itdoesntmatter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.content.Intent;

import com.example.itdoesntmatter.model.Trend;
@RunWith(RobolectricTestRunner.class)
public class FuturesTwitterServiceTest {

	@Mock
	Context mockContext;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	FuturesTwitterService twitterService = new FuturesTwitterService(mockContext);
	@Test
	public void testFilterTrends(){
		List<Trend> trends = createTrends();
		List<Trend> filtered = twitterService.filterPromoted(trends );
		for(Trend filteredTrend : filtered){
			if(filteredTrend.isPromoted())
				Assert.fail();
		}
	}
	private List<Trend> createTrends() {
		// TODO Auto-generated method stub
		List<Trend> trends = new ArrayList<Trend>();
		Random r = new Random();
		for(int i=0;i<5;i++){
			Trend trend = new Trend(String.valueOf(i),r.nextBoolean());
			trends.add(trend);
		}
		return trends;
	}
}
