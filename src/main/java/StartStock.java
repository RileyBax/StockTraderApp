
public class StartStock {

	String symbol;
	float amount;
	float pricePaid;
	float priceBoughtAt;
	
	// Temporary object used to store data  from database
	public StartStock(String symbol, String amount, String pricePaid, String priceBoughtAt) {
		
		this.symbol = symbol;
		this.amount = Float.parseFloat(amount);
		this.pricePaid = Float.parseFloat(pricePaid);
		this.priceBoughtAt = Float.parseFloat(priceBoughtAt);
		
	}
	
}
