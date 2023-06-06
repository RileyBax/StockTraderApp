import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StockHeldPanel extends JPanel{

	StockHeld stockHeld;
	JLabel symbol;
	PortfolioPanel pPanel;
	
	public StockHeldPanel(StockHeld stockHeld, final PortfolioPanel pPanel) {
		
		this.stockHeld = stockHeld;
		this.pPanel = pPanel;
		
		symbol = new JLabel(stockHeld.getSymbol());
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

				isClicked(e, pPanel);

			}
		});

		this.setVisible(true);
		
	}
	
	public void isClicked(MouseEvent e, PortfolioPanel pf) {
		
		// update graph, shpw amount owned
		if(pPanel.length > 31) pPanel.length = stockHeld.getHistory().size();
		pPanel.selectedStock = stockHeld;
		pPanel.updatePanel();
		
	}
	
}
