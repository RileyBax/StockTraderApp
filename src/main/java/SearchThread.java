import java.util.List;

import io.github.mainstringargs.alphavantagescraper.output.timeseries.data.StockData;

public class SearchThread extends Thread{

	RequestHandler rh;
	Stock stock;
	String symbol;
	MarketPanel mPanel;
	List<StockData> stockDataList;

	public SearchThread(RequestHandler rh, Stock stock, List<StockData> stockDataList, String symbol, MarketPanel mPanel) {

		this.rh = rh;
		this.stock = stock;
		this.symbol = symbol;
		this.stockDataList = stockDataList;
		this.mPanel = mPanel;

	}

	@Override
	public synchronized void run() {

		int i = 0;

		mPanel.searchButton.setEnabled(false);
		mPanel.buyButton.setEnabled(false);
		mPanel.sellButton.setEnabled(false);

		while(stock == null && i < 3 || stockDataList == null && i < 3) {

			if(stock == null) stock = rh.get(symbol);
			else if(stockDataList == null) stockDataList = rh.getHistory(symbol);

			if(stock == null || stockDataList == null) {

				try {
					this.wait(5000);
				} 
				catch (InterruptedException e) {}

			}

			i++;

		}

		if(stock != null) {
			mPanel.updatePanel(stock, stockDataList);
		}
		mPanel.searchButton.setEnabled(true);
		mPanel.buyButton.setEnabled(true);
		mPanel.sellButton.setEnabled(true);
		
	}

}
