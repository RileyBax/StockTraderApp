/* TODO:
 * plan gui layout - 2 panels, search and current stock data on first, portfolio info on second, JScrollBar
 * create new request handler - multi thread
 * 5 api calls per min, create queue and make calls when possible?
 * new stock and stock held classes
 * new portfolio class
 * get stock method
 */

import javax.swing.JFrame;

public class GUI extends JFrame{

	static RequestHandler rh = new RequestHandler();
	
	public GUI() {

		this.setBounds(0, 0, 800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		MarketPanel mPanel = new MarketPanel(rh);
		
		this.add(mPanel);
		mPanel.setVisible(true);

		this.setVisible(true);

	}

}
