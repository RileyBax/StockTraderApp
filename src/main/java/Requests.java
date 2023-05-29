import yahoofinance.YahooFinance;

public abstract class Requests extends YahooFinance{

	public abstract void search();
	public abstract void buy();
	public abstract void sell();
	public abstract void portfolio();
	public abstract void save();
	
}
