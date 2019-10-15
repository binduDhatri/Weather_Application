package com.usa.weatherforecast.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.usa.weatherforecast.service.TimeFrames;
import com.usa.weatherforecast.service.WeatherService;
import com.usa.weatherforecast.utils.HelperUtil;
import static com.usa.weatherforecast.utils.Constants.*;

@Controller
public class WeatherController {
	Logger logger = Logger.getLogger(WeatherController.class);
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	
	/*
	 * @Autowired(required=false) WeatherService weatherService;
	 */
	/*
	 * @Autowired HelperUtil helper;
	 */
	
	/*
	 * Controller class 
	 * 
	 * Takes Zip Code as Input 
	 * 
	 * Returns model attributes with tomorrow's coolest temperature.
	 * 
	 * WeatherUnlock API : Which returns 7 days weather-forecast along with 3 hour time frame temperature
	 * 
	 * Server class: parse the API response and fetch minimum temperature 
	 */
	
	@PostMapping(POST_URL_WEATHER_FORECASTE)
	public String getWeatherData(@RequestParam(INPUT_MODEL_ZIPCODE) String zipCode, Model model) {
		try {
			model.addAttribute(INPUT_MODEL_ZIPCODE, zipCode);
			WeatherService weatherService = new WeatherService();
			HelperUtil helper = new HelperUtil();
			//returns list of time-frames for tomorrow- in 3 hour time frames
			List<TimeFrames> listOfTimeFrame  = weatherService.getWeatherData(zipCode);
			//get minimum time-frame from list of time-frame
			TimeFrames minTempOfDay = weatherService.getMinTimeFrame(listOfTimeFrame);
			//convert min in hh:mm
			String dateTime = helper.getHourMin(minTempOfDay.getTime());
			
			//send list of object into view
			model.addAttribute(INPUT_MODEL_LISTOFTIMEFRAME, listOfTimeFrame);
			model.addAttribute(INPUT_MODEL_DATE, minTempOfDay.getDate());
			model.addAttribute(INPUT_MODEL_DATETIME,dateTime );
			model.addAttribute(INPUT_MODEL_WX_DESC,minTempOfDay.getWx_desc() );
			
			model.addAttribute(INPUT_MODEL_TEMPERATURE, minTempOfDay.getTemp_f());
		}catch(Exception e) {
			logger.error("Exception Occured - 500 " + e);
		}
			return WEATHERDETAIL_MODEL_NAME;
	}
}
