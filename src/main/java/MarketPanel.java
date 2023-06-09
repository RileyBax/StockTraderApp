
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import io.github.mainstringargs.alphavantagescraper.output.timeseries.data.StockData;

public class MarketPanel extends JPanel{

	ArrayList<StockPanel> recentList;
	RequestHandler rh;
	int length = 8;
	static JPanel recentPanel;
	Graph stockGraph;
	final JTextArea stockData;
	final JTextField searchBar;
	final JTextArea stockHistoryData;
	final JLabel sharesLabel;
	Portfolio pf;
	JButton searchButton;
	List<StockData> currentStockDataList;
	JButton sellButton;
	JButton buyButton;

	// Allows for buying and selling of stocks, searches for stock using AlphaVantage wrapper
	// Displays stock data to panel
	public MarketPanel(final RequestHandler rh, final Portfolio pf) {

		this.rh = rh;
		this.pf = pf;
		recentList = new ArrayList<StockPanel>();
		this.setOpaque(false);

		this.setBounds(0, 0, 800, 600);
		this.setLayout(null);

		stockGraph = new Graph();
		this.add(stockGraph);

		JLabel graphLabel = new JLabel("Price History Graph");
		graphLabel.setBounds(370, 40, 200, 30);
		this.add(graphLabel);

		searchBar = new JTextField();
		searchBar.setBounds(220, 10, 400, 30);
		this.add(searchBar);

		final JTextField buyField = new JTextField();
		buyField.setBounds(630, 100, 140, 30);
		this.add(buyField);

		buyButton = new JButton("Buy");
		buyButton.setBounds(630, 70, 140, 30);
		buyButton.addActionListener(new ActionListener() {
                        
                        // Adds stock to portfolio stock list
			@Override
			public void actionPerformed(ActionEvent e) {

				if(!buyField.getText().isEmpty()  && !stockData.getText().isEmpty()) {
					Stock stock = searchRecent(stockData.getText().split("\n")[0].split(" ")[1]);

					try {
						if(Float.parseFloat(buyField.getText()) > 0) pf.add(stock, Float.parseFloat(buyField.getText()));
						updateSharesLabel(stock);
					}
					catch(NumberFormatException e1) {}
				}

			}
		});
		this.add(buyButton);

		final JTextField sellField = new JTextField();
		sellField.setBounds(630, 170, 140, 30);
		this.add(sellField);

		sellButton = new JButton("Sell");
		sellButton.setBounds(630, 140, 140, 30);
		sellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(!sellField.getText().isEmpty()  && !stockData.getText().isEmpty()) {

					try {
						pf.sell(pf.getStock(stockData.getText().split("\n")[0].split(" ")[1]), Float.parseFloat(sellField.getText()));
						updateSharesLabel(searchRecent(stockData.getText().split("\n")[0].split(" ")[1]));
					} catch (NumberFormatException | IOException e1) {}

				}

			}
		});
		this.add(sellButton);

		sharesLabel = new JLabel("Shares: ");
		sharesLabel.setBounds(630, 202, 200, 30);
		this.add(sharesLabel);

		JLabel stockHistoryDataLabel = new JLabel("Historic Stock Data");
		stockHistoryDataLabel.setBounds(500, 350, 200, 30);
		this.add(stockHistoryDataLabel);

		stockHistoryData = new JTextArea();
		stockHistoryData.setBounds(500, 380, 270, 170);
		this.add(stockHistoryData);

		JButton hourGraph = new JButton("Week");
		hourGraph.setBounds(630, 240, 140, 30);
		hourGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentStockDataList != null) {
					length = 8;
					stockGraph.setStock(currentStockDataList, length);
					stockHistoryData.setText(updateGraphData());
				}
			}
		});
		this.add(hourGraph);

		JButton todayGraph = new JButton("Month");
		todayGraph.setBounds(630, 280, 140, 30);
		todayGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentStockDataList != null) {
					length = 31;
					stockGraph.setStock(currentStockDataList, length);
					stockHistoryData.setText(updateGraphData());
				}
			}
		});
		this.add(todayGraph);

		JButton weekGraph = new JButton("Maximum History");
		weekGraph.setBounds(630, 320, 140, 30);
		weekGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentStockDataList != null) {
					length = currentStockDataList.size();
					stockGraph.setStock(currentStockDataList, length);
					stockHistoryData.setText(updateGraphData());
				}
			}
		});
		this.add(weekGraph);

		JLabel stockDataLabel = new JLabel("Daily Stock Data");
		stockDataLabel.setBounds(220, 350, 200, 30);
		this.add(stockDataLabel);

		stockData = new JTextArea();
		stockData.setBounds(220, 380, 270, 170);
		this.add(stockData);

		recentPanel = new JPanel();
		recentPanel.setBounds(10, 70, 200, 480);
		recentPanel.setBackground(Color.LIGHT_GRAY);
		this.add(recentPanel);

		JLabel stockListLabel = new JLabel("Recently Viewed");
		stockListLabel.setBounds(60, 40, 200, 30);
		this.add(stockListLabel);

		searchButton = new JButton("Search");
		searchButton.setBounds(630, 10, 140, 30);
		searchButton.addActionListener(new ActionListener() {

                        // When clicked, creates a new thread that handles requesting stock
			public void actionPerformed(ActionEvent e) {

				Stock stock = null;
				List<StockData> stockDataList = null;
				SearchThread searchThread = new SearchThread(rh, stock, stockDataList, searchBar.getText(), getThis());
				searchThread.start();

			}

		});
		this.add(searchButton);

	}

	public MarketPanel getThis() {
		return this;
	}

	public String updateData(Stock stock) {

		String out = "";

		out += "Symbol: " + stock.getSymbol() 
		+ "\n\nCurrent Price: $" + stock.getPrice() 
		+ "\nClose Price: $" + stock.getClosePrice()  
		+ "\nOpen Price: $" + stock.getOpenPrice()
		+ "\nChange: $" + stock.getChange()
		+ "\nChange Percent: %" + stock.getChangePercent();


		return out;
	}

	public String updateGraphData() {

		String out = "";

		out += length-1 + " Stock Entries From:\n" 
				+ getDatetoString(currentStockDataList.get(length-1)) + " to " 
				+ getDatetoString(currentStockDataList.get(0))
				+ "\n\n" + getLowtoString()
				+ "\n" + getHightoString()
				+ "\n" + getMaxChangetoString();

		return out;
	}

	public String getMaxChangetoString() {

		double min = currentStockDataList.get(0).getAdjustedClose();
		double max = currentStockDataList.get(length-1).getAdjustedClose();

		for(int i = 0; i < length; i++) {

			if(currentStockDataList.get(i).getAdjustedClose() > max) max = currentStockDataList.get(i).getAdjustedClose();
			else if(currentStockDataList.get(i).getAdjustedClose() < min) min = currentStockDataList.get(i).getAdjustedClose();

		}

		return "Maximum Change: " + new DecimalFormat("0.00").format(max - min);
	}

	public String getDatetoString(StockData stock) {

		String out = "";

		out += stock.getDateTime().getDayOfMonth() + "/" + stock.getDateTime().getMonthValue() + "/" + stock.getDateTime().getYear();

		return out;	
	}

	public String getHightoString() {

		StockData out = currentStockDataList.get(0);

		for(int i = 0; i < length; i++) {

			if(currentStockDataList.get(i).getAdjustedClose() > out.getAdjustedClose()) out = currentStockDataList.get(i);

		}

		return "High " + out.getAdjustedClose() + " at " +  getDatetoString(out);
	}

	public String getLowtoString() {

		StockData out = currentStockDataList.get(0);

		for(int i = 0; i < length; i++) {

			if(currentStockDataList.get(i).getAdjustedClose() < out.getAdjustedClose()) out = currentStockDataList.get(i);

		}

		return "Low " + new DecimalFormat("0.00").format(out.getAdjustedClose()) + " at " + getDatetoString(out);
	}

	public void updateRecentStock() {

		for(int i = recentList.size()-1; i >= 0; i--) {

			recentList.get(i).setBounds(10, 40 + (recentList.size() - i) * 30, 200, 30);
			if (recentList.get(i).stock.getChange().floatValue() > 0) {
				recentList.get(i).setBackground(new Color(80 - 5 * i, 255 - 15 * i, 80 - 15 * i));
			}
			else {
				recentList.get(i).setBackground(new Color(255 - 15 * i, 80 - 15 * i, 80 - 15 * i));
			}

		}

		this.add(recentList.get(recentList.size()-1));

		recentPanel.setBounds(10, 70 + recentList.size() * 30, 200, 480 - recentList.size() * 30);

	}

	public boolean containsStock(Stock stock) {

		for(StockPanel sp : recentList) {

			if(sp.symbol.getText().equals(stock.getSymbol())) return true;

		}

		return false;
	}

	public Stock searchRecent(String symbol) {

		for(StockPanel sp : recentList) {

			if(sp.symbol.getText().equals(symbol)) return sp.stock;

		}

		return null;
	}

	public void createPanel(Stock stock, List<StockData> stockDataList) {
		recentList.add(new StockPanel(stock, stockDataList, this));
	}
	
	public void createBoughtPanel(StockHeld stock, List<StockData> stockDataList) {
		recentList.add(new StockPanel(stock, stockDataList, this));
	}

        // creates new recent stock object adds it to recent list
	public void updatePanel(Stock stock, List<StockData> stockDataList) {

		if(stock != null && stockDataList != null) {

			stockData.setText(updateData(stock));

			length = 8;

			currentStockDataList = stockDataList;

			stockGraph.setStock(stockDataList, 8);
			stockHistoryData.setText(updateGraphData());

			if(!containsStock(stock)) {
				createPanel(stock, stockDataList);
			}

			updateRecentStock();

			updateSharesLabel(stock);

		}
		else {
			stockData.setText("");
			JOptionPane.showMessageDialog(new JFrame(), "Could not Retrieve Stock. \nPlease try again.");
		}

	}

	public void updateFromRecent(Stock stock, List<StockData> stockDataList) {

		stockData.setText(updateData(stock));
		this.length = 8;

		if(stockDataList != null) {
			stockGraph.setStock(stockDataList, 8);
			stockHistoryData.setText(updateGraphData());
			currentStockDataList = stockDataList;
		}

		updateSharesLabel(stock);

	}

	public void updateSharesLabel(Stock stock) {

		if(pf.getStock(stock.getSymbol()) != null) {
			sharesLabel.setText("Shares: " + pf.getStock(stock.getSymbol()).getAmount());
		}
		else sharesLabel.setText("Shares: 0");

	}

}
