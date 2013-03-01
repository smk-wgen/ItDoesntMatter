package com.example.itdoesntmatter.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.example.itdoesntmatter.model.Trend;
@RunWith(RobolectricTestRunner.class)
public class BroadcastServiceTest {
	
	@Mock
	TrendService trendService;
	
	BroadcastService broadcastService = new BroadcastService();
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		TrendServiceFactory.setInstance(trendService);
		when(trendService.getAllTrends()).thenReturn(BroadcastServiceTest.createSomeMockTrends());
		broadcastService.onCreate();
		
	}


	
	

	public static List<Trend> createSomeMockTrends() {
		List<Trend> trends = new ArrayList<Trend>();
		trends.add(new Trend("trend1",true));
		trends.add(new Trend("trend2",false));
		trends.add(new Trend("trend3",false));
		return trends;
		
	}
	
	@Test
	public void createServiceShouldInitIntent(){

		Assert.assertNotNull(broadcastService.intent);
	}

}
