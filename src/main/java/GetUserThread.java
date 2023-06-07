import java.util.ArrayList;

import io.github.mainstringargs.alphavantagescraper.output.AlphaVantageException;

public class GetUserThread extends Thread{

	ArrayList<StartStock> startList;
	Portfolio pf;
	RequestHandler rh;
	PortfolioPanel pPanel;
	GUI gui;
	MarketPanel mPanel;
	
	public GetUserThread(ArrayList<StartStock> startList, Portfolio pf, GUI gui, PortfolioPanel pPanel, MarketPanel mPanel) {
		
		this.startList = startList;
		this.pf = pf;
		this.gui = gui;
		rh = gui.rh;
		this.pPanel = pPanel;
		this.mPanel = mPanel;
		
	}
	
	
        // Seperate thread that continues requesting stock data until all saved stocks are received.
	@Override
	public synchronized void run() {

		if(startList.size() > 0) {

			int size = startList.size();

			for(int i = 0; i < size; i++) {

				while(pf.stockList.size() != i + 1) {

					try {
						pf.stockList.add(new StockHeld(rh.get(startList.get(i).symbol), startList.get(i).amount, startList.get(i).pricePaid, startList.get(i).priceBoughtAt));
					}
					catch(NullPointerException | AlphaVantageException e){
						try {
							this.wait(5000);
						} catch (InterruptedException e1) {}
					}

				}

			}
			
			for(int i = 0; i < size; i++) {
				
				while(pf.stockList.get(i).getHistory() == null) {
					
					pf.stockList.get(i).setHistory(rh.getHistory(startList.get(i).symbol));
					
					if(pf.stockList.get(i).getHistory() == null) {
						try {
							this.wait(5000);
						} catch (InterruptedException e) {}
					}
					
				}
				
			}

			for(int i = 0; i < size ; i++) {
				
				mPanel.updatePanel(pf.stockList.get(i), pf.stockList.get(i).getHistory());
				
			}
			
		}

		gui.lPanel.setVisible(false);
		gui.mPanel.setVisible(true);
		gui.swapButton.setVisible(true);
		
	}
	
}
