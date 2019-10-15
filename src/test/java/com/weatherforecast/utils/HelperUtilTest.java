package com.weatherforecast.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.usa.weatherforecast.utils.HelperUtil;

@RunWith(MockitoJUnitRunner.class)
public class HelperUtilTest {

	 @InjectMocks
	 HelperUtil helper;
	
	 @Before
	    public void init() {
	        MockitoAnnotations.initMocks(this);
	     	       
	    }
	 @Test
	 public void createUrlTest() {
		// create url
			URL url = null;
			String zipCode = "19355";
			
			url = helper.createUrl(zipCode);
			
			assertNotNull(url);
	 }
}
