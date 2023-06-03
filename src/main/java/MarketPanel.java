
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.Format;
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

	ArrayList<Stock> recentList;
	List<StockData> stockDataList;
	RequestHandler rh;
	int length = 8;

	public MarketPanel(final RequestHandler rh) {

		this.rh = rh;
		recentList = new ArrayList<Stock>();

		this.setBounds(0, 0, 800, 600);
		this.setLayout(null);

		Graph stockGraph = new Graph();
		this.add(stockGraph);

		JLabel graphLabel = new JLabel("Price History Graph");
		graphLabel.setBounds(370, 40, 200, 30);
		this.add(graphLabel);

		final JTextField searchBar = new JTextField();
		searchBar.setBounds(220, 10, 400, 30);
		this.add(searchBar);

		JTextField buyField = new JTextField();
		buyField.setBounds(630, 100, 140, 30);
		this.add(buyField);

		JButton buyButton = new JButton("Buy");
		buyButton.setBounds(630, 70, 140, 30);
		this.add(buyButton);

		JTextField sellField = new JTextField();
		sellField.setBounds(630, 170, 140, 30);
		this.add(sellField);

		JButton sellButton = new JButton("Sell");
		sellButton.setBounds(630, 140, 140, 30);
		this.add(sellButton);
		
		JLabel sharesLabel = new JLabel("Shares: ");
		sharesLabel.setBounds(630, 202, 200, 30);
		this.add(sharesLabel);

		JLabel stockHistoryDataLabel = new JLabel("Historic Stock Data");
		stockHistoryDataLabel.setBounds(500, 350, 200, 30);
		this.add(stockHistoryDataLabel);

		final JTextArea stockHistoryData = new JTextArea();
		stockHistoryData.setBounds(500, 380, 270, 170);
		this.add(stockHistoryData);

		JButton hourGraph = new JButton("Week");
		hourGraph.setBounds(630, 240, 140, 30);
		hourGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(stockDataList != null) {
					length = 8;
					stockGraph.setStock(stockDataList, length);
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
				if(stockDataList != null) {
					length = 31;
					stockGraph.setStock(stockDataList, length);
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
				if(stockDataList != null) {
					length = stockDataList.size();
					stockGraph.setStock(stockDataList, length);
					stockHistoryData.setText(updateGraphData());
				}
			}
		});
		this.add(weekGraph);

		JButton pButton = new JButton("Portfolio");
		pButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		pButton.setBounds(10, 10, 200, 30);
		this.add(pButton);

		JLabel stockDataLabel = new JLabel("Daily Stock Data");
		stockDataLabel.setBounds(220, 350, 200, 30);
		this.add(stockDataLabel);

		final JTextArea stockData = new JTextArea();
		stockData.setBounds(220, 380, 270, 170);
		this.add(stockData);

		// create set class for recent stocks, no duplicate labels.
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
					length = 8;

					for(int i = 0; i < recentList.size(); i++) {

						recentStock[i].setBackground(new Color(255 - 15 * i, 255 - 15 * i, 255 - 15 * i));
						if(recentStockLabel[i].getText().isEmpty()) {
							recentStockLabel[i].setText(stock.getSymbol());
						}
						recentStock[i].setLocation(10, 70 + ((recentList.size() - 1) * 30 - i * 30));

					}

					stockData.setText(updateData(stock));

					// update graph
					stockDataList = rh.getHistory(searchBar.getText());
					if(stockDataList != null) {
						stockGraph.setStock(stockDataList, 8);
						stockHistoryData.setText(updateGraphData());
					}

				}
				else {
					stockData.setText("");
					JOptionPane.showMessageDialog(new JFrame(), "Could not Retrieve Stock. \nPlease try again.");
				}

			}

		});
		this.add(searchButton);

	}

	public String updateData(Stock stock) {

		String out = "";

		out += "Symbol: " + stock.getSymbol() 
		+ "\n\nCurrent Price: " + stock.getPrice() 
		+ "\nClose Price: " + stock.getClosePrice()  
		+ "\nOpen Price: " + stock.getOpenPrice()
		+ "\nChange: " + stock.getChange()
		+ "\nChange Percent: %" + stock.getChangePercent();


		return out;
	}

	public String updateGraphData() {

		String out = "";

		out += length-1 + " Stock Entries From:\n" 
				+ getDatetoString(stockDataList.get(length-1)) + " to " 
				+ getDatetoString(stockDataList.get(0))
				+ "\n\n" + getLowtoString()
				+ "\n" + getHightoString()
				+ "\n" + getMaxChangetoString();

		return out;
	}

	public String getMaxChangetoString() {

		double min = stockDataList.get(0).getAdjustedClose();
		double max = stockDataList.get(length-1).getAdjustedClose();

		for(StockData sd : stockDataList) {

			if(sd.getAdjustedClose() > max) max = sd.getAdjustedClose();
			else if(sd.getAdjustedClose() < min) min = sd.getAdjustedClose();

		}

		return "Maximum Change: " + new DecimalFormat("0.00").format(max - min);
	}

	public String getDatetoString(StockData stock) {

		String out = "";

		out += stock.getDateTime().getDayOfMonth() + "/" + stock.getDateTime().getMonthValue() + "/" + stock.getDateTime().getYear();

		return out;	
	}

	public String getHightoString() {

		StockData out = stockDataList.get(0);

		for(StockData sd : stockDataList) {

			if(sd.getAdjustedClose() > out.getAdjustedClose()) out = sd;

		}

		return "High " + out.getAdjustedClose() + " at " +  getDatetoString(out);
	}

	public String getLowtoString() {

		StockData out = stockDataList.get(0);

		for(StockData sd : stockDataList) {

			if(sd.getAdjustedClose() < out.getAdjustedClose()) out = sd;

		}

		return "Low " + out.getAdjustedClose() + " at " + getDatetoString(out);
	}

}
