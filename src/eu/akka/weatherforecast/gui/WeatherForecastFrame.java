package eu.akka.weatherforecast.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import eu.akka.weatherforecast.gui.widgets.WeatherForecastLabel;
import eu.akka.weatherforecast.http.WeatherForecastHttpUpdater;
import eu.akka.weatherforecast.utils.PropertiesProvider;

/**
 * This class is the main Weather Forecast Updater frame.
 * 
 * @author mbenest on behalf of AKKA TECHNOLOGIES.
 * @since 1.0.0
 */
public class WeatherForecastFrame extends JFrame implements Observer{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7913573317894947296L;
	
	/** The Constant HTTP_CONNECTION_ERROR. */
	private static final String HTTP_CONNECTION_ERROR = "HTTP connection error !";
	
	/** The score updater. */
	private WeatherForecastHttpUpdater scoreUpdater;
	
	/** The forecast label. */
	private JLabel forecastInformationLabel = new JLabel("", JLabel.CENTER);
	
	/** The connection label. */
	private JLabel connectionLabel = new JLabel("", JLabel.CENTER);
	
	/** The weather forecast labels. */
	private List<WeatherForecastLabel> weatherForecastLabels = new ArrayList<>();
	

	/**
	 * Instantiates a new weather forecast frame.
	 * @since 1.0.0
	 */
	public WeatherForecastFrame() {
		super();
		initComponent();
	}

	/**
	 * Inits the component.
	 * @since 1.0.0
	 */
	private void initComponent() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
			
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				scoreUpdater.start();
			}
		});
				
		WeatherForcastPanel panel = new WeatherForcastPanel(new GridBagLayout());
		
		String[] status = null;
		
		try {
			
			String windowTitle = PropertiesProvider.getInstance().getPropertyValue("window.title");
			String widgetVersion = PropertiesProvider.getInstance().getPropertyValue("widget.version");
			String windowSubTitle = PropertiesProvider.getInstance().getPropertyValue("window.subtitle");
			String weatherUrl = PropertiesProvider.getInstance().getPropertyValue("weather.url");
			Long weatherRate = Long.valueOf(PropertiesProvider.getInstance().getPropertyValue("weather.rate"));
			Integer windowHeight = Integer.valueOf(PropertiesProvider.getInstance().getPropertyValue("window.height"));
			Integer windowWidth = Integer.valueOf(PropertiesProvider.getInstance().getPropertyValue("window.width"));
			Color backgroundColor = Color.decode(PropertiesProvider.getInstance().getPropertyValue("window.background.color"));
			Color foregroundColor = Color.decode(PropertiesProvider.getInstance().getPropertyValue("window.foreground.color"));
			Color highLightColor = Color.decode(PropertiesProvider.getInstance().getPropertyValue("window.highlight.color"));
			
			setTitle(windowTitle + " - v" + widgetVersion);
			setSize(windowWidth, windowHeight);
			
			panel.setBackground(backgroundColor);
			panel.setForeground(foregroundColor);
			panel.setBorder(BorderFactory.createTitledBorder(windowSubTitle));
			((TitledBorder)panel.getBorder()).setTitleColor(foregroundColor);
			
			panel.addContainerListener(new ContainerListener() {
				
				@Override
				public void componentRemoved(ContainerEvent e) {
					
				}
				
				@Override
				public void componentAdded(ContainerEvent e) {
					e.getChild().setBackground(backgroundColor);
					e.getChild().setForeground(foregroundColor);
				}
			});
												
			status = PropertiesProvider.getInstance().getPropertyValue("weather.status").split(";");
			
			forecastInformationLabel.addPropertyChangeListener("text", new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					((JLabel)evt.getSource()).setForeground(highLightColor);
				}
			});
			
			scoreUpdater = new WeatherForecastHttpUpdater(weatherUrl, weatherRate);
			scoreUpdater.addObserver(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.gridwidth = 5;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		forecastInformationLabel.setPreferredSize(new Dimension(50, 20));
		panel.add(forecastInformationLabel, gbc1);
		
		for(int i = 0; i < 5; i++) {
			GridBagConstraints gbc2 = new GridBagConstraints();
			gbc2.gridx = i;
			gbc2.gridy = 1;
			gbc2.gridwidth = 1;
			WeatherForecastLabel label = new WeatherForecastLabel(i, status[i]);
			weatherForecastLabels.add(label);
			panel.add(label, gbc2);
		}

		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.gridx = 0;
		gbc3.gridx = 0;
		gbc3.gridy = 2;
		gbc3.gridwidth = 5;
		gbc3.fill = GridBagConstraints.HORIZONTAL;
		connectionLabel.setPreferredSize(new Dimension(50, 20));
		panel.add(connectionLabel, gbc3);
		
		getContentPane().add(panel);

	}

	
	/**
	 * Update.
	 *
	 * @param o the observable
	 * @param arg the object
	 * @since 1.0.0
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Integer) {
			Integer score = (Integer) arg;
			connectionLabel.setText("Last update: " + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
			forecastInformationLabel.setText("");
			weatherForecastLabels.stream().forEach(l -> {
				l.setEnabled(l.getId() == score);
				if(l.isEnabled()){
					forecastInformationLabel.setText("- " + l.getIcon().getDescription() + " -");
					setIconImage(l.getIcon().getImage());
				}
			});
			// HTTP connection error
			if(score == -1) {
				connectionLabel.setText(HTTP_CONNECTION_ERROR);
			}
		}
		
	}
}
