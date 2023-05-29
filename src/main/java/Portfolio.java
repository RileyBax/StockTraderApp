import java.io.IOException;
import java.util.ArrayList;

// portfolio class is unique for each user, stores their respective shares.
public class Portfolio {

	ArrayList<StockHeld> stockList = new ArrayList<StockHeld>();
	String name;
	private float change = 0.0f;

	// Reads in all saved data for returning user.
	public Portfolio(String name, String data) throws NumberFormatException, IOException {

		this.name = name;

		String[] tempData = data.split("/");

		for(int i = 1; i < tempData.length; i++) {

			String[] tempStock = tempData[i].split(",");

			stockList.add(new StockHeld(RequestHandler.get(tempStock[0]), Float.parseFloat(tempStock[1]),  Float.parseFloat(tempStock[2]),  Float.parseFloat(tempStock[3])));

		}

		change = Float.parseFloat(tempData[0].split(",")[1]);

	}

	// Creates new profile for first time user.
	public Portfolio(String name) {

		this.name = name;

	}

	public float getSetChange() {
		return change;
	}

	public void add(Stock stock, float pricePaid) {

		stockList.add(new StockHeld(stock, pricePaid));

	}

	public void sell(StockHeld sh, float sellAmount) throws IOException {

		if(sh.getAmount() >= sellAmount) {

			Stock tempStock = new Stock(RequestHandler.get(sh.getSymbol()));

			// converts currency to NZ dollar.
			float convertNZD = 1;
			if(!sh.getCurrency().equals("NZD")) {
				convertNZD = RequestHandler.get(sh.getCurrency() + "NZD=X").getQuote().getPrice().floatValue();
			}

			change += (tempStock.getCurrentPrice() * sellAmount - sh.getPriceBoughtAt() * sellAmount) * convertNZD;
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

	@Override
	public String toString() {

		String out = "";

		for(StockHeld s : stockList) {

			out += "\n" + s;

		}

		return out;

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

			float convertNZD = 1;
			if(!s.getCurrency().equals("NZD")) {
				convertNZD = RequestHandler.get(s.getCurrency() + "NZD=X").getQuote().getPrice().floatValue();
			}

			out += (s.getCurrentPrice() * s.getAmount() - s.getPriceBoughtAt() * s.getAmount()) * convertNZD;

		}

		out += change;

		return out;

	}

}
