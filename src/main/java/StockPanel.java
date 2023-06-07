
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import io.github.mainstringargs.alphavantagescraper.output.timeseries.data.StockData;

public class StockPanel extends JPanel{

	JLabel symbol;
	Stock stock;
	List<StockData> stockData;
	JPanel marketPanel;

	// used in market panel history list, clickable.
	public StockPanel(Stock stock, List<StockData> stockData, final MarketPanel marketPanel) {

		this.stock = stock;
		this.stockData = stockData;
		this.marketPanel = marketPanel;
		this.stock.setHistory(stockData);
		symbol = new JLabel(stock.getSymbol());
		symbol.setBounds(0, 0, 200, 30);
		this.add(symbol);

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				isClicked(e, marketPanel);

			}
		});

		this.setVisible(true);

	}

	public void isClicked(MouseEvent e, MarketPanel marketPanel ) {

		marketPanel.updateFromRecent(stock, stockData);

	}

}
