package eu.akka.weatherforecast.gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * This class is the main Weather Forecast Updater panel.
 * 
 * @author mbenest on behalf of AKKA TECHNOLOGIES.
 * @since 1.0.0
 */
public class WeatherForcastPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4504211053937424283L;

	/**
	 * Instantiates a new weather forcast panel.
	 *
	 * @param gridBagLayout the grid bag layout
	 */
	public WeatherForcastPanel(GridBagLayout gridBagLayout) {
		super(gridBagLayout);
	}

	/**
	 * Paint component.
	 *
	 * @param g the graphic
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color color1 = getBackground();
        Color color2 = color1.brighter();
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(
            0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }

	
}
