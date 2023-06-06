import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JLabel;

public class LoginPanel extends JPanel{

	public LoginPanel(final GUI gui, final Portfolio pf, final PortfolioPanel pPanel) {

		this.setBounds(0, 0, 800, 600);
		this.setLayout(null);

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

		JButton inputButton = new JButton("Enter");
		inputButton.setBounds(290, 300, 200, 30);
		inputButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(!input.getText().isEmpty()) {

					getThis().setVisible(false);
					pf.name = input.getText();
					pPanel.updateName();
					try {
						pPanel.getUser();
					} catch (SQLException e1) {}
					
					gui.mPanel.setVisible(true);
					gui.swapButton.setVisible(true);

				}

			}
		});
		this.add(inputButton);

		this.setVisible(true);

	}

	public LoginPanel getThis() {
		return this;
	}

}
