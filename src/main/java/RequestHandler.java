
import java.util.ArrayList;
import java.util.List;

import io.github.mainstringargs.alphavantagescraper.AlphaVantageConnector;
import io.github.mainstringargs.alphavantagescraper.StockQuotes;
import io.github.mainstringargs.alphavantagescraper.TimeSeries;
import io.github.mainstringargs.alphavantagescraper.input.timeseries.Interval;
import io.github.mainstringargs.alphavantagescraper.input.timeseries.OutputSize;
import io.github.mainstringargs.alphavantagescraper.output.AlphaVantageException;
import io.github.mainstringargs.alphavantagescraper.output.quote.data.StockQuote;
import io.github.mainstringargs.alphavantagescraper.output.timeseries.TimeSeriesResponse;
import io.github.mainstringargs.alphavantagescraper.output.timeseries.data.StockData;


public class RequestHandler extends StockQuotes {

	static String apiKey = "ATD3OP6WNYPCRAEY";
	static TimeSeries timeSeries;
	
	public RequestHandler() {
		
		super(new AlphaVantageConnector(apiKey, 3000));
		timeSeries = new TimeSeries(new AlphaVantageConnector(apiKey, 3000));
		
	}
	
	public Stock get(String input) {
		
		try {
			StockQuote stock = this.quote(input).getStockQuote();
			return new Stock(stock);
		}
		catch(NullPointerException | AlphaVantageException e) {
			return null;
		}

	}
	
	public List<StockData> getHistory(String input, int length) {
		
		ArrayList<StockData> out = new ArrayList<StockData>();
		
		try {
			
			if(length == 1) {
				TimeSeriesResponse response = timeSeries.dailyAdjusted(input);
				List<StockData> stockData = response.getStockData();
				for(int i = 0; i < 7; i++) {
					out.add(stockData.get(i));
				}
				return out;
			}
			else if(length == 2) {
				TimeSeriesResponse response = timeSeries.dailyAdjusted(input);
				List<StockData> stockData = response.getStockData();
				for(int i = 0; i < 30; i++) {
					out.add(stockData.get(i));
				}
				return out;
			}
			else if(length == 3) {
				TimeSeriesResponse response = timeSeries.monthlyAdjusted(input);
				List<StockData> stockData = response.getStockData();
				for(int i = 0; i < 12; i++) {
					out.add(stockData.get(i));
				}
				return out;
			}
			else {
				return null;
			}
		}
		catch(NullPointerException | AlphaVantageException e) {
			return null;
		}
		
	}
	
}
