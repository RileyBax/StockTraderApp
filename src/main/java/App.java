/* TODO:
 * plan gui layout - 2 panels, search and current stock data on first, portfolio info on second, JScrollBar
 * create new request handler - multi thread
 * 5 api calls per min, create queue and make calls when possible?
 * new stock and stock held classes
 * new portfolio class
 * get stock method
*/


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import io.github.mainstringargs.alphavantagescraper.AlphaVantageConnector;
import io.github.mainstringargs.alphavantagescraper.StockQuotes;
import io.github.mainstringargs.alphavantagescraper.output.quote.StockQuotesResponse;

public class App extends JFrame{

	static StockQuotesResponse stock;

	static String apiKey = "ATD3OP6WNYPCRAEY";

	static StockQuotes client = new StockQuotes(new AlphaVantageConnector(apiKey, 3000));

	public App() {

		this.setBounds(0, 0, 800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);

		JPanel stockList = new JPanel();
		stockList.setBounds(10, 50, 200, 500);
		stockList.setBackground(Color.GRAY);
		stockList.add(new JLabel("List of Stocks"));
		this.add(stockList);
		
		JPanel stockGraph = new JPanel();
		stockGraph.setBounds(220, 50, 550, 300);
		stockGraph.setBackground(Color.DARK_GRAY);
		stockGraph.add(new JLabel("Stock History Graph"));
		this.add(stockGraph);
		
		JPanel stockData = new JPanel();
		stockData.setBounds(220, 360, 550, 190);
		stockData.setBackground(Color.LIGHT_GRAY);
		stockData.add(new JLabel("Stock Info"));
		this.add(stockData);
		
		JPanel portfolioButton = new JPanel();
		portfolioButton.setBounds(10, 10, 200, 30);
		portfolioButton.setBackground(Color.LIGHT_GRAY);
		portfolioButton.add(new JLabel("Portfolio Button"));
		this.add(portfolioButton);
		
		JPanel stockName = new JPanel();
		stockName.setBounds(220, 10, 400, 30);
		stockName.setBackground(Color.GRAY);
		stockName.add(new JLabel("Stock Symbol Search Bar"));
		this.add(stockName);
		
		JPanel stockSearch = new JPanel();
		stockSearch.setBounds(630, 10, 140, 30);
		stockSearch.setBackground(Color.LIGHT_GRAY);
		stockSearch.add(new JLabel("Search Button"));
		this.add(stockSearch);

		this.setVisible(true);

	}

	public static void main(String[] args) throws InterruptedException {

		//Thread.sleep(3000);

		App frame = new App();


	}

}
