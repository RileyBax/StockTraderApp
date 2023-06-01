
import io.github.mainstringargs.alphavantagescraper.AlphaVantageConnector;
import io.github.mainstringargs.alphavantagescraper.StockQuotes;
import io.github.mainstringargs.alphavantagescraper.output.quote.data.StockQuote;


public class RequestHandler extends StockQuotes {

	static String apiKey = "ATD3OP6WNYPCRAEY";
	
	public RequestHandler() {
		
		super(new AlphaVantageConnector(apiKey, 3000));
		
	}
	
	public Stock get(String input) {
		
		StockQuote stock = this.quote(input).getStockQuote();
		
		return new Stock(stock);
		
	}
	
}
