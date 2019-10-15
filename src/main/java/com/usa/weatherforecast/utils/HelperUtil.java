package com.usa.weatherforecast.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import static com.usa.weatherforecast.utils.Constants.*;
/**
 * 
 * @author bindu
 *
 */
@Component
public class HelperUtil {

	Logger logger = Logger.getLogger(HelperUtil.class);

	/**
	 * 
	 * @param zipCode
	 * @return actual URL to send request for WeatherUnlocked API
	 */
	public URL createUrl(String zipCode) {
		// create url
		URL url = null;
		try {
		Properties property = getPropertyFile();
		String app_Id = property.getProperty(APP_ID);
		String app_key = property.getProperty(APP_KEY);
		
			url = new URL(property.getProperty(API_URL) + zipCode + CONCATNATE_APP_ID + app_Id
					+ CONCATNATE_APP_KEY + app_key + "");
		} catch (MalformedURLException e) {
			logger.error("Problem building the URL " + e);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return url;
	}

	/**
	 * 
	 * @param timeStr
	 * @return Date in String formate hh:mm
	 */
	public String getHourMin(String timeStr) {
		int t = Integer.parseInt(timeStr);
		int hours = t / 60; // since both are ints, you get an int
		int minutes = t % 60;
		logger.info(hours + ":" + minutes);

		return hours + ":" + minutes;
	}

	/**
	 * 
	 * @param url
	 * @return response from Weatherunlocked API
	 * @throws IOException
	 */
	public String createHttpRequest(URL url) throws IOException {
		String jsonResponse = "";
		if (url != null) {
			HttpURLConnection urlConnection = null;
			InputStream inputStream = null;
			try {
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod(HTTP_REQ_RES_TYPE);
				urlConnection.setReadTimeout(10000);
				urlConnection.setConnectTimeout(15000);
				urlConnection.connect();
				if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					inputStream = urlConnection.getInputStream();
					jsonResponse = readFromStream(inputStream);
				} else {

					logger.info("QueryUtils: Error response code: " + urlConnection.getResponseCode());
				}
			} catch (IOException e) {
				logger.error("QueryUtils: Problem retrieving the JSON results." + e);
			} finally {
				if (urlConnection != null)
					urlConnection.disconnect();
				if (inputStream != null)
					inputStream.close();
			}
		}
		return jsonResponse;
	}

	/**
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private String readFromStream(InputStream inputStream) throws IOException {
		StringBuilder output = new StringBuilder();
		if (inputStream != null) {
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName(HTTP_HEADER));
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line;
			do {
				line = reader.readLine();
				output.append(line);
			} while (line != null);
		}
		return output.toString();
	}
	
	
	 public Properties getPropertyFile() throws IOException {
		 ClassLoader classLoader = new HelperUtil().getClass().getClassLoader();
		 
	        File file = new File(classLoader.getResource(PROPERTY_FILE_NAME).getFile());
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			return properties;
	 }
}
