package com.usa.weatherforecast.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.usa.weatherforecast.service.WeatherService;
import com.usa.weatherforecast.utils.HelperUtil;
import com.usa.weatherforecast.service.TimeFrames;


@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {
	
	 @InjectMocks
	 WeatherService weatherService;
	 
	 @Mock
	 HelperUtil helper;
	 
	 @Before
	    public void init() {
	        MockitoAnnotations.initMocks(this);
	     
	       
	    }
	 
	
		
	@Test
	public void getWeatherDataTest() {
		 List<TimeFrames> timeFrames = new ArrayList<TimeFrames>();
			TimeFrames frame1 = new TimeFrames();
			frame1.setDate("12/10/2019");
			frame1.setTemp_f("54.7");
			frame1.setWx_desc("cloudy");
			timeFrames.add(frame1);
			timeFrames.add(frame1);
			timeFrames.add(frame1);
		 //for url 
		String app_Id = "bde4ecae";
		String app_key = "bf9ea0ec59dae4587e263ee0ffe74ce9";
		String zipCode = "19355";
		//json response test 
		String jsonResponse = "{Days:[]}";
        String urlstr = "http://api.weatherunlocked.com/api/forecast/us." + zipCode + "?app_id=" + app_Id + "&app_key=" + app_key + "";
		
		 try {
			 
			 when(helper.createUrl("19355")).thenReturn(new URL(urlstr));
				//test json response 
				
			when(helper.createHttpRequest(new URL(urlstr))).thenReturn(jsonResponse);
			
			
			 List<TimeFrames> timeFrames1 = weatherService.getWeatherData("19344");
			 assertEquals(timeFrames1, timeFrames1);
		      //create url and test return data 

		
		 } catch (IOException e) {
				
				e.printStackTrace();
			}
	}
	
	@Test
	public void getMinTimeFrameTest() {
		 List<TimeFrames> timeFrames = new ArrayList<TimeFrames>();
			TimeFrames frame1 = new TimeFrames();
			frame1.setDate("12/10/2019");
			frame1.setTemp_f("54.7");
			frame1.setWx_desc("cloudy");
			timeFrames.add(frame1);
			timeFrames.add(frame1);
			timeFrames.add(frame1);
			TimeFrames timeframe = weatherService.getMinTimeFrame(timeFrames);
		assertNotNull(timeframe);
	}
	

}
