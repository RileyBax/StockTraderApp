import java.io.IOException;
import java.math.BigDecimal;

public class Stock {

	private String symbol;
	private String name;
	private String currency;
	private BigDecimal price;
	private BigDecimal closePrice;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public Stock(yahoofinance.Stock stock) {

		this.symbol = stock.getSymbol();
		this.name = stock.getName();
		this.currency = stock.getCurrency();
		this.price = stock.getQuote().getPrice();
		this.closePrice = stock.getQuote().getPreviousClose();

	}

	public Stock(Stock stock) {

		this.symbol = stock.symbol;
		this.name = stock.name;
		this.currency = stock.currency;
		this.price = stock.price;
		this.closePrice = stock.closePrice;

	}

	@Override
	public String toString() {

		return "Name: " + this.name + "\nSymbol: " + this.symbol + "\nCurrency: " + this.currency + "\nCurrent Price: " + this.price + "\nPrevious Close: " + this.closePrice + "\n";

	}

	public float getCurrentPrice() throws IOException {

		return RequestHandler.get(getSymbol()).getQuote().getPrice().floatValue();

	}

}
