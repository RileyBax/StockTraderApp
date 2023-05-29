//import java.io.File;
//import java.io.IOException;
//import java.net.UnknownHostException;
//import java.util.HashMap;
//import java.util.Scanner;
//
//// this app creates a profile for a user, gets real time stats from yahoo finance,
//// saves prices of stocks the user buys and sells, shows profit and losses.
//
//public class Main {
//	
//	public static void main(String[] args) throws IOException {
//
//		Scanner scan = new Scanner(System.in);
//		//Scanner fScan = new Scanner(new File("./UserData"));
//		RequestHandler rh = null;
//		
//		System.setProperty("yahoofinance.baseurl.quotesquery1v7", "https://query1.finance.yahoo.com/v6/finance/quote");
//		
//		System.out.println("Enter your UserName: ");
//		String name = scan.nextLine();
//		// Read in name, check if user exists, create profile using saved data
////		while(fScan.hasNextLine()) {
////			
////			String temp = fScan.nextLine();
////			
////			if(temp.split(",")[0].toLowerCase().equals(name.toLowerCase())) {
////				
////				rh = new RequestHandler(new Portfolio(name, temp));
////				
////			}
////			
////		}
//		// Create new profile if name is new
//		if(rh == null) rh = new RequestHandler(new Portfolio(name));
//		// Check for Internet connection
//		try {
//			rh.get("test");
//		}
//		catch(UnknownHostException e) {
//			System.out.println("You are not connected to the Internet, functionality is limit.\n");
//		}
//		// Check if followed stock's respective market is currently open
//		long cTime = System.currentTimeMillis() / 1000 / 60 / 60;
//		HashMap<String, String> stockExchange = new HashMap<String, String>();
//		
//		if(rh.pf.stockList.size() > 0) {
//			
//			for(StockHeld s : rh.pf.stockList) {
//				
//				yahoofinance.Stock temp = rh.get(s.getSymbol());
//				
//				if(cTime - temp.getQuote().getLastTradeTime().getTimeInMillis() / 1000 / 60 / 60 > 2 && !stockExchange.containsKey(temp.getStockExchange())) {
//					stockExchange.put(temp.getStockExchange(), " Closed");
//				}
//				else if(!stockExchange.containsKey(temp.getStockExchange())){
//					stockExchange.put(temp.getStockExchange(), " Open");
//				}
//				
//			}
//			
//			for(String s : stockExchange.keySet()) {
//				
//				System.out.println(s + stockExchange.get(s));
//				
//			}
//			
//			System.out.println();
//			
//		}
//		// Main app loop
//		while(true) {
//
//			System.out.println("	- Main Menu -\n\n1. Search for Stock\n2. Buy Stock\n3. Sell Stock\n4. View Portfolio\n5. Save and Exit");
//
//			try {
//
//				int input = Integer.parseInt(scan.nextLine());
//
//				switch(input) {
//
//					case 1:
//						rh.search();
//						break;
//					case 2:
//						rh.buy();
//						break;
//					case 3:
//						rh.sell();
//						break;
//					case 4:
//						rh.portfolio();
//						break;
//					case 5:
//						rh.save();
//						System.exit(0);
//				}
//
//			}
//			catch(Exception e) {
//
//				System.out.println("Please enter a Valid Input.\n");
//
//			}
//
//		}
//
//
//	}
//
//}
