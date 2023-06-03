import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PortfolioPanel extends JPanel{

	RequestHandler rh;
	
	public PortfolioPanel(RequestHandler rh) {
		
		this.rh = rh;
		this.setOpaque(false);

		this.setBounds(0, 0, 800, 600);
		this.setLayout(null);
		
		JLabel ownedLabel = new JLabel("Currently owned Shares");
		ownedLabel.setBounds(40, 40, 200, 30);
		this.add(ownedLabel);
		
		JPanel ownedPanel = new JPanel();
		ownedPanel.setBounds(10, 70, 200, 480);
		ownedPanel.setBackground(Color.LIGHT_GRAY);
		this.add(ownedPanel);
		
		JLabel graphLabel = new JLabel("Price History Graph");
		graphLabel.setBounds(370, 40, 200, 30);
		this.add(graphLabel);
		
		// make new owned graph class
		Graph stockGraph = new Graph();
		this.add(stockGraph);
		
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
		updatePanel.setBounds(630, 110, 140, 240);
		updatePanel.setBackground(Color.LIGHT_GRAY);
		this.add(updatePanel);
		
	}
	
}
