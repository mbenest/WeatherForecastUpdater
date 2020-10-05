package eu.akka.weatherforecast;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;

import eu.akka.weatherforecast.gui.WeatherForecastFrame;


/**
 * This class is the Weather Forecast Updater launcher.
 * 
 * @author mbenest on behalf of AKKA TECHNOLOGIES.
 * @since 1.0.0
 */
public class Launcher {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				showMainFrame();
			}
		});
	}
	
	/**
	 * Show main frame.
	 * @since 1.0.0
	 */
	private static void showMainFrame() {
		WeatherForecastFrame frame = new WeatherForecastFrame();
		frame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}

}
