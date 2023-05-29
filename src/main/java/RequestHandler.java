import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Request Handler class stores methods for all application requests
// also handles most yahoo finance api requests.
public class RequestHandler extends Requests{

	Scanner scan = new Scanner(System.in);
	Portfolio pf;
	Stock stock;

	public RequestHandler(Portfolio pf) {

		this.pf = pf;

	}

	@Override
	public void search() {

		System.out.println("Enter the Stock Symbol");
		String input = scan.nextLine();

		try {

			stock = new Stock(this.get(input));

			System.out.println(stock);

		} 
		catch (Exception e) {
			System.out.println("Could not find \"" + input + "\"\n");
		}


	}

	@Override
	public void buy() {

		try {

			System.out.println("Enter the Stock Symbol");
			String inputSymbol = scan.nextLine();

			Stock tempStock = new Stock(this.get(inputSymbol));

			System.out.println(tempStock);

			System.out.println("Enter investment Price (" + tempStock.getCurrency() + ")\nEnter 0 to cancel");
			String inputPrice = scan.nextLine();

			if(Float.parseFloat(inputPrice) > 0.0f) {
				pf.add(tempStock, Float.parseFloat(inputPrice));
				System.out.println(pf.getStock(tempStock.getSymbol()).getAmount() + " " + tempStock.getName() + " shares bought for " + inputPrice + " " + tempStock.getCurrency() + "\n");
			}
			else {
				System.out.println("Request Cancelled");
			}

		} 
		catch (IOException e) {}

	}

	@Override
	public void sell() {

		if(pf.stockList.size() > 0) {

			System.out.println("Held Stocks:");

			for(StockHeld s : pf.stockList) {

				System.out.println(s.getSymbol());

			}

			System.out.println("\nEnter the Stock Symbol to sell");
			StockHeld sh = pf.getStock(scan.nextLine());

			if(sh != null) {
				System.out.println(sh);
			}
			else {
				System.out.println("Cancelled sell Request");
			}


			try {
				System.out.println("How many shares would you like to sell (\"All\" sells Maximum) (Max: " + sh.getAmount() + ")\nEnter 0 to cancel");
				String input = scan.nextLine();
				if(input.toLowerCase().equals("all")) 
				{
					System.out.println(sh.getAmount() + " " + sh.getName() + " shares sold\n");
					pf.sell(sh, sh.getAmount());
				}
				else if (Float.parseFloat(input) > 0.0f) {
					System.out.println(input + " " + sh.getName() + " shares sold\n");
					pf.sell(sh, Float.parseFloat(input));
				}
				else {
					System.out.println("Request Cancelled");
				}
			} catch (IOException e) {}

		}
		else {
			System.out.println("You do not hold any shares.");
		}

	}

	@Override
	public void portfolio() {

		boolean cont = true;

		while(cont) {

			System.out.println("	- Profile -\n\n1. View all Held Stock\n2. Search for Held Stock\n3. View current Profit/Loss\n4. Return");
			int input = Integer.parseInt(scan.nextLine());

			switch(input) {

			case 1:
				if (pf.stockList.size() > 0) {
					System.out.println(pf);
				}
				else {
					System.out.println("You do not hold any shares.\n");
				}
				break;
			case 2:
				if (pf.stockList.size() > 0) {
					for(StockHeld s : pf.stockList) {

						System.out.println(s.getSymbol());

					}
					System.out.println("\nEnter the Stock Symbol");
					StockHeld temp = pf.getStock(scan.nextLine());
					if(temp != null) {
						System.out.println(temp);
					}
					else {
						System.out.println("Could not find symbol");
					}
				}
				else {
					System.out.println("You do not hold any shares.\n");
				}
				break;
			case 3:
				try {
					System.out.println("Profit/Losses: " + pf.getChange() + " NZD\n");
				} 
				catch (Exception e) {
					
					try {
						System.out.println("Profit/Losses: " + pf.getChange() + " NZD\n");
					}
					catch (Exception ee) {}
					
				}
				break;
			case 4:
				cont = false;
				break;

			}

		}

	}
	
	// reads in all data, saves current user data to file
	// Separates stocks by backslash
	@Override
	public void save() {

		ArrayList<String> profiles = new ArrayList<String>();

		try {

			Scanner fScan = new Scanner(new File("./UserData"));

			while(fScan.hasNextLine()) {

				String temp = fScan.nextLine();

				if(!temp.split(",")[0].toLowerCase().equals(pf.name.toLowerCase())) {
					profiles.add(temp);
				}

			}

		} catch (FileNotFoundException e1) {}

		try {

			FileWriter fw = new FileWriter(new File("./UserData"));

			fw.write(pf.name + "," + pf.getSetChange());

			for(StockHeld sh : pf.stockList) {

				fw.write("/" + sh.getSymbol() + "," + sh.getAmount() + "," + sh.getPricePaid() + "," + sh.getPriceBoughtAt());

			}

			for(String s : profiles) {

				fw.write("\n" + s);

			}

			fw.close();

		} catch (IOException e) {}

	}

}
