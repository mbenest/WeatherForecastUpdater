package eu.akka.weatherforecast.gui.widgets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * This class is the Weather Forecast label.
 * 
 * @author mbenest on behalf of AKKA TECHNOLOGIES.
 * @since 1.0.0
 */
public class WeatherForecastLabel extends JLabel {

	/** The id. */
	private int id;

	/** The icon. */
	private ImageIcon icon;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7246762068891544315L;

	/**
	 * Instantiates a new weather forecast label.
	 *
	 * @param id      the id
	 * @param tooltip the tooltip
	 * @since 1.0.0
	 */
	public WeatherForecastLabel(int id, String tooltip) {
		this.id = id;
		icon = new ImageIcon(ClassLoader.getSystemResource("images/weather_" + id + ".png"), tooltip);
		initComponent();
	}

	/**
	 * Inits the component.
	 * 
	 * @since 1.0.0
	 */
	private void initComponent() {
		setIcon(icon);
		setSize(icon.getIconWidth(), icon.getIconHeight());
		setEnabled(false);
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 * @since 1.0.0
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 * @since 1.0.0
	 */
	public ImageIcon getIcon() {
		return icon;
	}

}
