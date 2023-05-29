import javax.swing.JFrame;

public class App extends JFrame{

	public App() {
		
		this.setBounds(0, 0, 800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		
		App frame = new App();

	}

}
