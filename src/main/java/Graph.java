import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import io.github.mainstringargs.alphavantagescraper.output.timeseries.data.StockData;

import javax.swing.JPanel;

public class Graph extends JPanel{

	ArrayList<StockData> stockData;

	public Graph() {

		this.setBounds(220, 50, 400, 300);
		stockData = new ArrayList<StockData>();

	}

	public void setStock(List<StockData> stockData) {

		if(stockData != null) {
			this.stockData = (ArrayList<StockData>) stockData;
		}
		else {
			this.stockData = new ArrayList<StockData>();
		}
		this.repaint();

	}

	@Override
	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, 400, 300);

		g2d.setColor(Color.BLACK);
		g2d.drawLine(30, 270, 30, 30);
		g2d.drawLine(30, 270, 370, 270);

		double maxPrice = max();
		double minPrice = min();
		double difference = maxPrice - minPrice;
		int x = 0;
		int y = 0;
		Point points[] = new Point[stockData.size()];

		for(int i = 0; i < stockData.size(); i++) {

			x = 30 + i * (340 / (stockData.size() - 1));
			y = (int) (30 + (stockData.get(i).getAdjustedClose() - minPrice) / difference * 240);

			points[i] = new Point(x, y);

		}

		if(points.length > 1) {

			for(int i = 0; i < points.length - 1; i++) {

				g2d.drawLine(points[i].x, points[i].y, points[i+1].x, points[i+1].y);

			}
			
		}

	}

	public double max() {

		double out = 0;

		for(int i = 0; i < stockData.size(); i++) {

			if(stockData.get(i).getAdjustedClose() > out) out = stockData.get(i).getAdjustedClose();

		}

		return out;
	}

	public double min() {

		if(stockData.size() > 0) {

			double out = stockData.get(0).getAdjustedClose();

			for(int i = 0; i < stockData.size(); i++) {

				if(stockData.get(i).getAdjustedClose() < out) out = stockData.get(i).getAdjustedClose();

			}

			return out;

		}
		else {
			return 0;
		}

	}

}
