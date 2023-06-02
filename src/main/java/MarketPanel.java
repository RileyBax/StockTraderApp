
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import io.github.mainstringargs.alphavantagescraper.output.quote.StockQuotesResponse;
import io.github.mainstringargs.alphavantagescraper.output.quote.data.StockQuote;
import io.github.mainstringargs.alphavantagescraper.output.timeseries.data.StockData;

public class MarketPanel extends JPanel{
	
	ArrayList<Stock> recentList;
	static int length = 1;
	RequestHandler rh;

	public MarketPanel(final RequestHandler rh) {
		
		this.rh = rh;
		recentList = new ArrayList<Stock>();
		
		this.setBounds(0, 0, 800, 600);
		this.setLayout(null);
		
		Graph stockGraph = new Graph();
		this.add(stockGraph);
		
		final JTextField searchBar = new JTextField();
		searchBar.setBounds(220, 10, 400, 30);
		this.add(searchBar);
		
		JTextField buyField = new JTextField();
		buyField.setBounds(630, 80, 140, 30);
		this.add(buyField);
		
		JButton buyButton = new JButton("Buy");
		buyButton.setBounds(630, 50, 140, 30);
		this.add(buyButton);
		
		JTextField sellField = new JTextField();
		sellField.setBounds(630, 150, 140, 30);
		this.add(sellField);
		
		JButton sellButton = new JButton("Sell");
		sellButton.setBounds(630, 120, 140, 30);
		this.add(sellButton);
		
		JButton hourGraph = new JButton("1 week");
		hourGraph.setBounds(630, 200, 140, 30);
		hourGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				length = 1;
				stockGraph.setStock(updateGraph(searchBar.getText()));
			}
		});
		this.add(hourGraph);
		
		JButton todayGraph = new JButton("1 month");
		todayGraph.setBounds(630, 240, 140, 30);
		todayGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				length = 2;
				stockGraph.setStock(updateGraph(searchBar.getText()));
			}
		});
		this.add(todayGraph);
		
		JButton weekGraph = new JButton("1 year");
		weekGraph.setBounds(630, 280, 140, 30);
		weekGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				length = 3;
				stockGraph.setStock(updateGraph(searchBar.getText()));
			}
		});
		this.add(weekGraph);
		
		JButton pButton = new JButton("Portfolio");
		pButton.setBounds(10, 10, 95, 30);
		this.add(pButton);
		
		JButton mButton = new JButton("Market");
		mButton.setBounds(115, 10, 95, 30);
		this.add(mButton);
		
		final JTextArea stockData = new JTextArea();
		stockData.setBounds(220, 360, 550, 190);
		this.add(stockData);
		
		final JPanel recentStock[] = new JPanel[16];
		final JLabel recentStockLabel[] = new JLabel[16];
		for(int i = 0; i < recentStock.length; i++) {
			
			recentStockLabel[i] = new JLabel();
			recentStockLabel[i].setBounds(0, 0, 200, 30);
			
			recentStock[i] = new JPanel();
			recentStock[i].setBounds(10, 70 + i * 30, 200, 30);
			recentStock[i].setBackground(Color.LIGHT_GRAY);
			this.add(recentStock[i]);
			
			recentStock[i].add(recentStockLabel[i]);
			
		}
		
		JLabel stockListLabel = new JLabel("Recently Viewed");
		stockListLabel.setBounds(60, 40, 200, 30);
		this.add(stockListLabel);
		
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(630, 10, 140, 30);
		searchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Stock stock = rh.get(searchBar.getText());
				if(stock != null) {
					recentList.add(stock);
					
					for(int i = 0; i < recentList.size(); i++) {

						recentStock[i].setBackground(new Color(255 - 15 * i, 255 - 15 * i, 255 - 15 * i));
						if(recentStockLabel[i].getText().isEmpty()) {
							recentStockLabel[i].setText(stock.getSymbol());
						}
						recentStock[i].setLocation(10, 70 + ((recentList.size() - 1) * 30 - i * 30));
						
					}
					
					stockData.setText("Symbol: " + stock.getSymbol() + "\nCurrent Price: " + stock.getPrice() + "\nClose Price: " + stock.getClosePrice()  + "\nOpen Price: " + stock.getOpenPrice());
					
					// update graph
					stockGraph.setStock(rh.getHistory(searchBar.getText(), length));
					
					
				}
				else {
					stockData.setText("Could not retrieve Stock, please try again.");
				}

			}
			
		});
		this.add(searchButton);
		
	}
	
	public ArrayList<StockData> updateGraph(String symbol) {
		
		ArrayList<StockData> out = (ArrayList<StockData>) rh.getHistory(symbol, length);
		
		if(out.size() > 0) {
			return out;	
		}
		else {
			return null;
		}
		
	}
	
}
