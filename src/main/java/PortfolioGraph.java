import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import io.github.mainstringargs.alphavantagescraper.output.timeseries.data.StockData;

public class PortfolioGraph extends JPanel{

	ArrayList<StockData> stockData;
	LocalDateTime date;
	int length;
	float boughtAt;

	public PortfolioGraph() {

		this.setBounds(220, 70, 400, 280);
		stockData = new ArrayList<StockData>();

	}

	public void setStock(List<StockData> stockData, int length, float boughtAt) {

		if(stockData != null) {
			this.stockData = (ArrayList<StockData>) stockData;
			this.length = length;
			this.boughtAt = boughtAt;
		}
		else {
			this.stockData = new ArrayList<StockData>();
			JOptionPane.showMessageDialog(new JFrame(), "Could not Retrieve Graph Data. \nPlease wait, then try again.");
			this.length = 0;
		}
		this.repaint();

	}

	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, 400, 280);

		if(length > 0) {

			g2d.setColor(Color.BLACK);
			g2d.drawLine(40, 250, 40, 30);
			g2d.drawLine(40, 250, 370, 250);

			double maxPrice = max();
			double minPrice = min();
			double difference = maxPrice - minPrice;
			int x = 0;
			int y = 0;
			Point points[] = new Point[length];

			for(int i = length - 1; i >= 0 ; i--) {

				x = (int) (40 + 330 - (i * (330.0f / (length - 1))));
				y = (int) (30 + 220 - (stockData.get(i).getAdjustedClose() - minPrice) / difference * 220);
				
				points[i] = new Point(x, y);

			}
				
			if(points.length > 1) {

				if(stockData.get(length-1).getAdjustedClose() > stockData.get(0).getAdjustedClose()) g2d.setColor(Color.RED);
				else g2d.setColor(Color.GREEN);

				for(int i = 0; i < points.length - 1; i++) {

					g2d.drawLine(points[i].x, points[i].y, points[i+1].x, points[i+1].y);

				}
				
				g2d.setColor(Color.red);
				g2d.drawLine(40, (int) (250 - (boughtAt - minPrice) / difference * 220), 370, (int) (250 - (boughtAt - minPrice) / difference * 220));

				g2d.setColor(Color.BLACK);

				g2d.drawString(stockData.get(length-1).getDateTime().toString(), 20, 270);
				g2d.drawString(stockData.get(0).getDateTime().toString(), 280, 270);

				g2d.drawString(String.valueOf(new DecimalFormat("0.00").format(maxPrice)), 0, 20);
				g2d.drawString(String.valueOf(new DecimalFormat("0.00").format(minPrice)), 0, 250);

			}

			if(points.length < 10) g2d.drawString("7 Day recent Close Price", 140, 20);
			else if (points.length > 10 && points.length < 32) g2d.drawString("1 Month recent Close Price", 130, 20);
			else if(points.length > 32) g2d.drawString(length + " Day recent Close Price", 140, 20);

		}

	}

	public double max() {

		double out = 0;

		for(int i = 0; i < length; i++) {

			if(stockData.get(i).getAdjustedClose() > out) out = stockData.get(i).getAdjustedClose();

		}

		return out;
	}

	public double min() {

		if(length > 0) {

			double out = stockData.get(0).getAdjustedClose();

			for(int i = 0; i < length; i++) {

				if(stockData.get(i).getAdjustedClose() < out) out = stockData.get(i).getAdjustedClose();

			}

			return out;

		}
		else {
			return 0;
		}

	}
	
}
