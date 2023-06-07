import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

public class UpdatePanel extends JPanel{

	public boolean isLoading;
	public int angle;
	int x;
	int y;
	int size;
	List<StockHeld> temp;
	
	// loading animation, used in portfolio panel
	public UpdatePanel() {
		
		this.setBounds(630, 110, 140, 120);
		isLoading = false;
		angle = 180;
		
	}
	
	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(new Color(238, 238, 238));
		g2d.fillRect(0, 0, 140, 120);
		
		if(isLoading) {
			
			for(int i = 0; i < 5; i++) {
			
				x = (int) (70 + Math.sin(Math.toRadians(angle + (i*20))) * 40);
				y = (int) (60 + Math.cos(Math.toRadians(angle + (i*20))) * 40);
				
				g2d.setColor(new Color(255 - i * 50, 255 - i * 50, 255 - i * 50));
				g2d.fillOval(x - 5 * i / 2, y - 5 * i / 2, 5 * i, 5 * i);
				
			}
			
			g2d.drawString(temp.size() + "/" + size, 60, 65);
			
			angle+=1;
		}
		else {
			angle = 180;
			g2d.setColor(new Color(238, 238, 238));
			g2d.fillRect(0, 0, 140, 120);
		}
		
	}
	
}
