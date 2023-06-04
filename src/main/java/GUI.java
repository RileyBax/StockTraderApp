/* TODO:
 * new portfolio class
 * handle requests between panels
 * buy and sell functionality
 * CREATE LOGIN PANEL, READ FROM DB
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI extends JFrame{

	static RequestHandler rh = new RequestHandler();
	
	public GUI() {

		this.setBounds(0, 0, 800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		// login here
		Portfolio pf = new Portfolio("temp");
		
		MarketPanel mPanel = new MarketPanel(rh, pf);
		PortfolioPanel pPanel = new PortfolioPanel(rh, pf);
		
		this.add(mPanel);
		mPanel.setVisible(true);
		
		this.add(pPanel);
		pPanel.setVisible(false);
		
		JButton swapButton = new JButton("Portfolio");
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

		this.setVisible(true);

	}

}
