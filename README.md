# Weather Forecast Application

## Overview
Weather Forecast is a Spring Boot web-application with MVC design pattern. This uses Weather Unlocked API to get weather forecast based on zipcode. User enters zipcode to get tommorrow's coolest temperature. 

The Weather Unlocked API returns temperature in a form of timeframe(json) it consists weather details of every 3 hours difference time. The app ID and Key is generated and added in property file, used to fetch data from API. 

#Installation and Running
Download the jar file from the project and import it in eclipse or if java is alrady installed in the system use below command 
java -jar weatherforecast.jar 
In eclipse: 
Click on File > Import Project 
Click on Import Existing Maven Project.
Select downloaded directry of .jar, click Finish. 

Right click on weather-api-application -> run as -> Spring boot App


run below url in the browser 

http://localhost:8080

Enter ZipCode web-page appears, then enter any state zipcode with in the USA click on Submit 

the WeatherDetails page gets loaded wich shows tomorrow's coolest temparature. 




