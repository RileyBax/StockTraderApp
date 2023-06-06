import java.util.List;

public class UpdateInfoThread extends Thread{

	public UpdatePanel updatePanel;
	public List<StockHeld> temp;
	public int size;
	public boolean isLoading;
	
	public UpdateInfoThread(UpdatePanel updatePanel, List<StockHeld> temp, int size) {
		
		this.updatePanel = updatePanel;
		this.temp = temp;
		this.size = size;
		
	}
	
	@Override
	public synchronized void run() {
		
		updatePanel.isLoading = true;
		
		while(temp.size() < size) {
			
			updatePanel.repaint();
			try {
				this.wait(10);
			} catch (InterruptedException e) {}
			
		}
		
		updatePanel.isLoading = false;
		updatePanel.repaint();
		
	}
	
}
