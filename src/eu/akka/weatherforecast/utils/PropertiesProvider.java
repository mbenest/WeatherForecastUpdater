package eu.akka.weatherforecast.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * This class is used to get the application properties.
 * 
 * @author mbenest on behalf of AKKA TECHNOLOGIES.
 * @since 1.0.0
 */
public class PropertiesProvider {
	
	private static final String propertiesFileURL = "properties/weatherforecast.properties";
	private static PropertiesProvider instance;
	private static Properties props = new Properties();
	
	/**
	 * Gets object instance
	 * 
	 * @return the instance
	 * @throws IOException when IO error
	 * @since 1.0.0
	 */
	public static PropertiesProvider getInstance() throws IOException {
		if (instance == null) {
			instance = new PropertiesProvider();
			props.load(ClassLoader.getSystemResourceAsStream(propertiesFileURL));
		}
		return instance;
	}
	
	/**
	 * Gets the property value according to key
	 * 
	 * @param key the property key
	 * @return the property value
	 * @since 1.0.0
	 */
	public String getPropertyValue(String key) {
		if(props == null) {
			return null;
		}
		return props.getProperty(key);
	}
	
	/**
	 * Gets the property value according to key and index after splitting according splitter
	 * 
	 * @param key the property key
	 * @param splitter the splitter value
	 * @param index the index value after splitting
	 * @return the value after splitting
	 * @since 1.0.0
	 */
	public String getPropertyValue(String key, String splitter, int index) {
		if(props == null) {
			return null;
		}
		return props.getProperty(key).split(splitter)[index];
	}
	

}
