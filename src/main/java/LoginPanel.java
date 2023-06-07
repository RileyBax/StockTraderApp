import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;

public class LoginPanel extends JPanel{
	
	Portfolio pf;
	PortfolioPanel pPanel;
	GUI gui;
	MarketPanel mPanel;
	JButton inputButton;

	public LoginPanel(final GUI gui, final Portfolio pf, final PortfolioPanel pPanel,  MarketPanel mPanel) {

		this.setBounds(0, 0, 800, 600);
		this.setLayout(null);
		
		this.pf = pf;
		this.pPanel = pPanel;
		this.gui = gui;
		this.mPanel = mPanel;

		JLabel title = new JLabel("Stock Trader");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42));
		title.setBounds(260, 100, 400, 100);
		this.add(title);

		JLabel inputLabel = new JLabel("Enter your Profile Name");
		inputLabel.setBounds(320, 230, 200, 30);
		this.add(inputLabel);

		final JTextField input = new JTextField();
		input.setBounds(290, 270, 200, 30);
		this.add(input);

		inputButton = new JButton("Enter");
		inputButton.setBounds(290, 300, 200, 30);
		inputButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(!input.getText().isEmpty()) {

					pf.name = input.getText();
					pPanel.updateName();
					
					try {
						setUpPortfolio();
						inputButton.setEnabled(false);
					} catch (SQLException e1) {
						gui.lPanel.setVisible(false);
						gui.mPanel.setVisible(true);
						gui.swapButton.setVisible(true);
					}

				}

			}
		});
		this.add(inputButton);

		this.setVisible(true);

	}
	
	public void setUpPortfolio() throws SQLException {
		
		ResultSet rs = pPanel.db.getData(pf.name);

		ArrayList<StartStock> startList = new ArrayList<StartStock>();
		int i = 0;
		while(rs.next()) {

			startList.add(new StartStock(rs.getString("symbol"), rs.getString("amount"), rs.getString("pricePaid"), rs.getString("priceBoughtAt")));
			
		}

		GetUserThread guThread = new GetUserThread(startList, pf, gui, pPanel, mPanel);
		UpdateLoginPanel uPanel = new UpdateLoginPanel();
		uPanel.setBounds(320, 330, 140, 120);
		this.add(uPanel);
		UpdateLoginInfoThread uiThread = new UpdateLoginInfoThread(uPanel, guThread);
		guThread.start();
		uiThread.start();
		
	}

	public LoginPanel getThis() {
		return this;
	}

}
