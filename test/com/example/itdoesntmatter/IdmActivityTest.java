package com.example.itdoesntmatter;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.widget.TextView;

import com.example.itdoesntmatter.IdmActivity;
import com.example.itdoesntmatter.R;
import com.example.itdoesntmatter.model.Trend;
import com.example.itdoesntmatter.services.BroadcastService;
import com.example.itdoesntmatter.services.BroadcastServiceTest;


@RunWith(RobolectricTestRunner.class)
public class IdmActivityTest {

	 @Test
	 public void shouldHaveHappySmiles() throws Exception {
		    IdmActivity idmActivity = new IdmActivity();
		    idmActivity.onCreate(null);
		    TextView txt1 = (TextView)idmActivity.findViewById(R.id.textView1);
		    Assert.assertNotNull(txt1);
	        String hello = String.valueOf(txt1.getText());
	        String expected = "Hello world!";
	        Assert.assertEquals("Should be default hello world",expected, hello);
	        
	       
	        
	    }
	 
	 /**
	  * This is an example of a test testng the Android framework, imo
	  */
	 @Test
	 public void broadcastReceiverShouldGetInvoked(){
		 IdmActivity idmActivity = new IdmActivity();
		
		    
		    
		    BroadcastReceiver receiver = new BroadcastReceiver(){

				@Override
				public void onReceive(Context context, Intent intent) {
					System.out.println("Comes in here");
					Assert.assertNotNull(intent.getParcelableArrayListExtra("trends"));
					
				}
		    	
		    };
		    idmActivity.registerReceiver(receiver, new IntentFilter(BroadcastService.BROADCAST_ACTION));
		    
		    Intent intent = new Intent(BroadcastService.BROADCAST_ACTION);	
		    intent.putParcelableArrayListExtra("trends", (ArrayList<? extends Parcelable>)BroadcastServiceTest.createSomeMockTrends());
		    ShadowApplication shadowApplication = Robolectric.shadowOf(idmActivity.getApplication());
		    shadowApplication.sendBroadcast(intent);
		   
	 }
	 /**
	  * Testing business logic 
	  */
	 @Test
	 public void updateUIwithData(){
		 IdmActivity idmActivity = new IdmActivity();
		 idmActivity.onCreate(null);
		 Intent intent = new Intent(BroadcastService.BROADCAST_ACTION);	
		 List<Trend> mockTrends = BroadcastServiceTest.createSomeMockTrends();
		    intent.putParcelableArrayListExtra("trends", (ArrayList<? extends Parcelable>)mockTrends);
		 idmActivity.updateUI(intent);
		 TextView tx1 = (TextView)idmActivity.findViewById(R.id.textView1);  
		 Assert.assertEquals(mockTrends.get(0).getName(),tx1.getText());
	 }
}
