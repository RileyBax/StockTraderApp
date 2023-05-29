import java.io.IOException;
import java.math.BigDecimal;

public class StockHeld extends Stock{
	
	private float amount;
	private float pricePaid;
	private float priceBoughtAt;

	public StockHeld(Stock stock, float pricePaid) {
		
		super(stock);
		
		this.pricePaid = pricePaid;
		priceBoughtAt = stock.getPrice().floatValue();
		amount = pricePaid / stock.getPrice().floatValue();
		
	}
	
	// CONSTUCTOR for when read from file
	public StockHeld(yahoofinance.Stock stock, float amount, float pricePaid, float priceBoughtAt) {
		
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

	@Override
	public String toString() {
		
		try {
			setPrice(BigDecimal.valueOf(getCurrentPrice()));
		} catch (IOException e) {}
		
		return super.toString() + "Shares Held: " + amount + "\nPrice of Shares Held: " + getPrice().floatValue() * amount + " " + getCurrency() + "\n";
		
	}

}
