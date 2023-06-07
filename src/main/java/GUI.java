/* TODO:
 * polish
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI extends JFrame{

	static RequestHandler rh = new RequestHandler();
	final MarketPanel mPanel;
	final PortfolioPanel pPanel;
	final JButton swapButton;
	final LoginPanel lPanel;
	
	public GUI() {

		this.setBounds(0, 0, 800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		Portfolio pf = new Portfolio("");
		
		mPanel = new MarketPanel(rh, pf);
		pPanel = new PortfolioPanel(rh, pf);
		
		lPanel = new LoginPanel(this, pf, pPanel, mPanel);
		this.add(lPanel);
		
		this.add(mPanel);
		mPanel.setVisible(false);
		
		this.add(pPanel);
		pPanel.setVisible(false);
		
		swapButton = new JButton("Portfolio");
		swapButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(mPanel.isVisible()) {
					mPanel.setVisible(false);
					pPanel.setVisible(true);
					pPanel.updatePanel();
					swapButton.setText("Market");
				}
				else {
					mPanel.setVisible(true);
					pPanel.setVisible(false);
					swapButton.setText("Portfolio");
				}
				
			}
		});
		swapButton.setBounds(10, 10, 200, 30);
		this.add(swapButton);
		swapButton.setVisible(false);

		this.setVisible(true);

	}

}
