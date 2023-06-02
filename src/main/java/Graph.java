import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

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
		int x = 0;
		int y = 0;
		
		for(int i = 0; i < stockData.size(); i++) {
			
			x = 30 + i * (340 / stockData.size() - 1);
			y = (int) (30 + stockData.get(i).getClose() / maxPrice * 240);
			
			System.out.println(x + ", " + y);
			
			g2d.drawOval(x, y, 5, 5);
			
		}
		
	}
	
	public double max() {
		
		double out = 0;
		
		for(int i = 0; i < stockData.size(); i++) {
			
			if(stockData.get(i).getClose() > out) out = stockData.get(i).getClose();
			
		}
		
		return out;
	}
	
}
