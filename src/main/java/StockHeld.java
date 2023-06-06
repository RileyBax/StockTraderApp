
import java.time.LocalDateTime;

import io.github.mainstringargs.alphavantagescraper.output.quote.data.StockQuote;

public class StockHeld extends Stock{

	private float amount;
	private float pricePaid;
	private float priceBoughtAt;
	private LocalDateTime date;

	public StockHeld(Stock stock, float pricePaid) {
		
		super(stock);
		
		this.pricePaid = pricePaid;
		priceBoughtAt = stock.getPrice().floatValue();
		amount = pricePaid / stock.getPrice().floatValue();
		date = LocalDateTime.now();
		
	}
	
	// CONSTUCTOR for when read from file
	public StockHeld(StockQuote stock, float amount, float pricePaid, float priceBoughtAt) {
		
		super(stock);

		this.amount = amount;
		this.pricePaid = pricePaid;
		this.priceBoughtAt = priceBoughtAt;
		
	}
	
	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getPricePaid() {
		return pricePaid;
	}

	public void setPricePaid(float pricePaid) {
		this.pricePaid = pricePaid;
	}

	public float getPriceBoughtAt() {
		return priceBoughtAt;
	}

	public void setPriceBoughtAt(float priceBoughtAt) {
		this.priceBoughtAt = priceBoughtAt;
	}
	
	public LocalDateTime getDate() {
		return date;
	}

	@Override
	public String toString() {
		
		return super.toString() + "Shares Held: " + amount + "\nPrice of Shares Held: " + getPrice().floatValue() * amount + "\n";
		
	}
	
	public float thisChange() {
		return this.getPrice().floatValue() - this.getPriceBoughtAt();
	}
	
}
