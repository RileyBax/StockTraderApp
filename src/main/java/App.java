import java.util.List;

import javax.swing.JFrame;

import io.github.mainstringargs.alphavantagescraper.AlphaVantageConnector;
import io.github.mainstringargs.alphavantagescraper.TimeSeries;
import io.github.mainstringargs.alphavantagescraper.input.timeseries.Interval;
import io.github.mainstringargs.alphavantagescraper.output.timeseries.data.StockData;

public class App extends JFrame{

	public App() {
		
		this.setBounds(0, 0, 800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		
		App frame = new App();
		
		String apiKey = "ATD3OP6WNYPCRAEY";
		
		TimeSeries client = new TimeSeries(new AlphaVantageConnector(apiKey, 3000));
		
		List<StockData> data =  client.intraDay("AAPL", Interval.ONE_MIN).getStockData();
		
		for(StockData s : data) {
			System.out.println(s.getDateTime());
		}
		
	}

}
