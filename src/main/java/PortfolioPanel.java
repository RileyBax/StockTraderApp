import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import io.github.mainstringargs.alphavantagescraper.output.AlphaVantageException;

public class PortfolioPanel extends JPanel{

	RequestHandler rh;
	Portfolio pf;
	JPanel ownedPanel;
	int length = 8;
	PortfolioGraph stockGraph;
	StockHeld selectedStock;
	JTextArea stockData;
	JTextArea userStockData;
	JButton updateButton;
	UpdatePanel updatePanel;
	JLabel userInfo;
	Database db;

	public PortfolioPanel(final RequestHandler rh, final Portfolio pf) {

		this.rh = rh;
		this.pf = pf;
		try {
			db = new Database();
		} catch (SQLException e) {}
		this.setOpaque(false);

		this.setBounds(0, 0, 800, 600);
		this.setLayout(null);

		JLabel ownedLabel = new JLabel("Currently owned Shares");
		ownedLabel.setBounds(40, 40, 200, 30);
		this.add(ownedLabel);

		ownedPanel = new JPanel();
		ownedPanel.setBounds(10, 70, 200, 480);
		ownedPanel.setBackground(Color.LIGHT_GRAY);
		this.add(ownedPanel);

		JLabel graphLabel = new JLabel("Price History Graph");
		graphLabel.setBounds(370, 40, 200, 30);
		this.add(graphLabel);

		stockGraph = new PortfolioGraph();
		this.add(stockGraph);

		JButton hourGraph = new JButton("Week");
		hourGraph.setBounds(630, 240, 140, 30);
		hourGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				length = 8;
				updatePanel();
			}
		});
		this.add(hourGraph);

		JButton todayGraph = new JButton("Month");
		todayGraph.setBounds(630, 280, 140, 30);
		todayGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				length = 31;
				updatePanel();
			}
		});
		this.add(todayGraph);

		JButton weekGraph = new JButton("Maximum History");
		weekGraph.setBounds(630, 320, 140, 30);
		weekGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pf.stockList.size() > 0) {
					length = search(selectedStock).getHistory().size();
				}
				else {
					length = 8;
				}
				updatePanel();

			}
		});
		this.add(weekGraph);

		JLabel stockDataLabel = new JLabel("Current Day Stock Data");
		stockDataLabel.setBounds(220, 350, 200, 30);
		this.add(stockDataLabel);

		stockData = new JTextArea();
		stockData.setBounds(220, 380, 270, 170);
		this.add(stockData);

		JLabel userStockDataLabel = new JLabel("Your Investment Data");
		userStockDataLabel.setBounds(500, 350, 200, 30);
		this.add(userStockDataLabel);

		userStockData = new JTextArea();
		userStockData.setBounds(500, 380, 270, 170);
		this.add(userStockData);

		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(220, 10, 400, 30);
		infoPanel.setBackground(Color.LIGHT_GRAY);
		this.add(infoPanel);

		userInfo = new JLabel("Profile: " + pf.name);
		userInfo.setBounds(0, 0, 400, 30);
		infoPanel.add(userInfo);

		JButton saveButton = new JButton("Save Portfolio");
		saveButton.setBounds(630, 10, 140, 30);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				db.saveProfile(pf);

			}
		});
		this.add(saveButton);

		// new thread takes held stock list continues repainting until thread ends
		updateButton = new JButton("Update Stocks");
		updateButton.setBounds(630, 70, 140, 30);
		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				UpdateThread updateThread = new UpdateThread(rh, getThis());
				updateThread.stockList = pf.stockList;
				updateThread.start();

			}
		});
		this.add(updateButton);

		// LOADING INFO IN THIS PANEL WHEN UPDATING
		updatePanel = new UpdatePanel();
		this.add(updatePanel);

	}

	public synchronized void getUser() throws SQLException {

		ResultSet rs = db.getData(pf.name);

		ArrayList<StartStock> startList = new ArrayList<StartStock>();

		while(rs.next()) {

			startList.add(new StartStock(rs.getString("symbol"), rs.getString("amount"), rs.getString("pricePaid"), rs.getString("priceBoughtAt")));

		}

		if(startList.size() > 0) {

			int size = startList.size();

			for(int i = 0; i < size; i++) {

				while(pf.stockList.size() != i + 1) {

					try {
						pf.stockList.add(new StockHeld(rh.get(startList.get(i).symbol), startList.get(i).amount, startList.get(i).pricePaid, startList.get(i).priceBoughtAt));
					}
					catch(NullPointerException | AlphaVantageException e){
						try {
							this.wait(5000);
						} catch (InterruptedException e1) {}
					}

				}

			}
			
			for(int i = 0; i < size; i++) {
				
				while(pf.stockList.get(i).getHistory() == null) {
					
					try {
						pf.stockList.get(i).setHistory(rh.getHistory(startList.get(i).symbol));
					}
					catch(NullPointerException | AlphaVantageException e){
						try {
							this.wait(5000);
						} catch (InterruptedException e1) {}
					}
					
				}
				
			}

		}

	}

	public PortfolioPanel getThis() {
		return this;
	}

	public void updatePanel() {

		// update stock list
		StockHeldPanel temp;

		// TODO: if stocklist has more than one temp, add an index number to each
		for(int i = 0; i < pf.stockList.size(); i++) {

			temp = new StockHeldPanel(pf.stockList.get(i), this);
			temp.setBounds(10, 70 + i * 30, 200, 30);

			if(pf.stockList.get(i).thisChange() > 0) temp.setBackground(new Color(80 - 5 * i, 255 - 15 * i, 80 - 15 * i));
			else if(pf.stockList.get(i).thisChange() < 0) temp.setBackground(new Color(255 - 5 * i, 80 - 15 * i, 80 - 15 * i));
			else temp.setBackground(new Color(255 - 15 * i, 255 - 15 * i, 255 - 15 * i));

			this.add(temp);

		}

		ownedPanel.setBounds(10, 70 + pf.stockList.size() * 30, 200, 480 - pf.stockList.size() * 30);

		if(selectedStock == null && pf.stockList.size() > 0) {
			this.selectedStock = pf.stockList.get(0);
		}

		// stock list is null exception?
		if(pf.stockList.size() > 0) {

			stockGraph.setStock(pf.stockList.get(getIndex(selectedStock)).getHistory(), length, pf.stockList.get(getIndex(selectedStock)).getPriceBoughtAt());

			// update stockData
			stockData.setText("Symbol: " + selectedStock.getSymbol()
			+ "\n\nCurrent Price: $" + selectedStock.getPrice() 
			+ "\nClose Price: $" + selectedStock.getClosePrice()  
			+ "\nOpen Price: $" + selectedStock.getOpenPrice()
			+ "\nChange: $" + selectedStock.getChange()
			+ "\nChange Percent: %" + selectedStock.getChangePercent());

			StockHeld tempStock = pf.getStock(selectedStock.getSymbol());

			userStockData.setText("Amount Spent: $" + tempStock.getPricePaid()
			+ "\nPrice Bought At: " + tempStock.getPriceBoughtAt()
			+ "\nShares Owned: " + tempStock.getAmount()
			+ "\nCurrent Price of Shares: $" + tempStock.getAmount() * tempStock.getPrice().floatValue()
			+ "\nProfit/Loss: $" + tempStock.thisChange());

		}

	}



	public StockHeld search(StockHeld stock) {

		StockHeld out = null;

		for(StockHeld sh : pf.stockList) if (sh.getSymbol().equals(stock.getSymbol())) out = sh;

		return out;
	}

	public int getIndex(StockHeld stock) {

		int out = 0;

		for(int i = 0; i < pf.stockList.size(); i++) if (pf.stockList.get(i).getSymbol().equals(stock.getSymbol())) out = i;

		return out;

	}

	public void updateName() {
		userInfo.setText("Profile: " + pf.name);
	}

}
