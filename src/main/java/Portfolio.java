import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {

	String name;
	List<StockHeld> stockList;
	float change;
	
	// create new constructor for reading from DB
	
	public Portfolio(String name) {
		
		this.name = name;
		stockList = new ArrayList<StockHeld>();
		change = 0.0f;
		
	}
	
	public void add(Stock stock, float pricePaid) {

		stockList.add(new StockHeld(stock, pricePaid));

	}
	
	public void sell(StockHeld sh, float sellAmount) throws IOException {

		if(sh.getAmount() >= sellAmount) {

			change += sh.getPricePaid() * sellAmount - sh.getPriceBoughtAt() * sellAmount;
			sh.setPricePaid(sh.getPricePaid() - (sellAmount / sh.getAmount()) * sh.getPricePaid());
			sh.setAmount(sh.getAmount() - sellAmount);


		}
		else {

			System.out.print("Cannot sell more than is owned");

		}

		if(sh.getAmount() == 0) {

			stockList.remove(sh);

		}

	}
	
	public StockHeld getStock(String stockSymbol) {

		StockHeld out = null;

		for(StockHeld s : stockList) {

			if(stockSymbol.toLowerCase().equals(s.getSymbol().toLowerCase())) out = s;

		}

		return out;

	}
	
	public float getChange() throws IOException {

		float out = 0.0f;

		for(StockHeld s : stockList) {

			out += (s.getCurrentPrice() * s.getAmount() - s.getPriceBoughtAt() * s.getAmount());

		}

		out += change;

		return out;

	}
	
}
