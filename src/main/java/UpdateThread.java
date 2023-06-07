import java.util.List;

import io.github.mainstringargs.alphavantagescraper.output.AlphaVantageException;

import java.util.ArrayList;


public class UpdateThread extends Thread{

	public RequestHandler rh;
	public List<StockHeld> stockList;
	public PortfolioPanel pPanel;

	// Updates all stocks in portfolio
	public UpdateThread(RequestHandler rh, PortfolioPanel pPanel) {

		this.rh = rh;
		this.pPanel = pPanel;

	}

	@Override
	public synchronized void run() {

		List<StockHeld> temp = new ArrayList<StockHeld>();
		pPanel.updateButton.setEnabled(false);
		
		UpdateInfoThread uiThread = new UpdateInfoThread(pPanel.updatePanel, temp, stockList.size());
		uiThread.start();
		
		for(int i = 0; i < stockList.size(); i++) {

			while(temp.size() != i + 1) {

				try {
					temp.add(new StockHeld(rh.get(stockList.get(i).getSymbol()), stockList.get(i).getPricePaid()));
				}
				catch(NullPointerException | AlphaVantageException e){
					try {
						this.wait(5000);
					} catch (InterruptedException e1) {}
				}

			}

		}
		
		pPanel.updateButton.setEnabled(true);
		pPanel.updatePanel();

	}

}
