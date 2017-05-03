package chartexamples;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;

public class LineChartPanel extends JPanel {
	private Rectangle2D.Double borderRectangle;
	private int n;
	private double[] x;
	private double[] y;
	private Color c;
	private Line2D.Double yAxis = new Line2D.Double(200, 20, 200, 280);
	private Line2D.Double xAxis = new Line2D.Double(20, 150, 380, 150);

	public LineChartPanel() {
		// default constructor for initialization
	}

	public LineChartPanel(Rectangle2D.Double border, int nPoints, double[] xValues, double[] yValues,
			Color colorValue) {
		this.borderRectangle = border;
		this.n = nPoints;
		this.x = xValues;
		this.y = yValues;
		this.c = colorValue;
	}

	public void paintComponent(Graphics g) {
		// Draws a line chart - pairs of (x,y) coordinates
		// borderRectangle - rectangle region to draw plot
		// n - number of points to plot
		// x - array of x points (lower index is 0, upper index is n-1)
		// y - array of y points (lower index is 0, upper index is n-1)
		// c - color of line
		// Need at least 2 points to plot
		if (n < 2) {
			return;
		}
		double xMin = x[0];
		double xMax = x[0];
		double yMin = y[0];
		double yMax = y[0];
		// find minimums and maximums
		for (int i = 1; i < n; i++) {
			xMin = Math.min(xMin, x[i]);
			xMax = Math.max(xMax, x[i]);
			yMin = Math.min(yMin, y[i]);
			yMax = Math.max(yMax, y[i]);
		}
		// Extend y values a bit so lines are not right on borders
		yMin = (1 - 0.05 * Double.compare(yMin, 0)) * yMin;
		yMax = (1 + 0.05 * Double.compare(yMax, 0)) * yMax;
		Graphics2D g2D = (Graphics2D) g;
		super.paintComponent(g2D);
		Line2D.Double myLine;
		g2D.setPaint(c);
		for (int i = 0; i < n - 1; i++) {
			// plot in user coordinates
			myLine = new Line2D.Double(xPhysicalToxUser(borderRectangle, x[i], xMin, xMax),
					yPhysicalToyUser(borderRectangle, y[i], yMin, yMax),
					xPhysicalToxUser(borderRectangle, x[i + 1], xMin, xMax),
					yPhysicalToyUser(borderRectangle, y[i + 1], yMin, yMax));

			g2D.draw(myLine);
		}
		// draw border
		g2D.setPaint(Color.BLACK);
		g2D.draw(borderRectangle);
		g2D.draw(yAxis);
		g2D.draw(xAxis);
		g2D.dispose();
	}

	private double xPhysicalToxUser(Rectangle2D.Double r, double xPhysical, double xMin, double xMax) {
		double xTemp = (r.x + (xPhysical - xMin) * (r.width - 1) / (xMax - xMin));
		return xTemp;
	}

	private double yPhysicalToyUser(Rectangle2D.Double r, double yPhysical, double yMin, double yMax) {
		return (r.y + (yMax - yPhysical) * (r.height - 1) / (yMax - yMin));
	}
}