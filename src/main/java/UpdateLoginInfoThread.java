
public class UpdateLoginInfoThread extends Thread{

	public UpdateLoginPanel updatePanel;
	public GetUserThread guThread;
	public boolean isLoading;
	
	// Updates update login panel, for loading animation 
	public UpdateLoginInfoThread(UpdateLoginPanel updatePanel, GetUserThread guThread) {
		
		this.updatePanel = updatePanel;
		this.guThread = guThread;
		
	}
	
	@Override
	public synchronized void run() {
		
		updatePanel.isLoading = true;
		
		while(guThread.isAlive()) {
			
			updatePanel.repaint();
			try {
				this.wait(10);
			} catch (InterruptedException e) {}
			
		}
		
		updatePanel.isLoading = false;
		updatePanel.repaint();
		
	}
	
}
