import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PortfolioPanel extends JPanel{

	RequestHandler rh;
	Portfolio pf;
	JPanel ownedPanel;
	int length = 8;
	PortfolioGraph stockGraph;
	StockHeld selectedStock;
	
	public PortfolioPanel(RequestHandler rh, Portfolio pf) {
		
		this.rh = rh;
		this.pf = pf;
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
		
		// make new owned graph class
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
				length = search(selectedStock).getHistory().size();
				updatePanel();
			}
		});
		this.add(weekGraph);
		
		JLabel stockDataLabel = new JLabel("Stock Data");
		stockDataLabel.setBounds(220, 350, 200, 30);
		this.add(stockDataLabel);
		
		JTextArea stockData = new JTextArea();
		stockData.setBounds(220, 380, 550, 170);
		this.add(stockData);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(220, 10, 400, 30);
		infoPanel.setBackground(Color.LIGHT_GRAY);
		this.add(infoPanel);
		
		JLabel userInfo = new JLabel("Profile: NAME     Profit: CHANGE     Amount Spent: NUMBER");
		userInfo.setBounds(0, 0, 400, 30);
		infoPanel.add(userInfo);
		
		JButton saveButton = new JButton("Save Portfolio");
		saveButton.setBounds(630, 10, 140, 30);
		this.add(saveButton);
		
		// new thread takes held stock list continues repainting until thread ends
		JButton updateButton = new JButton("Update Stocks");
		updateButton.setBounds(630, 70, 140, 30);
		this.add(updateButton);
		
		JPanel updatePanel = new JPanel();
		updatePanel.setBounds(630, 110, 140, 120);
		updatePanel.setBackground(Color.LIGHT_GRAY);
		this.add(updatePanel);
		
	}
	
	public void updatePanel() {
		
		// update stock list
		StockHeldPanel temp;
		
		for(int i = 0; i < pf.stockList.size(); i++) {
			
			temp = new StockHeldPanel(pf.stockList.get(i), this);
			temp.setBounds(10, 70 + i * 30, 200, 30);
			
			if(pf.stockList.get(i).getChange().floatValue() > 0) temp.setBackground(new Color(80 - 5 * i, 255 - 15 * i, 80 - 15 * i));
			else temp.setBackground(new Color(255 - 5 * i, 80 - 15 * i, 80 - 15 * i));
			
			this.add(temp);
			
		}
		
		ownedPanel.setBounds(10, 70 + pf.stockList.size() * 30, 200, 480 - pf.stockList.size() * 30);
		
		if(selectedStock == null && pf.stockList.size() > 0) {
			this.selectedStock = pf.stockList.get(0);
		}
		
		stockGraph.setDateBoughtAt(selectedStock);
		
		// stock list is null
		if(pf.stockList.size() > 0) {
			
			stockGraph.setStock(pf.stockList.get(getIndex(selectedStock)).getHistory(), length);
			
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
	
}
