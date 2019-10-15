package com.usa.weatherforecast.service;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.usa.weatherforecast.utils.HelperUtil;
import static com.usa.weatherforecast.utils.Constants.*;

/**
 * 
 * @author bindu
 * 
 *         The WeatherService class
 * 
 *         consists methods: getWeatherData(zipcode) returns List of TimeFrames
 *         Object for tomorrow.
 * 
 * 
 *
 */
@Service
public class WeatherService {

//	@Autowired
//	HelperUtil helper;

	Logger logger = Logger.getLogger(WeatherService.class);
	
	/**
	 * 
	 * @param zipCode
	 * @return List<TimeFrames>
	 * 
	 *         Calls createHttpRequests : methods which creates the url and send
	 *         request for API then returns response Calls parseJson : method which
	 *         parse the response and collects required fields
	 * 
	 */
	public List<TimeFrames> getWeatherData(String zipCode) {
		HelperUtil helper = new HelperUtil();
		URL url = helper.createUrl(zipCode);
		String jsonResponse = null;
		List<TimeFrames> listOfTimeFrame = null;
		logger.info("the api url" + url);
		try {
			// send request and get response from API
			jsonResponse = helper.createHttpRequest(url);
			// parse the response and collect List of Time-frames for tomorrow
			listOfTimeFrame = parseJson(jsonResponse);
		} catch (Exception e) {
			logger.error("exception " + e);
		}
		return listOfTimeFrame;
	}

	/**
	 * 
	 * @param jsonResponse
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private List<TimeFrames> parseJson(String jsonResponse) throws JsonProcessingException, IOException {

		List<TimeFrames> listOfTimeFrame = null;
		JSONObject obj = new JSONObject(jsonResponse);
		JSONArray listOfJson = (JSONArray) obj.get(JSON_RESPONSE_FIRST_KEY);

		Gson g = new Gson();
		// list of days
		List<Days> listOfDays = g.fromJson(listOfJson.toString(), new TypeToken<List<Days>>() {
		}.getType());

		// get tomorrow's date + get a date to represent "today" + add one day to the
		// date/calendar
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		logger.info("today's date" + today);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		// get "tomorrow"
		Date tomorrowDate = calendar.getTime();
		logger.info("tomorrow's date" + tomorrowDate);
		// convert date to localdate
		// 1. Convert Date -> Instant
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = tomorrowDate.toInstant();
		// 2. Instant + system default time zone + toLocalDate() = LocalDate
		LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
		String tomorrowDateStr = DateTimeFormatter.ofPattern(JSON_RESPONSE_DATE_FORMAT).format(localDate);


		// fetch tomorrow's weather object
		Days tomorrowDayObj = listOfDays.stream().filter(dateStr -> tomorrowDateStr.equals(dateStr.getDate())).findAny()
				.orElse(null);
		if (tomorrowDayObj != null) {
			listOfTimeFrame = tomorrowDayObj.getTimeFrameObj();

		}

		return listOfTimeFrame;

	}

	/**
	 * 
	 * @param listOfTimeFrame
	 * @return minimum timeframe object consist of coolest temperature in list of
	 *         tomorrow's weatherforecast's timeframes.
	 */
	public TimeFrames getMinTimeFrame(List<TimeFrames> listOfTimeFrame) {
		TimeFrames minTimeFrame = null;
		if (listOfTimeFrame != null) {
			minTimeFrame = Collections.min(listOfTimeFrame, Comparator.comparing(s -> s.getTemp_f()));
			logger.info("The min temperature is " + minTimeFrame.getTemp_f() + "" + minTimeFrame.getDate() + ""
					+ minTimeFrame.getTime() + " " + minTimeFrame.getWx_desc());

		}
		return minTimeFrame;
	}

}
