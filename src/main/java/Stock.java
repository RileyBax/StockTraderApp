import java.io.IOException;
import java.math.BigDecimal;

import io.github.mainstringargs.alphavantagescraper.output.quote.data.StockQuote;

public class Stock {

	private String symbol;
	private BigDecimal price;
	private BigDecimal closePrice;
	private BigDecimal openPrice;
	public RequestHandler rh = new RequestHandler();

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

	public Stock(StockQuote stock) {

		this.symbol = stock.getSymbol();
		this.price =  BigDecimal.valueOf(stock.getPrice());
		this.closePrice = BigDecimal.valueOf(stock.getPreviousClose());
		this.openPrice = BigDecimal.valueOf(stock.getOpen());

	}

	public Stock(Stock stock) {

		this.symbol = stock.symbol;
		this.price = stock.price;
		this.closePrice = stock.closePrice;
		this.openPrice = stock.openPrice;

	}

	@Override
	public String toString() {

		return "Symbol: " + this.symbol + "\nCurrent Price: " + this.price + "\nPrevious Close: " + this.closePrice + "\n";

	}

	public float getCurrentPrice() throws IOException {

		return rh.get(this.getSymbol()).getPrice().floatValue();

	}
	
}
