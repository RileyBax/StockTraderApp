
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

public class MarketPanel extends JPanel{
	
	ArrayList<Stock> recentList;

	public MarketPanel(final RequestHandler rh) {
		
		recentList = new ArrayList<Stock>();
		
		this.setBounds(0, 0, 800, 600);
		this.setLayout(null);
		
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
		
		JButton hourGraph = new JButton("1 hour");
		hourGraph.setBounds(630, 200, 140, 30);
		this.add(hourGraph);
		
		JButton todayGraph = new JButton("24 hours");
		todayGraph.setBounds(630, 240, 140, 30);
		this.add(todayGraph);
		
		JButton weekGraph = new JButton("1 week");
		weekGraph.setBounds(630, 280, 140, 30);
		this.add(weekGraph);
		
		JButton monthGraph = new JButton("1 month");
		monthGraph.setBounds(630, 320, 140, 30);
		this.add(monthGraph);
		
		JButton pButton = new JButton("Portfolio");
		pButton.setBounds(10, 10, 95, 30);
		this.add(pButton);
		
		JButton mButton = new JButton("Market");
		mButton.setBounds(115, 10, 95, 30);
		this.add(mButton);
		
		final JTextField searchBar = new JTextField();
		searchBar.setBounds(220, 10, 400, 30);
		this.add(searchBar);
		
		final JTextArea stockData = new JTextArea();
		stockData.setBounds(220, 360, 550, 190);
		this.add(stockData);
		
		// make an object for this
		final JPanel recentStock[] = new JPanel[16];
		final JLabel recentStockLabel[] = new JLabel[16];
		for(int i = 0; i < recentStock.length; i++) {
			
			recentStockLabel[i] = new JLabel();
			recentStockLabel[i].setBounds(10, 70 + i * 30, 200, 30);
			this.add(recentStockLabel[i]);
			
			recentStock[i] = new JPanel();
			recentStock[i].setBounds(10, 70 + i * 30, 200, 30);
			recentStock[i].setBackground(Color.LIGHT_GRAY);
			this.add(recentStock[i]);
			
		}
		
		JLabel stockListLabel = new JLabel("Previously Viewed");
		stockListLabel.setBounds(60, 40, 200, 30);
		this.add(stockListLabel);
		
		JPanel stockGraph = new JPanel();
		stockGraph.setBounds(220, 50, 400, 300);
		stockGraph.setBackground(Color.LIGHT_GRAY);
		this.add(stockGraph);
		
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(630, 10, 140, 30);
		searchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Stock stock = rh.get(searchBar.getText());
				
				recentList.add(stock);
				
				for(int i = 0; i < recentList.size(); i++) {
					// fix this
					recentStock[i].setBackground(new Color(255 - 15 * i, 255 - 15 * i, 255 - 15 * i));
					recentStockLabel[i].setText(stock.getSymbol());
					
				}
				
				stockData.setText("Symbol: " + stock.getSymbol() + "\nCurrent Price: " + stock.getPrice() + "\nClose Price: " + stock.getClosePrice()  + "\nOpen Price: " + stock.getOpenPrice());
				
			}
			
		});
		this.add(searchButton);
		
	}
	
}
