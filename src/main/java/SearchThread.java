
public class SearchThread extends Thread{

	RequestHandler rh;
	Stock stock;
	String symbol;
	MarketPanel mPanel;
	
	public SearchThread(RequestHandler rh, Stock stock, String symbol, MarketPanel mPanel) {

		this.rh = rh;
		this.stock = stock;
		this.symbol = symbol;
		this.mPanel = mPanel;

	}

	@Override
	public synchronized void run() {

		int i = 0;
		
		mPanel.searchButton.setEnabled(false);
		
		while(stock == null && i < 5) {

			stock = rh.get(symbol);

			if(stock == null) {
				
				try {
					this.wait(5000);
				} 
				catch (InterruptedException e) {}
				
			}

			i++;
			
		}
		
		if(stock != null) {
			mPanel.updatePanel(stock);
		}
		mPanel.searchButton.setEnabled(true);

	}

}
