import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import io.github.mainstringargs.alphavantagescraper.output.quote.data.StockQuote;
import io.github.mainstringargs.alphavantagescraper.output.timeseries.data.StockData;

public class Stock {

	private String symbol;
	private BigDecimal price;
	private BigDecimal closePrice;
	private BigDecimal openPrice;
	private BigDecimal change;
	private BigDecimal changePercent;
	private List<StockData> history;
	public RequestHandler rh = new RequestHandler();

	// stores stock information
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(BigDecimal closePrice) {
		this.closePrice = closePrice;
	}
	
	public BigDecimal getOpenPrice() {
		return openPrice;
	}
	
	public void setOpenPrice(BigDecimal openPrice) {
		this.openPrice = openPrice;
	}

	public BigDecimal getChange() {
		return change;
	}

	public void setChange(BigDecimal change) {
		this.change = change;
	}

	public BigDecimal getChangePercent() {
		return changePercent;
	}

	public void setChangePercent(BigDecimal changePercent) {
		this.changePercent = changePercent;
	}
	
	public List<StockData> getHistory(){
		return history;
	}
	
	public void setHistory(List<StockData> history) {
		this.history = history;
	}

	public Stock(StockQuote stock) {

		this.symbol = stock.getSymbol();
		this.price =  BigDecimal.valueOf(stock.getPrice());
		this.closePrice = BigDecimal.valueOf(stock.getPreviousClose());
		this.openPrice = BigDecimal.valueOf(stock.getOpen());
		this.change = BigDecimal.valueOf(stock.getChange());
		this.changePercent = BigDecimal.valueOf(stock.getChangePercent());

	}

	public Stock(Stock stock) {

		this.symbol = stock.symbol;
		this.price = stock.price;
		this.closePrice = stock.closePrice;
		this.openPrice = stock.openPrice;
		this.change = stock.getChange();
		this.changePercent = stock.getChangePercent();
		
		this.history = stock.getHistory();

	}

	@Override
	public String toString() {

		return "Symbol: " + this.symbol + "\nCurrent Price: " + this.price + "\nPrevious Close: " + this.closePrice + "\n";

	}

	public float getCurrentPrice() throws IOException {

		return rh.get(this.getSymbol()).getPrice().floatValue();

	}
	
}
