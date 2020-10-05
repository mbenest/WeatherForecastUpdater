/*
 * 
 */
package eu.akka.weatherforecast.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * This class is responsible of the HTTP update and notification.
 * 
 * @author mbenest on behalf of AKKA TECHNOLOGIES.
 * @since 1.0.0
 */
public class WeatherForecastHttpUpdater extends Observable{
	
	/** The refresh rate. */
	private long refreshRate;
	
	/** The script url. */
	private String scriptUrl; 
	
	/** The result. */
	private Integer result = -1;
	
	/**
	 * Instantiates a new weather forecast updater.
	 *
	 * @param scriptUrl the script url
	 * @param refreshRate the refresh rate
	 */
	public WeatherForecastHttpUpdater(String scriptUrl, long refreshRate) {
		this.scriptUrl = scriptUrl;
		this.refreshRate = refreshRate;
	}
	
	/**
	 * Start the HTTP update task.
	 * @since 1.0.0
	 */
	public void start(){
		 TimerTask repeatedTask = new TimerTask() {
		        public void run() {
		        	try {
					HttpURLConnection connection =  (HttpURLConnection) new URL(scriptUrl).openConnection();
					connection.setConnectTimeout(2000);
					connection.setReadTimeout(2000);

					if (connection.getResponseCode() == 200) {
						BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String value =  reader.lines().collect(Collectors.joining(System.lineSeparator())).trim();
						result = Integer.valueOf(value);
					}else {
						result = -1;
					}
					
					setChanged();
					notifyObservers(result);
					
				} catch (IOException e) {
					result = -1;
					setChanged();
					notifyObservers(result);
				}
		        }
		    };
		    Timer timer = new Timer("Timer");

		    long delay  = 0;
		    long period = refreshRate;
		    timer.scheduleAtFixedRate(repeatedTask, delay, period);
	}
}
